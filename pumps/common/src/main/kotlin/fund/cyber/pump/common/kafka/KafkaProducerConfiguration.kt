package fund.cyber.pump.common.kafka

import fund.cyber.common.kafka.JsonSerializer
import fund.cyber.common.kafka.defaultProducerConfig
import fund.cyber.common.with
import fund.cyber.search.configuration.KAFKA_BROKERS
import fund.cyber.search.configuration.KAFKA_BROKERS_DEFAULT
import fund.cyber.search.model.chains.Chain
import fund.cyber.search.model.chains.EthereumFamilyChain
import fund.cyber.search.model.events.PumpEvent
import fund.cyber.search.model.events.blockPumpTopic
import fund.cyber.search.model.events.txPumpTopic
import org.apache.kafka.clients.admin.AdminClient
import org.apache.kafka.clients.admin.AdminClientConfig
import org.apache.kafka.clients.admin.NewTopic
import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.common.config.TopicConfig
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.annotation.EnableKafka
import org.springframework.kafka.core.DefaultKafkaProducerFactory
import org.springframework.kafka.core.KafkaAdmin
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.core.ProducerFactory
import org.springframework.kafka.transaction.KafkaTransactionManager
import org.springframework.transaction.annotation.EnableTransactionManagement
import java.util.concurrent.TimeUnit
import javax.annotation.PostConstruct

private const val CLEANUP_RETENTION_POLICY_TIME_DAYS = 14L

@EnableKafka
@Configuration
@EnableTransactionManagement
class KafkaProducerConfiguration {

    @Value("\${$KAFKA_BROKERS:$KAFKA_BROKERS_DEFAULT}")
    private lateinit var kafkaBrokers: String

    @Autowired
    private lateinit var chain: Chain

    @Autowired
    private lateinit var kafkaTopicNames: List<String>

    @Bean
    fun kafkaAdmin(): KafkaAdmin {
        val configs = mapOf(
                AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG to kafkaBrokers
        )
        return KafkaAdmin(configs)
    }

    @Bean
    fun producerFactory(): ProducerFactory<PumpEvent, Any> {
        return DefaultKafkaProducerFactory<PumpEvent, Any>(producerConfigs(), JsonSerializer(), JsonSerializer())
                .apply {
                    setTransactionIdPrefix(chain.name + "_PUMP")
                }
    }

    @Bean
    fun kafkaTemplate(): KafkaTemplate<PumpEvent, Any> {
        return KafkaTemplate(producerFactory())
    }

    @Bean
    fun producerConfigs(): Map<String, Any> = defaultProducerConfig().with(
            ProducerConfig.BOOTSTRAP_SERVERS_CONFIG to kafkaBrokers
    )

    @Bean
    fun transactionManager(): KafkaTransactionManager<PumpEvent, Any> {
        return KafkaTransactionManager(producerFactory())
    }

    @Bean
    fun topicConfigs(): Map<String, String> {
        return mapOf(
                TopicConfig.RETENTION_MS_CONFIG to TimeUnit.DAYS.toMillis(CLEANUP_RETENTION_POLICY_TIME_DAYS)
                        .toString(),
                TopicConfig.CLEANUP_POLICY_CONFIG to TopicConfig.CLEANUP_POLICY_DELETE
        )
    }

    @PostConstruct
    fun createTopics() {
        val kafkaClient = AdminClient.create(kafkaAdmin().config)

        val newTopics = mutableListOf<NewTopic>()
        kafkaTopicNames.forEach { topic ->
            newTopics.add(NewTopic(topic, 1, 1).configs(topicConfigs()))
        }

        kafkaClient.createTopics(newTopics)
        kafkaClient.close()
    }
}

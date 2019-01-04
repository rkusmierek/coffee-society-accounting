package pl.altkom.coffee.accounting.configuration

import com.mongodb.MongoClient
import org.axonframework.eventhandling.tokenstore.TokenStore
import org.axonframework.extensions.mongo.DefaultMongoTemplate
import org.axonframework.extensions.mongo.MongoTemplate
import org.axonframework.extensions.mongo.eventsourcing.tokenstore.MongoTokenStore
import org.axonframework.serialization.Serializer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class AxonMongoConfig {

    @Bean
    fun mongoTemplate(mongoClient: MongoClient): MongoTemplate {
        return DefaultMongoTemplate.builder()
                .domainEventsCollectionName("domainevents_accounting")
                .sagasCollectionName("sagas_accounting")
                .snapshotEventsCollectionName("snapshotevents_accounting")
                .trackingTokensCollectionName("trackingtokens_accounting")
                .mongoDatabase(mongoClient)
                .build()
    }

    @Bean
    fun tokenStore(mongoTemplate: MongoTemplate, tokenSerializer: Serializer): TokenStore {
        return MongoTokenStore.builder()
                .mongoTemplate(mongoTemplate)
                .serializer(tokenSerializer)
                .build()
    }

}

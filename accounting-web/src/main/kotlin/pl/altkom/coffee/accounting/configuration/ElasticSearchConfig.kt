package pl.altkom.coffee.accounting.configuration

import org.springframework.context.annotation.Configuration
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories

@Configuration
@EnableElasticsearchRepositories("pl.altkom.coffee.accounting.provider")
class ElasticSearchConfig

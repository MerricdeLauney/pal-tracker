package io.pivotal.pal.tracker

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.Bean
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.SerializationFeature
import com.mysql.cj.jdbc.MysqlDataSource
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder


@SpringBootApplication
class PalTrackerApplication {

    @Value("\${SPRING_DATASOURCE_URL}")
    var dataSourceUrl: String? = null

    @Bean
    fun timeEntryRepository(): TimeEntryRepository {
        val ds = MysqlDataSource()
        ds.setURL(dataSourceUrl)
        return JdbcTimeEntryRepository(ds)
    }

    @Bean fun jsonObjectMapper(): ObjectMapper{
        return Jackson2ObjectMapperBuilder.json()
                .serializationInclusion(JsonInclude.Include.NON_NULL) // Don’t include null values
                .featuresToDisable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS) //ISODate
                .modules(JavaTimeModule())
                .build()
    }


}


fun main(args: Array<String>) {
    SpringApplication.run(PalTrackerApplication::class.java, *args)
}

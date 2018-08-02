
package frontend.fabricstore

import com.mongodb.MongoClient
import com.mongodb.MongoClientOptions
import com.mongodb.MongoCredential
import com.mongodb.ServerAddress
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.PropertySource
import org.springframework.core.annotation.Order
import org.springframework.data.mongodb.MongoDbFactory
import org.springframework.data.mongodb.core.SimpleMongoDbFactory
import org.springframework.web.multipart.commons.CommonsMultipartResolver
import org.springframework.web.multipart.support.StandardServletMultipartResolver
import org.springframework.web.multipart.support.MultipartFilter




@Configuration
@PropertySource("classpath:application.properties")
class AppConfig {
    @Value("\${DB_HOST}") internal var dbHost: String = "mongodb"
    @Value("\${DB_PORT}") internal var dbPort: Int = 27017
    @Value("\${DB_NAME}") internal var dbName: String = "templates"
    @Value("\${DB_USER}") internal var dbUser: String = "mongo"
    @Value("\${DB_PASSWORD}") internal var dbPassword: String = "mongo"

    /**
     * DB connection Factory
     *
     * @return a ready to use MongoDbFactory
     */
    @Bean
    @Throws(Exception::class)
    fun mongoDbFactory(): MongoDbFactory {
        println(" ########################### ")
        println("mongoDbFactory() called - access parameters are: dbHost: ${dbHost}, dbPort: ${dbPort}, dbName: ${dbName}, dbUser: ${dbUser}, dbPassword: ${dbPassword}.")
        println(" ########################### ")
        // Set credentials
        val credential = MongoCredential.createScramSha1Credential(dbUser, dbName, dbPassword.toCharArray())
        val serverAddress = ServerAddress(dbHost, dbPort)

        // Mongo Client
        val mongoClient = MongoClient(serverAddress, credential, MongoClientOptions.builder().build())

        // Mongo DB Factory
        return SimpleMongoDbFactory(
            mongoClient, dbName
        )
    }

    @Bean
    fun multipartResolver(): CommonsMultipartResolver {
        val multipart = CommonsMultipartResolver()
        multipart.setMaxUploadSize((3 * 1024 * 1024).toLong())
        return multipart
    }

    @Bean
    @Order(0)
    fun multipartFilter(): MultipartFilter {
        val multipartFilter = MultipartFilter()
        multipartFilter.setMultipartResolverBeanName("multipartResolver")
        return multipartFilter
    }
}

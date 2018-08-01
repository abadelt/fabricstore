
package frontend.fabricstore

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.context.annotation.PropertySource
import org.springframework.jdbc.datasource.DriverManagerDataSource
import org.springframework.transaction.annotation.EnableTransactionManagement
import javax.sql.DataSource


@Configuration
@EnableTransactionManagement
@PropertySource("classpath:application.properties")
@Profile("default")
class AppConfig {
    @Value("\${MYSQL_DB_HOST}") internal var host: String? = "cf-mysql"
    @Value("\${MYSQL_DB_PORT}") internal var port: String? = "33061"
    @Value("\${MYSQL_DB_NAME}") internal var dbName: String? = "cf-mysqldb"
    @Value("\${MYSQL_USER}") internal var userName: String? = "cf-mysql"
    @Value("\${MYSQL_PASSWORD}") internal var password: String? = "cf-mysql"

    @Bean
    fun dataSource(): DataSource {
        val dataSource = DriverManagerDataSource()

        dataSource.username = userName
        dataSource.password = password
        dataSource.url = "jdbc:mysql://${host}:${port}/${dbName}?useSSL=false&createDatabaseIfNotExist=true"

        return dataSource
    }
}

/*
package frontend.fabricstore

import org.springframework.jdbc.datasource.init.DataSourceInitializer
import org.springframework.orm.hibernate5.HibernateTransactionManager
import org.hibernate.SessionFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.orm.hibernate5.LocalSessionFactoryBuilder
import org.springframework.boot.jdbc.DataSourceBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.PropertySource
import org.springframework.transaction.annotation.EnableTransactionManagement
import javax.sql.DataSource

@Configuration
@EntityScan("frontend.fabricstore.model")
@EnableTransactionManagement
@PropertySource("classpath:application.properties")
class AppConfig {
    @Value("\${app.datasource.driverClassName}") internal var driverClassName:String? = null
    @Value("\${app.datasource.url}") internal var url:String? = null
    @Value("\${app.datasource.username}") internal var username:String? = null
    @Value("\${app.datasource.password}") internal var password:String? = null

    @Bean(name = arrayOf("dataSource"))
    fun getDataSource(): DataSource = DataSourceBuilder
        .create()
        .username(username)
        .password(password)
        .url(url)
        .driverClassName(driverClassName)
        .build()

    @Bean(name = arrayOf("sessionFactory"))
    fun getSessionFactory(dataSource: DataSource): SessionFactory {
        val sessionBuilder = LocalSessionFactoryBuilder(dataSource)
        sessionBuilder.scanPackages("frontend.fabricstore.model")
        return sessionBuilder.buildSessionFactory()
    }

    @Bean(name = arrayOf("transactionManager"))
    fun getTransactionManager(
        sessionFactory:SessionFactory): HibernateTransactionManager {
        return HibernateTransactionManager(
            sessionFactory)
    }

    @Bean
    fun dataSourceInitializer(dataSource: DataSource): DataSourceInitializer {
        val initializer = DataSourceInitializer()
        initializer.setDataSource(dataSource)
        return initializer
    }
}
 */
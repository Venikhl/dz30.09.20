package org.step.configuration;

import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;

/*
beans.xml - то же что и конфигурация

Цикл создания бинов:
0. Загрузка Environment - среда в которой выполняется ваше приложение
1. BeanDefinitionReader сканирует все классы и получает из них информацию о бинах
2. BeanFactory - создает объекты из предоставленной информации
3. Dependency Injection - внедрение созданных ранее зависимостей
4. BeanPostProcessor - method postProcessBeforeInitialization - настроит бин
5. @PostConstruct (init-method, afterPropertiesSet)
6. BeanPostProcessor - method postProcessAfterInitialization - создает (если нужно) proxy класс
7. @PreDestroy (destroy)
 */
@Configuration
@ComponentScan(basePackages = {"org.step"})
@PropertySources({
        @PropertySource("classpath:db.properties")
})
@EnableTransactionManagement
// Включает аспектно-ориентированный шаблон программирования для Spring
@EnableAspectJAutoProxy
@EnableJpaRepositories(basePackages = "org.step")
public class DatabaseConfiguration {

    private final Environment environment;

    @Autowired
    public DatabaseConfiguration(Environment environment) {
        this.environment = environment;
    }

    /*
    Если ID не указан напрямую, то он всегда является названием метода (dataSource)
     */
//    @Bean
//    public ProfileRepository profileRepository() {
//        return new ProfileRepositoryImpl();
//    }

    /*
    singleton - Bean будет создан 1 раз за весь цикл жизни приложения
    prototype - Bean будет создан, когда произойдет обращение к нему (не создаются на старте контекста)
    request - Bean будет создан каждый раз, когда появляется новый Http request
    session - Bean будет создан каждый раз, когда появляется новая Http сессия
    globalSession - как и session, только для Portlet web приложений
     */
    @Bean(value = "dataSourceJavaConfig")
    @Profile("default")
//    @Scope("prototype")
    public DataSource dataSourceDevelopment() {
        System.out.println("***********THIS IS DEV***********");

        final var url = environment.getProperty("db.url");
        final var driver = environment.getProperty("db.driver");
        final var username = environment.getProperty("db.username");
        final var password = environment.getProperty("db.password");

        DriverManagerDataSource dataSource = new DriverManagerDataSource();

        dataSource.setUrl(url);
        // org.postgresql.Driver
        dataSource.setDriverClassName(driver);
        dataSource.setUsername(username);
        dataSource.setPassword(password);

        return dataSource;
    }

    @Bean(value = "dataSourceJavaConfig")
    @Profile("prod")
    public DataSource dataSourceProduction() {
        System.out.println("***********THIS IS PROD***********");

        final var url = environment.getProperty("db.url");
        final var driver = environment.getProperty("db.driver");
        final var username = environment.getProperty("db.username");
        final var password = environment.getProperty("db.password");

        DriverManagerDataSource dataSource = new DriverManagerDataSource();

        dataSource.setUrl(url);
        // org.postgresql.Driver
        dataSource.setDriverClassName(driver);
        dataSource.setUsername(username);
        dataSource.setPassword(password);

        return dataSource;
    }

    @Bean
//    @DependsOn(value = {"dataSourceJavaConfig"})
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(
            DataSource dataSource
    ) {
        LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();

        factory.setDataSource(dataSource);
        factory.setPersistenceProviderClass(HibernatePersistenceProvider.class);
        factory.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        factory.setJpaProperties(jpaProperties());
        factory.setPackagesToScan("org.step");

//        1--- JDBC
//        2--- JPA
//        3--- Spring Data
        return factory;
    }

    @Bean
//    @DependsOn(value = {"entityManagerFactory"})
    public PlatformTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
        JpaTransactionManager jpaTransactionManager = new JpaTransactionManager();

        jpaTransactionManager.setEntityManagerFactory(entityManagerFactory);

        return jpaTransactionManager;
    }

    @Bean
    public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
        return new PersistenceExceptionTranslationPostProcessor();
    }

    private Properties jpaProperties() {
        Properties properties = new Properties();

        properties.put("hibernate.show_sql", "true");
        properties.put("hibernate.hbm2ddl.auto", "create-drop");
        properties.put("hibernate.format_sql", "true");
        properties.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQL10Dialect");
        properties.put("hibernate.jdbc.batch_size", "50");
        properties.put("hibernate.jdbc.fetch_size", "50");
        properties.put("hibernate.order_updates", "true");
        properties.put("hibernate.order_inserts", "true");

        return properties;
    }
}

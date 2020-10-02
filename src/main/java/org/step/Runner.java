package org.step;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.step.configuration.DatabaseConfiguration;
import org.step.spring.Animal;

import java.sql.SQLException;

public class Runner {

    public static void main(String[] args) throws SQLException {

        // test one
//
//        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
//
//        Validator validator = validatorFactory.getValidator();
//
//        validator.validate()

//        System.out.println("Hello world");
//
//        EntityManager entityManager = SessionFactoryCreator.getEntityManager();
//
//        Map<String, Object> properties = entityManager.getProperties();
//
//        System.out.println(properties);

//        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("beans.xml");
//
//        Foo foo = applicationContext.getBean("foo", Foo.class);
//
//        Bar bar = applicationContext.getBean("bar", Bar.class);
//
//        System.out.println(bar.getFoo().getiAmFoo());

        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(DatabaseConfiguration.class);
//
//        ProfileRepository profileRepository = applicationContext.getBean("profileRepositoryImpl", ProfileRepository.class);
//
//        System.out.println(profileRepository);
//
//        DataSource dataSource = applicationContext.getBean("dataSourceJavaConfig", DataSource.class);
//
//        LocalContainerEntityManagerFactoryBean entityManagerFactory = applicationContext.getBean("entityManagerFactory", LocalContainerEntityManagerFactoryBean.class);
//
//        System.out.println(entityManagerFactory);
//
//        Connection connection = dataSource.getConnection();
//
//        DatabaseMetaData metaData = connection.getMetaData();
//
//        System.out.println(metaData.getDatabaseProductName());

//        DataSource first = applicationContext.getBean("dataSourceJavaConfig", DataSource.class);
//
//        DataSource second = applicationContext.getBean("dataSourceJavaConfig", DataSource.class);
//
//        if (first == second) {
//            System.out.println("They are the same");
//        } else {
//            System.out.println("They are different beans");
//        }

        Animal animal = applicationContext.getBean("animal", Animal.class);

        System.out.println(animal.getClass().getSimpleName());

        String sound = animal.getSound();
    }


}

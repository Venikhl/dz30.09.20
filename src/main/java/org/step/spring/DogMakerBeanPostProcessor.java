package org.step.spring;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.lang.reflect.Field;

@Component
public class DogMakerBeanPostProcessor implements BeanPostProcessor {

//    @Autowired
//    private JpaTransactionManager jpaTransactionManager;
//
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        final String dogSound = "bark";

        Class<?>[] interfaces = bean.getClass().getInterfaces();



        if (bean.getClass().isAssignableFrom(Animal.class)) {
            System.out.println(beanName);
            Field[] declaredFields = bean.getClass().getDeclaredFields();
            for (Field fd : declaredFields) {
                fd.setAccessible(true);
                String fieldName = fd.getName();
                if (fieldName.equalsIgnoreCase("sound")) {
                    try {
                        fd.set(bean, dogSound);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (bean.getClass().isAssignableFrom(Animal.class)) {

            Enhancer enhancer = new Enhancer();

            enhancer.setSuperclass(Animal.class);

            enhancer.setCallback((MethodInterceptor) (obj, method, args, proxy) -> {
                if (method.isAnnotationPresent(GetSoundDetector.class)) {

                    System.out.println("Before proxy");

                    Object o = proxy.invokeSuper(obj, args);

                    System.out.println("After proxy");

                    return o;
                } else {
                    return proxy.invokeSuper(obj, args);
                }
            });

            Animal proxyAnimal = (Animal) enhancer.create();

//            Field sound = null;
//            try {
//                sound = bean.getClass().getDeclaredField("sound");
//
//                sound.setAccessible(true);
//
//                String animalSound = (String) sound.get(bean);
//
//                System.out.println(animalSound);
//            } catch (NoSuchFieldException | IllegalAccessException e) {
//                e.printStackTrace();
//            }
//            System.out.println(beanName);
            System.out.println(proxyAnimal.getClass().getName());

            return proxyAnimal;
        }
        return bean;
    }
}

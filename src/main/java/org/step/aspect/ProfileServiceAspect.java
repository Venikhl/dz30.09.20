package org.step.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;
import org.step.entity.Profile;

import javax.persistence.PersistenceException;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Aspect
@Component
// Order (-1 - наивысшая степень и далее по возрастающей)
@Order(-1)
public class ProfileServiceAspect {

    public static AtomicInteger persistenceExceptionCounter = new AtomicInteger(0);

    private final PlatformTransactionManager platformTransactionManager;
    private final TransactionTemplate transactionTemplate;
    // = new TransactionTemplate(null)

    @Autowired
    public ProfileServiceAspect(PlatformTransactionManager platformTransactionManager) {
        this.platformTransactionManager = platformTransactionManager;
        this.transactionTemplate = new TransactionTemplate(platformTransactionManager);
    }

    /*
    1. * - тип возвращаемого значения, если * - любой тип, если не любой тип -
          (пишется полный путь) org.step.entity.Person;
    2. Params:
    () - no args
    * - one any type
    .. - 0 or more any type
    3. public или любой другой модификатор доступа не обязателен
     */

    /*
    @Pointcut for reuseability of expression
    @Before before method
    @After working always
    @AfterReturning only on success
    @AfterThrowing only on exception
    @Around working before and after method
     */
    @Pointcut(
            "execution(public org.step.entity.Profile org.step.service.CrudService.save(org.step.entity.Profile))"
    )
    public void saveMethodProfileServiceApplied() {}

    @Pointcut("execution(public * org.step.service.CrudService.save(..))")
    public void saveMethodForAllEntities() {}

    @Before("saveMethodProfileServiceApplied()")
    public void loggingSaveProfileMethods(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();

        Set<Profile> profiles = Stream.of(args)
                .filter(obj -> obj.getClass().isAssignableFrom(Profile.class))
                .map(obj -> (Profile) obj)
                .collect(Collectors.toSet());

        profiles
                .stream()
                .findFirst()
                .ifPresent(profile -> {
                    profile.setGraduation("Mega Giper Grad");
                    System.out.printf("@Before aspect is called %s%n", profile.getGraduation());
                });
    }

    /*
    @After отличается от @AfterReturning тем, что @After выполнится в любом случае,
    @AfterReturning выполняется только при успешном завершении метода
     */

    @After("saveMethodProfileServiceApplied()")
    public void afterSaveMethodProfileServiceApplied(JoinPoint joinPoint) {
        System.out.printf("@After aspect is called %s%n", joinPoint.getSignature().toShortString());
    }

    @AfterReturning(
            pointcut = "saveMethodProfileServiceApplied()",
            returning = "profile"
    )
    public void returningAfterSaveMethodProfileServiceApplied(JoinPoint joinPoint, Profile profile) {
        System.out.printf("@AfterReturning is called %s%n", joinPoint.getSignature().toShortString());

        profile.setGraduation("After returning graduation");

        System.out.println(profile.toString());
    }

    @AfterThrowing(
            pointcut = "saveMethodProfileServiceApplied()",
            throwing = "ex"
    )
    public void throwingAfterSaveMethodProfileServiceApplied(JoinPoint joinPoint, Throwable ex) {
        System.out.printf(
                "Something went wrong in save method with signature %s%n",
                joinPoint.getSignature().toShortString()
        );
        System.out.println(ex.toString());
        if (ex.getClass().isAssignableFrom(PersistenceException.class)) {
            persistenceExceptionCounter.incrementAndGet();
        }
    }

    @Around(value = "saveMethodProfileServiceApplied()")
    public Object aroundSaveMethodProfileServiceApplied(ProceedingJoinPoint joinPoint) {
        long start = System.currentTimeMillis();

        Object execute = transactionTemplate.execute(status -> {
            try {
                return joinPoint.proceed();
            } catch (Throwable throwable) {
                System.out.println(throwable.toString());
                return null;
            }
        });

        long finish = System.currentTimeMillis();

        System.out.printf("Performance of this method is: %d", (finish - start));
        System.out.println();

        return execute;
    }
}

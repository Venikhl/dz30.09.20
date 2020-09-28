package org.step.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.step.entity.Profile;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Aspect
@Component
// Order (-1 - наивысшая степень и далее по возрастающей)
@Order(-1)
public class ProfileServiceAspect {

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
    @Pointcut for reusability of expression
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
                    System.out.printf("@Before aspect is called %s%n", profile.getGraduation());
                });
    }

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

        System.out.println(profile.toString());
    }
}

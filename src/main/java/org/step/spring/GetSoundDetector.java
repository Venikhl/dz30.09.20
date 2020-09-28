package org.step.spring;

import java.lang.annotation.*;

@Documented
@Target(value = {ElementType.METHOD})
@Retention(value = RetentionPolicy.RUNTIME)
public @interface GetSoundDetector {
}

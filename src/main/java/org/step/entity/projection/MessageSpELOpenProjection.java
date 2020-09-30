package org.step.entity.projection;

import org.springframework.beans.factory.annotation.Value;

public interface MessageSpELOpenProjection {

    @Value("#{target.id + ' ' + target.message}")
    String getMessageWithId();
}

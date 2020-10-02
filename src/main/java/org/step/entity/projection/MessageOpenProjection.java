package org.step.entity.projection;

public interface MessageOpenProjection {

    Long getId();
    String getMessage();

    default String getMessageWithIdDescription() {
        return String.format("Message id: %d and message is %s", this.getId(), this.getMessage());
    }
}

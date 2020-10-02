package org.step.entity.projection;

/*
Основной объект Message
 */
public interface MessageProjection {
    /*
    getMessage() - должен быть у Message
     */
    String getMessage();

    /* метод getUser() у Message */
    MessageUserProjection getUser();

    /*
    Вложенный объект User
     */
    interface MessageUserProjection {
        /*
        getUsername() - должен быть у User
         */
        String getUsername();
    }
}

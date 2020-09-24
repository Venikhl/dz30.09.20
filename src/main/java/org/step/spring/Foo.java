package org.step.spring;

//@Service
/*
если scope = singleton, class Foo создается 1 раз за весь цикл жизни приложения
и потом производится его внедрение при помощи BeanPostProcessor'ов
 */
public class Foo {

    private String iAmFoo = "Foo";

    public String getiAmFoo() {
        return iAmFoo;
    }

    public void setiAmFoo(String iAmFoo) {
        this.iAmFoo = iAmFoo;
    }
}

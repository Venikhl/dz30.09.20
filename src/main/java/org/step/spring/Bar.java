package org.step.spring;

public class Bar {

//    @Autowired
    private Foo foo;

    public Bar(Foo foo) {
        this.foo = foo;
    }

    public Foo getFoo() {
        return foo;
    }

    public void setFoo(Foo foo) {
        this.foo = foo;
    }
}

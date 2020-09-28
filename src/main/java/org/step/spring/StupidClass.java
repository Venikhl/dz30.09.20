package org.step.spring;

import java.io.IOException;
import java.io.Serializable;

public class StupidClass implements Serializable {

    private void writeObject(java.io.ObjectOutputStream out) throws IOException {
        out.writeObject(new StupidClass());
    }

    private void readObject(java.io.ObjectInputStream in) throws IOException, ClassNotFoundException {
        Object o = in.readObject();
    }
}

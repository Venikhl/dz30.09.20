package org.step.spring;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@AnimalWorld
@Component
public class Animal {

    private String sound = "kukareku";

    public Animal() {
        System.out.println("Animal constructor");
    }

    @PostConstruct
    public void init() {
        System.out.println("Init method");
        this.sound = null;
    }

    @PreDestroy
    public void destroy() {
        System.out.println("Destroy");
    }

    @GetSoundDetector
    public String getSound() {
        System.out.println(sound);
        return sound;
    }

    public void setSound(String sound) {
        this.sound = sound;
    }
}

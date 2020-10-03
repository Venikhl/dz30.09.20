package org.step.entity;

import javax.persistence.*;

@Entity
// do not write user
@Table(name = "speakers")
public class Speaker {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "model")
    private String model;

    public Speaker() {
    }

    public Speaker(Long id, String name, String model) {
        this.id = id;
        this.name = name;
        this.model = model;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public static Speaker.SpeakerBuilder builder() {
        return new Speaker.SpeakerBuilder();
    }

    public static class SpeakerBuilder {
        private Long id;
        private String name;
        private String model;

        private SpeakerBuilder() {
        }

        public Speaker.SpeakerBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public Speaker.SpeakerBuilder name(String name) {
            this.name = name;
            return this;
        }

        public Speaker.SpeakerBuilder model(String model) {
            this.model = model;
            return this;
        }

        public Speaker build() {
            return new Speaker(id, name, model);
        }
    }
}

package org.step.entity;

import javax.persistence.*;

import static org.step.entity.User.USER_MESSAGE_GRAPH;

@Entity
@Table(name = "speakers")
@NamedEntityGraph(
        name = SPEAKER_MUSIC_GRAPH,
        attributeNodes = {
                @NamedAttributeNode(value = Speaker_.MUSIC)
        }
)
public class Speaker {

    public static final String SPEAKER_MUSIC_GRAPH = "Speaker[music]";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "model")
    private String model;

    @OneToOne(
            mappedBy = "speaker",
            cascade = {CascadeType.PERSIST, CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH},
            fetch = FetchType.LAZY
    )
    private Music music;

    public Speaker() {
    }

    public Speaker(Long id, String name, String model, Music music) {
        this.id = id;
        this.name = name;
        this.model = model;
        this.music = music;
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

    public Music getMusic() {
        return music;
    }

    public void setMusic(Music music) {
        this.music = music;
    }

    public static Speaker.SpeakerBuilder builder() {
        return new Speaker.SpeakerBuilder();
    }

    public static class SpeakerBuilder {
        private Long id;
        private String name;
        private String model;
        private Music music;

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

        public Speaker.SpeakerBuilder music(Music music) {
            this.music = music;
            return this;
        }

        public Speaker build() {
            return new Speaker(id, name, model, music);
        }
    }
}

package org.step.entity;

import javax.persistence.*;

import static org.step.entity.Message.USER_ENTITY_GRAPH;

@Entity
// do not write user
@Table(name = "musics")
//@NamedEntityGraphs({
//        @NamedEntityGraph(
//                name = SPEAKER_ENTITIE_GRAPH,
//                attributeNodes = @NamedAttributeNode(value = "speaker")
//        )
//})
public class Music {

    //public static final String SPEAKER_ENTITIE_GRAPH = "Music.findSpeaker";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "speaker_id"
    )
    private Speaker speaker;

    public Music() {
    }

    public Music(Long id, String name, Speaker speaker) {
        this.id = id;
        this.name = name;
        this.speaker = speaker;
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

    public Speaker getSpeaker() {
        return speaker;
    }

    public void setSpeaker(Speaker speaker) {
        this.speaker = speaker;
    }

    public static Music.MusicBuilder builder() {
        return new Music.MusicBuilder();
    }

    public static class MusicBuilder {
        private Long id;
        private String name;
        private Speaker speaker;

        private MusicBuilder() {
        }

        public Music.MusicBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public Music.MusicBuilder name(String name) {
            this.name = name;
            return this;
        }

        public Music.MusicBuilder name(Speaker speaker) {
            this.speaker = speaker;
            return this;
        }

        public Music build() {
            return new Music(id, name, speaker);
        }
    }
}

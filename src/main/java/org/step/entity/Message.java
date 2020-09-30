package org.step.entity;

import javax.persistence.*;
import java.util.Objects;

import static org.step.entity.Message.USER_ENTITY_GRAPH;

@Entity
@Table(name = "messages")
@NamedEntityGraphs({
        @NamedEntityGraph(
                name = USER_ENTITY_GRAPH,
                attributeNodes = @NamedAttributeNode(value = "user")
        )
})
public class Message {

    public static final String USER_ENTITY_GRAPH = "Message.findUser";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "message")
    private String message;

    @Column(name = "date")
    private String date;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "user_id",
            foreignKey = @ForeignKey(name = "message_user_fk")
    )
    private User user;

    public Message() {
    }

    private Message(Long id, String message, String date) {
        this.id = id;
        this.message = message;
        this.date = date;
    }

    public static MessageBuilder builder() {
        return new MessageBuilder();
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public static class MessageBuilder {
        private Long id;
        private String message;
        private String date;

        MessageBuilder() {
        }

        public MessageBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public MessageBuilder message(String message) {
            this.message = message;
            return this;
        }

        public MessageBuilder date(String date) {
            this.date = date;
            return this;
        }

        public Message build() {
            return new Message(id, message, date);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Message message1 = (Message) o;
        return Objects.equals(id, message1.id) &&
                Objects.equals(message, message1.message);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, message);
    }

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", message='" + message + '\'' +
                '}';
    }
}

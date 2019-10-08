package model;

import java.util.Date;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

    @Entity
    @Table(name = "tweet")
    public class Tweet {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "id")
        private long id;

        @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
        @JoinColumn(name = "id_author")
        private User author;

        @Column(name = "published_at")
        private Date publishedAt;

        @Column(name = "message")
        private String message;

        public Tweet() {
        }

        public long getId() {
            return id;
        }

        public User getAuthor() {
            return author;
        }

        public void setAuthor(User author) {
            this.author = author;
        }

        public Date getPublishedAt() {
            return publishedAt;
        }

        public void setPublishedAt(Date publishedAt) {
            this.publishedAt = publishedAt;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public void setId(long id) {
            this.id = id;
        }
    }


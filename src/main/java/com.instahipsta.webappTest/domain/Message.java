package com.instahipsta.webappTest.domain;


import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
public class Message {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    @NotBlank(message = "The message field cannot be empty")
    @Length(max = 2048, message = "Message cannot be more then 2kB")
    private String text;
    @Length(max = 255, message = "Tag cannot be more then 2kB")
    private String tag;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User author;

    private String filename;

    public Message() {}

    public Message(String text, String tag, User author) {
        this.text = text;
        this.tag = tag;
        this.author = author;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getText() { return text; }
    public void setText(String text) { this.text = text; }
    public String getTag() { return tag; }
    public void setTag(String tag) { this.tag = tag; }
    public User getAuthor() { return author; }
    public void setAuthor(User author) { this.author = author; }
    public String getAuthorName() { return author.getUsername(); }
    public String getFilename() { return filename; }
    public void setFilename(String filename) { this.filename = filename; }
}

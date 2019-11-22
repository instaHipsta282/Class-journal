package com.instahipsta.webappTest.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table
public class Schedule {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "scheduleSequence")
    @SequenceGenerator( name = "scheduleSequence", sequenceName = "schedule_id_seq", allocationSize = 1)
    private Long id;

    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate date;

    @ManyToOne(targetEntity = User.class)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(targetEntity = Course.class)
    @JoinColumn(name = "course_id")
    private Course course;

    @Enumerated(EnumType.STRING)
    private PresenceStatus presenceStatus = PresenceStatus.NONE;

    @Enumerated(EnumType.STRING)
    private Score score = Score.NONE;

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public LocalDate getDate() { return date; }

    public void setDate(LocalDate date) { this.date = date; }

    public User getUser() { return user; }

    public void setUser(User user) { this.user = user; }

    public Course getCourse() { return course; }

    public void setCourse(Course course) { this.course = course; }

    public PresenceStatus getPresenceStatus() { return presenceStatus; }

    public void setPresenceStatus(PresenceStatus presenceStatus) { this.presenceStatus = presenceStatus; }

    public Score getScore() { return score; }

    public void setScore(Score score) { this.score = score; }
}

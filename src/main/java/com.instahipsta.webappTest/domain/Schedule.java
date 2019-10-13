package com.instahipsta.webappTest.domain;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table
public class Schedule {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private LocalDate date;

    @ManyToOne(targetEntity = User.class)
    @JoinColumn(name = "user_id")
    private User student;

    @ManyToOne(targetEntity = Course.class)
    @JoinColumn(name = "course_id")
    private Course course;

    private int score;

    private boolean presence;

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public LocalDate getDate() { return date; }

    public void setDate(LocalDate date) { this.date = date; }

    public User getStudent() { return student; }

    public void setStudent(User student) { this.student = student; }

    public Course getCourse() { return course; }

    public void setCourse(Course course) { this.course = course; }

    public int getScore() { return score; }

    public void setScore(int score) { this.score = score; }

    public boolean isPresence() { return presence; }

    public void setPresence(boolean presence) { this.presence = presence; }


}

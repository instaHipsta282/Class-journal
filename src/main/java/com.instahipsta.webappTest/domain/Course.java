package com.instahipsta.webappTest.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "course",
        uniqueConstraints = @UniqueConstraint(columnNames = {"title", "startDate", "endDate"})
)
public class Course implements Serializable{

    private static final long serialVersionUID = -7100745683715978956L;

    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "courseSequence")
    @SequenceGenerator( name = "courseSequence", sequenceName = "course_id_seq", allocationSize = 10)
    private Long id;

    private String title;

    private LocalDate startDate;

    private LocalDate endDate;

    private int daysCount;

    private int studentsCount;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "course_usr",
            joinColumns = {@JoinColumn(name = "course_id")},
            inverseJoinColumns = {@JoinColumn(name = "user_id")}
    )
    private Set<User> students = new HashSet<>();

    @Column(columnDefinition = "VARCHAR(1000) DEFAULT 'Description for this course has not yet been added.'")
    private String description;

    @Column(columnDefinition = "VARCHAR(255) DEFAULT 'courseDefaultImage.jpg'")
    private String image;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public int getDaysCount() {
        return daysCount;
    }

    public void setDaysCount(int dayCount) {
        this.daysCount = dayCount;
    }

    public int getStudentsCount() {
        return studentsCount;
    }

    public void setStudentsCount(int studentsCount) {
        this.studentsCount = studentsCount;
    }

    public Set<User> getStudents() {
        return students;
    }

    public void setStudents(Set<User> students) {
        this.students = students;
    }

    public String getDescription() { return description; }

    public void setDescription(String description) { this.description = description; }

    public static long getSerialVersionUID() { return serialVersionUID; }

    public String getImage() { return image; }

    public void setImage(String image) { this.image = image; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Course course = (Course) o;
        return id.equals(course.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}

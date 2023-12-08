package com.example.spring3security6docker.dao.entity;

import com.example.spring3security6docker.dto.request.CourseCreateRequestDto;
import lombok.*;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "course", schema = "courses")
@NoArgsConstructor
@AllArgsConstructor
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Course extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1435515995276255188L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "author")
    private String author;

    @Column(name = "name")
    private String name;

    public Course(CourseCreateRequestDto courseCreateRequestDto) {
        this.author = courseCreateRequestDto.getAuthor();
        this.name = courseCreateRequestDto.getName();
        this.setCreated(new Date());
        this.setUpdated(new Date());
        this.setStatus(Status.NOT_ACTIVE);
    }

    @Override
    public String toString() {
        return "Course{" +
                "id=" + id +
                ", author='" + author + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}

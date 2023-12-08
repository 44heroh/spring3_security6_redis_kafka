package com.example.spring3security6docker.dto;

import com.example.spring3security6docker.dao.entity.Status;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CourseDto implements Serializable {
//    private static final long serialVersionUID = 1435515995276255188L;

    private Long id;
    @NotNull(message = "Название не может быть пустым")
    private String author;
    @NotNull(message = "Название не может быть пустым")
    private String name;
    @JsonProperty("created")
    @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss")
    private Date created;
    @JsonProperty("updated")
    @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss")
    private Date updated;
    private Status status;

    @Override
    public String toString() {
        return "CourseDto{" +
                "id=" + id +
                ", author='" + author + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}

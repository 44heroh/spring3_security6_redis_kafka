package com.example.spring3security6docker.dto;

import lombok.*;

import java.io.Serializable;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PagerQueryDto implements Serializable {
//    private static final long serialVersionUID = 1435515995276255188L;

    private String sortBy;
    private String sortDirection;
    private Integer page;
    private Integer pageSize;

    @Override
    public String toString() {
        return "PagerQueryDto{" +
                "sortBy='" + sortBy + '\'' +
                ", sortDirection='" + sortDirection + '\'' +
                ", page=" + page +
                ", pageSize=" + pageSize +
                '}';
    }
}

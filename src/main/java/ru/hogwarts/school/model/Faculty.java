package ru.hogwarts.school.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Collection;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Setter @Getter
@Entity
public class Faculty {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String color;
    @OneToMany(mappedBy = "faculty")
    private Collection<Student> students;
}

package org.jetbrains.assignment.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.Hibernate;

import java.util.Objects;

@Entity
@Table(name = "coordinates")
@Getter
@Setter
public class CoordinateEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "coordinates_gen")
    @SequenceGenerator(name = "coordinates_gen", sequenceName = "coordinates_id_seq")
    private Long id;

    private Long x;
    private Long y;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        CoordinateEntity that = (CoordinateEntity) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

}

package br.com.alac.entities;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;

import java.util.List;

@Entity
public class Person extends PanacheEntity {

    public String name;
    public int yearOfBirthday;


    public static List<Person> findByYearOfBirthday(int yearOfBirthday) {
        return find("yearOfBirthday", yearOfBirthday).list();
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", yearOfBirthday=" + yearOfBirthday +
                ", name='" + name + '\'' +
                '}';
    }
}

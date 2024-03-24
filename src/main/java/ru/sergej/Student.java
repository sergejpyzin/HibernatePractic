package ru.sergej;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Random;

@Entity
@Table (name = "students")
@Getter
@Setter
@NoArgsConstructor
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "age")
    private Integer age;


    public Student(String firstName, String lastName, Integer age) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
    }

    public static String randomFirstName() {
        String[] nameArray = {"Иван", "Петр", "Александр", "Сергей", "Михаил", "Андрей", "Алексей", "Дмитрий", "Николай", "Егор"};
        Random random = new Random();

        int index = random.nextInt(nameArray.length);

        return nameArray[index];
    }

    public static String randomLastName() {
        String[] lastNameArray = {"Иванов", "Петров", "Сидоров", "Козлов", "Николаев", "Васильев", "Зайцев", "Павлов", "Семенов", "Голубев"};
        Random random = new Random();

        int index = random.nextInt(lastNameArray.length);

        return lastNameArray[index];
    }


    @Override
    public String toString() {
        return String.format("Студент id %d:\nИмя: %s, Фамилия: %s, Возраст: %d\n", id, firstName, lastName, age);
    }
}

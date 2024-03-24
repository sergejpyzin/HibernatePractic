package ru.sergej;


import jakarta.persistence.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.List;
import java.util.Random;

public class Main {
    public static void main(String[] args) {

        Configuration configuration = new Configuration().configure();
        try (SessionFactory sessionFactory = configuration.buildSessionFactory();
             Session session = sessionFactory.openSession()) {
            insertStudent(session);
            findStudentByID(session, 3L);
            persistStudent(session, 11L);

            readStudents(session);
        }
    }

    private static void insertStudent(Session session) {
        Random random = new Random();
        Transaction transaction = session.beginTransaction();

        for (long i = 1; i <= 10; i++) {

            Student student = new Student();

            String firstName = Student.randomFirstName();
            String lastName = Student.randomLastName();

            student.setId(i);
            student.setFirstName(firstName);
            student.setLastName(lastName);
            student.setAge(random.nextInt(17, 25));

            session.merge(student);
        }

        transaction.commit();

    }


}
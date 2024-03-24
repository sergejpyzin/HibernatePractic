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
            findStudentById(session, 3L);

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

    private static void findStudentById(Session session, Long id) {
        Student student = session.find(Student.class, id);

        if (student != null) {
            System.out.println("Найден студент с id " + id + ":\n" + student);
        } else {
            System.out.println("Студент с id " + id + " не найден!");
        }
    }

    private static void persistStudent(Session session, Long id) {
        Random random = new Random();
        Student student = new Student();

        session.beginTransaction();
        session.saveOrUpdate(student);
        session.getTransaction().commit();

        student.setId(id);
        student.setAge(random.nextInt(17, 25));
        student.setLastName(Student.randomLastName());
        student.setFirstName(Student.randomFirstName());

        session.beginTransaction();
        session.persist(student);
        session.getTransaction().commit();

        System.out.println("Добавлен новый студент:\n" + student);
    }





}
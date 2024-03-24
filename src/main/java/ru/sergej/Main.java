package ru.sergej;


import jakarta.persistence.Query;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
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
            persistStudent(session);
            removeStudent(session, 10L);
            findStudentsOlderThanAge(session, 20);


        }
    }

    private static void insertStudent(Session session) {
        Random random = new Random();
        Transaction transaction = session.beginTransaction();

        for (long i = 1; i <= 10; i++) {

            Student student = new Student();

            String firstName = Student.randomFirstName();
            String lastName = Student.randomLastName();

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

    private static void persistStudent(Session session) {
        Random random = new Random();
        Student student = new Student();

        session.beginTransaction();
        session.saveOrUpdate(student);
        session.getTransaction().commit();

//        student.setId(id);
        student.setAge(random.nextInt(17, 25));
        student.setLastName(Student.randomLastName());
        student.setFirstName(Student.randomFirstName());

        session.beginTransaction();
        session.persist(student);
        session.getTransaction().commit();

        System.out.println("Добавлен новый студент:\n" + student);
    }

    private static void removeStudent(Session session, Long id) {
        session.beginTransaction();
        Student student = session.find(Student.class, id);
        session.getTransaction().commit();

        if (student != null) {
            session.beginTransaction();
            session.remove(student);
            session.getTransaction().commit();

            System.out.println("Студент удален: " + student);
        } else {
            System.out.println("Студент с id 1 не найден");
        }
    }

    private static void readStudents(Session session) {
        String hql = "FROM Student"; // HQL запрос для выбора всех записей из таблицы Student

        Query query = session.createQuery(hql, Student.class);
        List<Student> students = query.getResultList();

        // Выводим информацию о каждом студенте
        for (Student student : students) {
            System.out.println(student);
        }
    }

    private static void findStudentsOlderThanAge(Session session, Integer age) {
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Student> criteriaQuery = builder.createQuery(Student.class);
        Root<Student> root = criteriaQuery.from(Student.class);
        criteriaQuery.select(root)
                .where(builder.gt(root.get("age"), 20));

        List<Student> students = session.createQuery(criteriaQuery).getResultList();
        System.out.println("Студенты старше 20 лет:");
        for (Student student : students) {
            System.out.println(student);
        }
    }



}
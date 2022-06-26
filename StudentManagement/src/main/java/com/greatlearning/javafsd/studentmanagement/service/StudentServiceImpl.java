package com.greatlearning.javafsd.studentmanagement.service;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.greatlearning.javafsd.studentmanagement.entity.Student;


@Repository
public class StudentServiceImpl implements StudentService {


	private Session session;

	@Autowired
	StudentServiceImpl(SessionFactory sessionFactory) {
		try {
			session = sessionFactory.getCurrentSession();
		} catch (HibernateException e) {
			session = sessionFactory.openSession();
		}

	}

	@Transactional
	public List<Student> listAll() {

		Transaction transaction = session.beginTransaction();

		List<Student> students 
			= session.createQuery("from student_entity").list();

		transaction.commit();
		return students;
	}

	@Transactional
	public Student findById(int id) {

		Student student = new Student();

		Transaction transaction = session.beginTransaction();
		student = session.get(Student.class, id);
		transaction.commit();

		return student;
	}

	@Transactional
	public void save(Student student) {

		Transaction transaction = session.beginTransaction();

		session.saveOrUpdate(student);

		transaction.commit();
	}

	@Transactional
	public void deleteById(int id) {

		Transaction transaction = session.beginTransaction();

		Student student = session.get(Student.class, id);

		session.delete(student);

		transaction.commit();
	}
}
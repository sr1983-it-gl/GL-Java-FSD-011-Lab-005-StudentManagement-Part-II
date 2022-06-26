package com.greatlearning.javafsd.studentmanagement.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.greatlearning.javafsd.studentmanagement.entity.Student;
import com.greatlearning.javafsd.studentmanagement.service.StudentService;

@Controller
@RequestMapping("/student")
public class StudentController {

	@Autowired
	private StudentService studentService;

	@RequestMapping("/begin-add")
	public String handleBeginAdd(Model theModel) {

		Student student = new Student();

		theModel.addAttribute("student", student);

		return "student-details";
	}

	@RequestMapping("/begin-update")
	public String handleBeginUpdate(
			@RequestParam("studentId") int theId, 
			Model theModel) {

		Student student = studentService.findById(theId);
		theModel.addAttribute("student", student);
		return "student-details";
	}

	@PostMapping("/save")
	public String handleSave(
		@RequestParam("id") int id, 
		@RequestParam("firstName") String firstName,
		@RequestParam("lastName") String lastName, 		
		@RequestParam("course") String course,
		@RequestParam("country") String country) {

		Student student;
		if (id != 0) {
			
			// Update Student
			student = studentService.findById(id);
			student.setFirstName(firstName);
			student.setLastName(lastName);
			student.setCourse(course);
			student.setCountry(country);
		} else {
		
			// Add Student
			student = new Student(firstName, lastName, course, country);
		}	
		studentService.save(student);
		return "redirect:/students/list";

	}

	@RequestMapping("/delete")
	public String handleDelete(@RequestParam("studentId") int theId) {

		studentService.deleteById(theId);

		return "redirect:/students/list";

	}

}

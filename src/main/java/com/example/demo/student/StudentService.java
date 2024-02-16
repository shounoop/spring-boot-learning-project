package com.example.demo.student;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    // @Autowired is to tell Spring to inject the StudentRepository dependency into the StudentService class for us so that we can use it.
    @Autowired
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public List<Student> getStudents() {
        return studentRepository.findAll();
    }

    public void addNewStudent(Student student) {
        studentRepository.findStudentByEmail(student.getEmail())
                .ifPresentOrElse(s -> {
                    throw new IllegalStateException("email taken" );
                }, () -> {
                    studentRepository.save(student);
                });
    }

    public void deleteStudent(Long studentId) {
        boolean exists = studentRepository.existsById(studentId);
        if (!exists) {
            throw new IllegalStateException("student with id " + studentId + " does not exist" );
        }
        studentRepository.deleteById(studentId);
    }
}

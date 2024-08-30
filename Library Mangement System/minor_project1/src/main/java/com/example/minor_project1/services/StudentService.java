package com.example.minor_project1.services;

import com.example.minor_project1.dto.CreateStudentRequest;
import com.example.minor_project1.dto.UpdateStudentRequest;
import com.example.minor_project1.model.Student;
import com.example.minor_project1.repository.StudentRepositary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentService {

    @Autowired
    StudentRepositary studentRepositary;

    public Student create(CreateStudentRequest createStudentRequest){
        Student student=createStudentRequest.to();
        return studentRepositary.save(student);
    }

    public Student get(int studentId) {
        return studentRepositary.findById(studentId).orElse(null);
    }

    public Student delete(int studentId) {
        Student student=this.get(studentId);
        studentRepositary.deleteById(studentId);
        return student;
    }

    public Student update(int studentId, UpdateStudentRequest updateStudentRequest) {
        return null;
    }
}

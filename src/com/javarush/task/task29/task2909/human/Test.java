package com.javarush.task.task29.task2909.human;

import java.util.ArrayList;
import java.util.List;

public class Test {
    public static void main(String[] args) {
        Student student1 = new Student("Pasha", 15, 5.87);
    Student student2 = new Student("Masha", 1, 15.87);
    Student student3 = new Student("Sasha", 143, 56.87);
    University university = new University("Byrsitet", 145);
    List<Student> students = new ArrayList<>();
        students.add(student1);
        students.add(student2);
        students.add(student3);
        university.setStudents(students);
        System.out.println(university.getStudentWithAverageGrade(15.87).name);
}
}

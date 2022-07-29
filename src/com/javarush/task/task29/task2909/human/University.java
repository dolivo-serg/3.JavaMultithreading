package com.javarush.task.task29.task2909.human;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class University {

    private String name;
    private int age;
    private List<Student> students = new ArrayList<>();


    public University(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    public Student getStudentWithAverageGrade(double averageGrade) {
        //TODO:
        for(Student student : students){
            if(student.getAverageGrade() == averageGrade)
                return student;
        }
        return null;
    }

    Comparator<Student> averageGradeComparator = new Comparator<Student>() {
        @Override
        public int compare(Student o1, Student o2) {
            return (int) (o2.getAverageGrade() - o1.getAverageGrade());
        }
    };

    public Student getStudentWithMaxAverageGrade() {
        //TODO:
        students.sort(averageGradeComparator);
        return students.get(0);
    }

    public Student getStudentWithMinAverageGrade() {
        //TODO:
        students.sort(averageGradeComparator);
        return students.get(students.size() - 1);
    }

    public void expel(Student student){
        students.remove(student);
    }

}
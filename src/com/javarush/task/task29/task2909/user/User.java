package com.javarush.task.task29.task2909.user;

public class User {
    private String name;
    private String surname;
    private int age;
    private boolean man;
    
    private Address address;
    
    private Work work;

    public boolean isMan() {
        return man;
    }

    public void setMan(boolean man) {
        this.man = man;
    }

    public String getCountry() {
        return address.getCountry();
    }

    public void setCountry(String country) {
        address.setCountry(country);
    }

    public String getCity() {
        return address.getCity();
    }

    public void setCity(String city) {
        address.setCity(city);
    }

    public String getAddress() {
        return address.getCountry() + " " + address.getCity() + " " + address.getHouse();
    }


   

    public User(String name, String surname, int age) {
        this.name = name;
        this.surname = surname;
        this.age = age;
    }
    public void printInfo(){
        System.out.println("Имя: " + getName() + "\n" +
        "Фамилия: " + getSurname());
    }

    public void printAdditionalInfo() {
        if (this.getAge() < 16)
            System.out.println("Пользователь моложе 16 лет");
        else
            System.out.println("Пользователь старше 16 лет");
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Work getWork() {
        return work;
    }

    public void setWork(Work work) {
        this.work = work;
    }

    //    Необходимо добавить в класс User метод getBoss() и реализовать этот метод.
//8. Необходимо изменить реализацию метода getBossName(User user)
//   класса UserHelper (используй метод getBoss() класса User).

//        Проверь, что метод getBoss() класса User вызывает метод getBoss() у объекта типа Work


    public String getBoss() {
        return work.getBoss();
    }
}
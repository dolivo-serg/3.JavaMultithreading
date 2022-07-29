package com.javarush.task.task36.task3608.dao;

import com.javarush.task.task36.task3608.bean.User;
import com.javarush.task.task36.task3608.dao.mock.DataSource;

import java.util.ArrayList;
import java.util.List;

/*//---------Эмуляция базы данных---------
UserDao - это уровень ДАО, т.е. уровень доступа к базе.
В нем размещают различные методы по сохранению и получению объектов
 из базы данных.
В реальном приложении строку private DataSource dataSource = DataSource.getInstance()
 не встретить.
Я реализовал DataSource в виде синглтона.
В действительности, у тебя будет что-то такое:
@Autowired
private DataSource dataSource;

Фреймворк, которым ты будешь пользоваться, сам создаст объект
 базы данных и инициализирует поле dataSource.

Запомни, с ДАО уровнем работают сервисы. Никакие другие классы в
ДАО не лезут. В сервисах описана бизнес логика.
Сервисы забирают данные из базы используя ДАО, обрабатывают их и
отдают тому, кто данные запросил.
Однако не все данные хранятся в базе. Например, залогиненый пользователь
будет храниться в специальном объекте - Модели.
Объект, который содержит в себе данные, необходимые для отображения
информации на клиенте, называется Моделью.
Также этот объект Модель содержит ссылки на все необходимые сервисы.
Если данных для отображения очень много, то их выделяют в отдельный объект.*/

public class UserDao {
    private DataSource dataSource = DataSource.getInstance();

    public User getUserById(long id) {
        List<User> users = dataSource.getUsers();
        for (User user : users) {
            if (user.getId() == id) {
                return user.clone();
            }
        }
        return User.NULL_USER;
    }

    public List<User> getUsersByName(String name) {
        if (name == null || name.trim().isEmpty()) throw new IllegalArgumentException();

        List<User> users = dataSource.getUsers();
        List<User> result = new ArrayList<>();
        for (User user : users) {
            if (name.equals(user.getName())) {
                addUserToResult(result, user);
            }
        }
        return result;
    }

    public List<User> getAllUsers() {
        List<User> users = dataSource.getUsers();
        List<User> result = new ArrayList<>();
        for (User user : users) {
            addUserToResult(result, user);
        }
        return result;
    }

    public List<User> getUsersByLevel(int level) {
        if (level < 1) throw new IllegalArgumentException();

        List<User> users = dataSource.getUsers();
        List<User> result = new ArrayList<>();

        for (User user : users) {
            if (level == user.getLevel()) {
                addUserToResult(result, user);
            }
        }
        return result;
    }

    public void addUserToResult(List<User> result, User user) {
        User clone = user.clone();

        //skip bad users
        if (clone != User.NULL_USER) {
            result.add(clone);
        }
    }

    public User createOrUpdate(User user) {
        return dataSource.createOrUpdate(user);
    }

    public User getUsersById(long userId) {
        if (userId < 1) throw new IllegalArgumentException();

        List<User> users = dataSource.getUsers();
        for (User user : users) {
            if (userId == user.getId()) {
                return user;
            }
        }
        return User.NULL_USER;
    }
}
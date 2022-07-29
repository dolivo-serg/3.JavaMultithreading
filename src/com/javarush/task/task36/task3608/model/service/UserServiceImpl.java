package com.javarush.task.task36.task3608.model.service;

import com.javarush.task.task36.task3608.Util;
import com.javarush.task.task36.task3608.bean.User;
import com.javarush.task.task36.task3608.dao.UserDao;

import java.util.ArrayList;
import java.util.List;

//сервис для работы с пользователями.
//Модель обращается к сервисам
public class UserServiceImpl implements UserService {
    private UserDao userDao = new UserDao();

    //удаление пользователя
    @Override
    public User deleteUser(long id) {
        User user = userDao.getUserById(id);
        Util.markDeleted(user);
        userDao.createOrUpdate(user);

        return user;
    }

    @Override
    public User createOrUpdateUser(String name, long id, int level) {
        User user = new User(name, id, level);
        return userDao.createOrUpdate(user);
    }


    //    метод, который возвращает всех удаленных пользователей.
    @Override
    public List<User> getAllDeletedUsers() {
        List<User> result = new ArrayList<>();
        for (User user : userDao.getAllUsers()) {
            if (Util.isUserDeleted(user)) {
                result.add(user);
            }
        }

        return result;
    }

    @Override
    public List<User> getUsersByName(String name) {
        return userDao.getUsersByName(name);
    }

    @Override
    public List<User> getUsersBetweenLevels(int fromLevel, int toLevel) {
        // лучше получить всех пользователей из DAO одним запросом
        // к БД, но мы будем использовать то, что у нас есть
        List<User> result = new ArrayList<>();
        for (int i = fromLevel; i <= toLevel; i++) {
            result.addAll(userDao.getUsersByLevel(i));
        }

        return result;
    }

    @Override
    public List<User> filterOnlyActiveUsers(List<User> allUsers) {
        //не изменит список allUsers, а создаст новый вместо него
        List<User> result = new ArrayList<>();
        for (User user : allUsers) {
            if (User.NULL_USER != user && !Util.isUserDeleted(user)) {
                result.add(user);
            }
        }

        return result;
    }

    @Override
    public User getUsersById(long userId) {
        return userDao.getUsersById(userId);
    }
}
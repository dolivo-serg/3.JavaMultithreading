package com.javarush.task.task36.task3608.model;

import com.javarush.task.task36.task3608.bean.User;
import com.javarush.task.task36.task3608.model.service.UserService;
import com.javarush.task.task36.task3608.model.service.UserServiceImpl;

import java.util.List;

public class MainModel implements Model {
    private ModelData modelData = new ModelData();
    private UserService userService = new UserServiceImpl();

    @Override
    public ModelData getModelData() {
        return modelData;
    }

    @Override
    public void loadUsers() {
//        Достань всех пользователей между 1 и 100 уровнями.
//        Положи всех пользователей в modelData.
        List<User> users = getAllUsers();
        modelData.setUsers(users);
        modelData.setDisplayDeletedUserList(false);
    }

    //   метод, который возвращает всех удаленных пользователей.
    @Override
    public void loadDeletedUsers() {
        List<User> users = userService.getAllDeletedUsers();
        modelData.setUsers(users);
        modelData.setDisplayDeletedUserList(true);
    }

    //   Пользователь видит Вью со списком пользователей, нажимает
    //   на одного из них, запрос идет на сервер, выгребаем новые
    //   данные и отображаем другую Вью, которая относится к одному
    //   выбранному пользователю.
    @Override
    public void loadUserById(long userId) {
        User user = userService.getUsersById(userId);
        modelData.setActiveUser(user);
    }

//    напиши логику удаления пользователя.
    @Override
    public void deleteUserById(long id){
        userService.deleteUser(id);
        List<User> users = getAllUsers();
        modelData.setUsers(users);
    }

    //    обновления пользователя.
    @Override
    public void changeUserData(String name, long id, int level) {
        userService.createOrUpdateUser(name, id, level);
        List<User> users = getAllUsers();
        modelData.setUsers(users);
    }

    private List<User> getAllUsers() {
//        модель должна содержать всю бизнес-логику в методах
        List<User> allUsers = userService.getUsersBetweenLevels(1, 100);
        allUsers = userService.filterOnlyActiveUsers(allUsers);
        return allUsers;
    }
}
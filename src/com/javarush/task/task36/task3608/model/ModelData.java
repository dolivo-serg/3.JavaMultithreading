package com.javarush.task.task36.task3608.model;

/*      ModelData - это объект, который будет
  хранить необходимые данные для отображения на клиенте.*/

import com.javarush.task.task36.task3608.bean.User;

import java.util.List;

public class ModelData {
    private List<User> users; //это будет список пользователей для отображения.
    private User activeUser; //это будет выбранный пользователь для отображения.

//    список каких пользователей - удаленных или нет - выводится во view
//    чтобы Вью отображала эту информацию
    private boolean displayDeletedUserList;

    public boolean isDisplayDeletedUserList() {
        return displayDeletedUserList;
    }

    public void setDisplayDeletedUserList(boolean displayDeletedUserList) {
        this.displayDeletedUserList = displayDeletedUserList;
    }

    public User getActiveUser() {
        return activeUser;
    }

    public void setActiveUser(User activeUser) {
        this.activeUser = activeUser;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}

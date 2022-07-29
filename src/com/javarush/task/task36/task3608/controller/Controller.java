package com.javarush.task.task36.task3608.controller;


/*Этот класс будет получать запрос от клиента, оповещать Модель
об этом, а Модель, в свою очередь, будет обновлять ModelData.*/

import com.javarush.task.task36.task3608.model.Model;
import com.javarush.task.task36.task3608.view.EditUserView;
import com.javarush.task.task36.task3608.view.UsersView;

public class Controller {
    private Model model;
    private UsersView usersView;
    private EditUserView editUserView;

    public void setEditUserView(EditUserView editUserView) {
        this.editUserView = editUserView;
    }

    public void setModel(Model model) {
        this.model = model;
    }

    //должен обратиться к модели и инициировать загрузку пользователей
    public void onShowAllUsers(){
        //загрузили пользователей в модель
        model.loadUsers();

        //обновление данных во Вью.
        usersView.refresh(model.getModelData());
    }

    //данные, полученные с сервера, необходимо
    // положить в ModelData. А потом обновить view
    public void onShowAllDeletedUsers() {
        model.loadDeletedUsers();
        usersView.refresh(model.getModelData());
    }

    public void setUsersView(UsersView usersView) {
        this.usersView = usersView;
    }

    //   Пользователь видит Вью со списком пользователей, нажимает
    //   на одного из них, запрос идет на сервер, выгребаем новые
    //   данные и отображаем другую Вью, которая относится к одному
    //   выбранному пользователю.
    public void onOpenUserEditForm(long userId) {
        model.loadUserById(userId);
        editUserView.refresh(model.getModelData());
    }

    //    напиши логику удаления пользователя.
    public void onUserDelete(long id){
        model.deleteUserById(id);
        usersView.refresh(model.getModelData());
    }

    //    обновления пользователя.
    public void onUserChange(String name, long id, int level) {
        model.changeUserData(name, id, level);
        usersView.refresh(model.getModelData());
    }
}

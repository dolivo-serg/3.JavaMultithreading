package com.javarush.task.task36.task3608.model;

public interface Model {
    ModelData getModelData();
    void loadUsers();
    void loadDeletedUsers();

    //   Пользователь видит Вью со списком пользователей, нажимает
    //   на одного из них, запрос идет на сервер, выгребаем новые
    //   данные и отображаем другую Вью, которая относится к одному
    //   выбранному пользователю.
    void loadUserById(long userId);

    //    напиши логику удаления пользователя.
    //    После удаления должен отображаться список пользователей.
    void deleteUserById(long id);

    //    обновления пользователя.
    void changeUserData(String name, long id, int level);
}

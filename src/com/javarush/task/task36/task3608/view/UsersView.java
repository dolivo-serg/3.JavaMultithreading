package com.javarush.task.task36.task3608.view;

import com.javarush.task.task36.task3608.bean.User;
import com.javarush.task.task36.task3608.controller.Controller;
import com.javarush.task.task36.task3608.model.ModelData;

/*
    UsersView отображает список пользователей.
  Он будет отображать список пользователей в консоль.
*/
public class UsersView implements View {
    private Controller controller;


//    Выведи в консоль всех пользователей, которые есть в modelData
    @Override
    public void refresh(ModelData modelData) {
        //в зависимости от того, какие пользователи находятся в списке
        // удаленны или нет.
        if(modelData.isDisplayDeletedUserList())
            System.out.println("All deleted users:");
        else
        System.out.println("All users:");

        for(User user : modelData.getUsers()){
            System.out.println("\t" + user);
        }
        System.out.println("===================================================");
    }

    @Override
    public void setController(Controller controller) {
        this.controller = controller;
    }

//    будет эмулировать событие клиента.
// Обратись к контроллеру и вызови его
// нужный метод для отображения всех пользователей.
    public void fireEventShowAllUsers(){
        controller.onShowAllUsers();
    }

    public void fireEventShowDeletedUsers() {
        controller.onShowAllDeletedUsers();
    }

    //   Пользователь видит Вью со списком пользователей, нажимает
    //   на одного из них, запрос идет на сервер, выгребаем новые
    //   данные и отображаем другую Вью, которая относится к одному
    //   выбранному пользователю.
    public void fireEventOpenUserEditForm(long id) {
        controller.onOpenUserEditForm(id);
    }


}

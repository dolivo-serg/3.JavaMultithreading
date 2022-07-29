package com.javarush.task.task36.task3608.view;

import com.javarush.task.task36.task3608.controller.Controller;
import com.javarush.task.task36.task3608.model.ModelData;

/*
    EditUserView будет отображать данные в консоле
     о редактировании конкретного пользователя.
*/
public class EditUserView implements View {
    private Controller controller;

    @Override
    public void refresh(ModelData modelData) {
        System.out.println("User to be edited:");
        // С новой строки вывести табуляцию и активного пользователя.
        System.out.println("\t" + modelData.getActiveUser());
        System.out.println("===================================================");
    }

    @Override
    public void setController(Controller controller) {
        this.controller = controller;
    }

    //    напиши логику удаления пользователя.
    public void fireEventUserDeleted(long id){
        controller.onUserDelete(id);
    }

    //    обновления пользователя.
    public void fireEventUserChanged(String name, long id, int level){
        controller.onUserChange(name, id, level);
    }


}

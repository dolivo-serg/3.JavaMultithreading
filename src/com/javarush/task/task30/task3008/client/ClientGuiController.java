package com.javarush.task.task30.task3008.client;

public class ClientGuiController extends Client {
    private ClientGuiModel model = new ClientGuiModel();// поле, отвечающее за модель
    private ClientGuiView view = new ClientGuiView(this);//поле, отвечающее за представление

//создавать и возвращать объект типа GuiSocketThread.
    @Override
    protected SocketThread getSocketThread() {
        return new GuiSocketThread();
    }

//получать объект SocketThread через метод getSocketThread() и вызывать у него метод run().
    @Override
    public void run() {
        getSocketThread().run();//не старт
    }

    @Override
    protected String getServerAddress() {
        return view.getServerAddress();
    }

    @Override
    protected int getServerPort() {
        return view.getServerPort();
    }

    @Override
    protected String getUserName() {
        return view.getUserName();
    }

    public ClientGuiModel getModel() {
        return model;
    }

    public static void main(String[] args) {
        ClientGuiController controller = new ClientGuiController();
        controller.run();
    }

    public class GuiSocketThread extends SocketThread{
        @Override
        protected void processIncomingMessage(String message) {
            model.setNewMessage(message);  //устанавливать новое сообщение у модели
            view.refreshMessages();        //вызывать обновление вывода сообщений у представления
        }

        @Override
        protected void informAboutAddingNewUser(String userName) {
            model.addUser(userName);    //добавлять нового пользователя в модель
            view.refreshUsers();        //вызывать обновление вывода пользователей у отображения
        }

        @Override
        protected void informAboutDeletingNewUser(String userName) {
            model.deleteUser(userName); //удалять пользователя из модели
            view.refreshUsers();        //вызывать обновление вывода пользователей у отображения
        }

        @Override
        protected void notifyConnectionStatusChanged(boolean clientConnected) {
            view.notifyConnectionStatusChanged(clientConnected); //аналогичный метод у представления
        }
    }
}

package com.javarush.task.task30.task3008.client;

import com.javarush.task.task30.task3008.Connection;
import com.javarush.task.task30.task3008.ConsoleHelper;
import com.javarush.task.task30.task3008.Message;
import com.javarush.task.task30.task3008.MessageType;

import java.io.IOException;
import java.net.Socket;

public class Client {
    protected Connection connection;
    private volatile boolean clientConnected = false;

//    Он должен создавать вспомогательный поток SocketThread, ожидать пока тот установит соединение с сервером,
//    а после этого в цикле считывать сообщения с консоли и отправлять их серверу.
//    Условием выхода из цикла будет отключение клиента или ввод пользователем команды 'exit'.
    public void run() {

        SocketThread socketThread = getSocketThread();
        socketThread.setDaemon(true);
        socketThread.start();

        try {
            synchronized (this){
                wait();
            }
        } catch (InterruptedException e) {
            ConsoleHelper.writeMessage("Возникла ошибка во время работы клиента, завершение работы...");
            return;
        }
            if(clientConnected)
                ConsoleHelper.writeMessage("Соединение установлено. Для выхода наберите команду << exit >>.");
            else
                ConsoleHelper.writeMessage("Произошла ошибка во время работы клиента.");

        // Пока не будет введена команда exit, считываем сообщения с консоли и отправляем их на сервер
            while (clientConnected) {
                String message = ConsoleHelper.readString();

                if (message.equalsIgnoreCase("exit")) {
                    if (shouldSendTextFromConsole()) {   //ДОБАВЛЯЕМ УВЕДОМЛЕНИЕ СЕРВЕРА О ВЫХОДЕ УЧАСНИКА ЧАТА затем уже интераптимся
                        try {
                            connection.send(new Message(MessageType.USER_LEFT));
                            break;
                        } catch (IOException e) {
                            break;
                        }
                    }
                }
                // вот тут вызываем метод отправки сообщения на сервер
                if (shouldSendTextFromConsole())
                    sendTextMessage(message);
            }

    }


    //1.  - должен запросить ввод адреса сервера у пользователя и вернуть введенное значение.
//Адрес может быть строкой, содержащей ip, если клиент и сервер запущен на разных машинах
// или 'localhost', если клиент и сервер работают на одной машине
    protected String getServerAddress(){
        ConsoleHelper.writeMessage("Введите адрес сервера : ");
        return ConsoleHelper.readString();
    }

    // должен запрашивать ввод порта сервера и возвращать его.
    protected int getServerPort(){
        ConsoleHelper.writeMessage("Введите номер порта : ");
        return ConsoleHelper.readInt();
    }

    // должен запрашивать и возвращать имя пользователя.
    protected String getUserName(){
        ConsoleHelper.writeMessage("Укажите имя пользователя : ");
        return ConsoleHelper.readString();
    }

    //  в данной реализации клиента всегда должен
//  возвращать true (мы всегда отправляем текст введенный в консоль).
//Этот метод может быть переопределен, если мы будем писать какой-нибудь другой клиент,
// унаследованный от нашего, который не должен отправлять введенный в консоль текст.
    protected boolean shouldSendTextFromConsole(){
        return true;
    }

    //     - должен создавать и возвращать новый объект класса SocketThread.
    protected SocketThread getSocketThread(){
        return new SocketThread();
    }

    //   - создает новое текстовое сообщение, используя переданный текст и отправляет его
//    серверу через соединение connection.
//Если во время отправки произошло исключение IOException, то необходимо вывести
// информацию об этом пользователю и присвоить false полю clientConnected.
    protected void sendTextMessage(String text){
        try {
            Message message = new Message(MessageType.TEXT, text);
            connection.send(message);
        } catch (IOException e) {
            ConsoleHelper.writeMessage("Ошибка при отправке сообщения");
            clientConnected = false;
        }
    }


    //    поток, устанавливающий сокетное соединение и читающий сообщения сервера.
    public class SocketThread extends Thread{

        // должен выводить текст message в консоль.
        protected void processIncomingMessage(String message){
            ConsoleHelper.writeMessage(message);
        }

        // должен выводить в консоль информацию о том, что участник с именем userName присоединился к чату.
        protected void informAboutAddingNewUser(String userName){
            ConsoleHelper.writeMessage("Участник " + userName + " присоединился к чату.");
        }

        // должен выводить в консоль, что участник с именем userName покинул чат.
        protected void informAboutDeletingNewUser(String userName){
            ConsoleHelper.writeMessage("Участник " + userName + " покинул наш чудесный чатик.");
        }

        // этот метод должен Устанавливать значение поля clientConnected внешнего объекта Client в соответствии с
        // переданным параметром. Оповещать (пробуждать ожидающий) основной поток класса Client.
        protected void notifyConnectionStatusChanged(boolean clientConnected){
            Client.this.clientConnected = clientConnected;
            synchronized(Client.this) {
                Client.this.notify();
            }
        }


        protected void clientHandshake() throws IOException, ClassNotFoundException {
//а) В цикле получать сообщения, используя соединение connection.
            while (true){
                Message messageFromServer = connection.receive();
//б) Если тип полученного сообщения NAME_REQUEST (сервер запросил имя), запросить ввод имени пользователя с помощью
// метода getUserName(), создать новое сообщение с типом MessageType.USER_NAME и введенным именем, отправить сообщение серверу.
                if(messageFromServer.getType() == MessageType.NAME_REQUEST) {
                    // Запрашиваем ввод имени с консоли
                    String name = getUserName();
                    // Отправляем имя на сервер
                    connection.send(new Message(MessageType.USER_NAME, name));
                    continue;
                }
//в) Если тип полученного сообщения MessageType.NAME_ACCEPTED (сервер принял имя), значит сервер принял имя клиента, нужно об
// этом сообщить главному потоку, он этого очень ждет.
                if(messageFromServer.getType() == MessageType.NAME_ACCEPTED){
                    notifyConnectionStatusChanged(true);
                    processIncomingMessage(messageFromServer.getData());  //отсебятина, исправил
                    break;
                }
                else
                    throw new IOException("Unexpected MessageType");
            }
//Сделай это с помощью метода notifyConnectionStatusChanged(), передав в него true.
//После этого выйди из метода.
//г) Если пришло сообщение с каким-либо другим типом, кинь исключение IOException("Unexpected MessageType").
        }

        // Этот метод будет реализовывать главный цикл обработки сообщений сервера. Внутри метода:
        protected void clientMainLoop() throws IOException, ClassNotFoundException {
            while (true) {
//        а) Получи сообщение от сервера, используя соединение connection.
                Message messageFromServer = connection.receive();
//        б) Если это текстовое сообщение (тип MessageType.TEXT), обработай его с помощью метода processIncomingMessage().
                if (messageFromServer.getType() == MessageType.TEXT)
                    processIncomingMessage(messageFromServer.getData());
//        в) Если это сообщение с типом MessageType.USER_ADDED, обработай его с помощью метода informAboutAddingNewUser().
                else if (messageFromServer.getType() == MessageType.USER_ADDED)
                    informAboutAddingNewUser(messageFromServer.getData());
//        г) Если это сообщение с типом MessageType.USER_REMOVED, обработай его с помощью метода informAboutDeletingNewUser().
                else if (messageFromServer.getType() == MessageType.USER_REMOVED)
                    informAboutDeletingNewUser(messageFromServer.getData());
//        д) Если клиент получил сообщение какого-либо другого типа, брось исключение IOException("Unexpected MessageType").
                else
                    throw new IOException("Unexpected MessageType");
            }
        }

        @Override
        public void run() {
//1) Запроси адрес и порт сервера с помощью методов getServerAddress() и getServerPort().
//2) Создай новый объект класса java.net.Socket, используя данные, полученные в предыдущем пункте.
            try {
                Socket clientSocket = new Socket(getServerAddress(), getServerPort());
//3) Создай объект класса Connection, используя сокет из п.17.2.
                Client.this.connection = new Connection(clientSocket);
                //4) Вызови метод, реализующий "рукопожатие" клиента с сервером (clientHandshake()).
                this.clientHandshake();
//5) Вызови метод, реализующий основной цикл обработки сообщений сервера.
                this.clientMainLoop();

//6) При возникновении исключений IOException или ClassNotFoundException сообщи главному
// потоку о проблеме, используя notifyConnectionStatusChanged() и false в качестве параметра.
            } catch (IOException | ClassNotFoundException e) {
                notifyConnectionStatusChanged(false);
            }
        }

    }

    public static void main(String[] args) {
        Client client = new Client();
        client.run();
    }

}

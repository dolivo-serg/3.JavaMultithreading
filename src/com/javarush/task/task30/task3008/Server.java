package com.javarush.task.task30.task3008;

/* Server - основной класс сервера

Сервер должен поддерживать множество соединений с разными клиентами одновременно.
Это можно реализовать с помощью следующего алгоритма:

- Сервер создает серверное сокетное соединение.
- В цикле ожидает, когда какой-то клиент подключится к сокету.
- Создает новый поток обработчик Handler, в котором будет происходить обмен сообщениями с клиентом.
- Ожидает следующее соединение.

*/

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class Server {
    //  ключом будет имя клиента, а значением - соединение с ним
    private static Map<String, Connection> connectionMap = new ConcurrentHashMap<>();

    public static void main(String[] args) {
//а) Запрашивать порт сервера, используя ConsoleHelper.
        ConsoleHelper.writeMessage("Введите порт сервера:");
        int port = ConsoleHelper.readInt();
//б) Создавать серверный сокет java.net.ServerSocket, используя порт из предыдущего пункта.
        try (ServerSocket serverSocket = new ServerSocket(port)){
//в) Выводить сообщение, что сервер запущен.
            ConsoleHelper.writeMessage("Сервер чата запущен.");
            Handler handler;
//г) В бесконечном цикле слушать и принимать входящие сокетные соединения только что созданного серверного сокета.
            while (true){
                Socket socket = serverSocket.accept();
//д) Создавать и запускать новый поток Handler, передавая в конструктор сокет из предыдущего пункта.
                handler = new Handler(socket);
                handler.start();
//е) После создания потока обработчика Handler переходить на новый шаг цикла. 30 строка
            }
//ж) Предусмотреть закрытие серверного сокета в случае возникновения исключения.
//з) Если исключение Exception все же произошло, поймать его и вывести сообщение об ошибке.
        } catch (IOException e) {
            ConsoleHelper.writeMessage("Произошла ошибка при запуске или работе сервера.");
        }
    }

    //    Класс Handler должен реализовывать протокол общения с клиентом.
    private static class Handler extends Thread{
        private Socket socket;

        public Handler(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
//1. Выводить сообщение, что установлено новое соединение с удаленным адресом, который можно получить с
// помощью метода getRemoteSocketAddress().
            ConsoleHelper.writeMessage("Установлено новое соединение с " + socket.getRemoteSocketAddress().toString());
//2. Создавать Connection, используя поле socket.
            String userName = null;
            try(Connection connection = new Connection(socket)) {
//3. Вызывать метод, реализующий рукопожатие с клиентом, сохраняя имя нового клиента.
                userName = serverHandshake(connection);

//4. Рассылать всем участникам чата информацию об имени присоединившегося участника (сообщение с типом USER_ADDED).
                sendBroadcastMessage(new Message(MessageType.USER_ADDED, userName));
//5. Сообщать новому участнику о существующих участниках.
                notifyUsers(connection, userName);
//6. Запускать главный цикл обработки сообщений сервером.
                serverMainLoop(connection, userName);
//7. Обеспечить закрытие соединения при возникновении исключения.
//8. Отловить все исключения типа IOException и ClassNotFoundException, вывести в консоль информацию, что произошла
// ошибка при обмене данными с удаленным адресом.
//9. После того как все исключения обработаны, если п.11.3 отработал и возвратил нам имя, мы должны удалить запись для
// этого имени из connectionMap и разослать всем остальным участникам сообщение с типом USER_REMOVED и сохраненным именем.
                
            } catch (IOException | ClassNotFoundException e) {
                ConsoleHelper.writeMessage("При обмене данными с " + socket.getRemoteSocketAddress() + " произошол сбой...");
            }
            if (userName != null) {
                connectionMap.remove(userName);
                sendBroadcastMessage(new Message(MessageType.USER_REMOVED, userName));   // ТУТ не ПОМЕНЯЛ МЕСТАМИ
            }
//10. Последнее, что нужно сделать в методе run() - вывести сообщение, информирующее что соединение
// с удаленным адресом закрыто.
            ConsoleHelper.writeMessage("Соединение с " + socket.getRemoteSocketAddress() + " закрыто Х_x");
        }

        private String serverHandshake(Connection connection) throws IOException, ClassNotFoundException{
            while (true) {
//1) Сформировать и отправить команду запроса имени пользователя
                connection.send(new Message(MessageType.NAME_REQUEST, "Введите имя пользователя: "));
//2) Получить ответ клиента
                Message nameUser = connection.receive();
//3) Проверить, что получена команда с именем пользователя
                if (nameUser.getType() != MessageType.USER_NAME) {
                    ConsoleHelper.writeMessage("Получено сообщение от " + socket.getRemoteSocketAddress() + ". Тип сообщения не соответствует протоколу.");
                    continue;
                }
//4) Достать из ответа имя, проверить, что оно не пустое и пользователь с таким именем еще не подключен (используй connectionMap)
                String name = nameUser.getData();
                if (name.isEmpty()){
                    ConsoleHelper.writeMessage("Попытка подключения к серверу с пустым именем от " + socket.getRemoteSocketAddress());
                    continue;
                }
                if(connectionMap.containsKey(name)) {
                    ConsoleHelper.writeMessage("Попытка подключения к серверу с уже используемым именем от " + socket.getRemoteSocketAddress());
                    continue;
                }
//5) Добавить нового пользователя и соединение с ним в connectionMap
                connectionMap.put(name, connection);
//6) Отправить клиенту команду информирующую, что его имя принято
//7) Если какая-то проверка не прошла, заново запросить имя клиента
//8) Вернуть принятое имя в качестве возвращаемого значения
                connection.send(new Message(MessageType.NAME_ACCEPTED, "Имя: ~" + name + "~ принято! Юху!!!"));
                return name;

            }
        }

        //        отправка клиенту (новому участнику) информации об остальных клиентах (участниках) чата.
        private void notifyUsers(Connection connection, String userName) throws IOException{
//1) Пройтись по connectionMap.
            for(String name : connectionMap.keySet()){
                if(!userName.equals(name)) {
                    connection.send(new Message(MessageType.USER_ADDED, name));
                }
            }
//2) У каждого элемента из п.1 получить имя клиента, сформировать команду с типом USER_ADDED и полученным именем.
//3) Отправить сформированную команду через connection.
//4) Команду с типом USER_ADDED и именем равным userName отправлять не нужно, пользователь и так имеет информацию о себе.
        }

        private void serverMainLoop(Connection connection, String userName) throws IOException, ClassNotFoundException {
            while (true) {
                //1. Принимать сообщение клиента
                Message messageFromClient = connection.receive();
                Message generateMessage;
//2. Если принятое сообщение - это текст (тип TEXT), то формировать новое текстовое сообщение путем конкатенации: имени
// клиента, двоеточия, пробела и текста сообщения. Например, если мы получили сообщение с текстом "привет чат" от
// пользователя "Боб", то нужно сформировать сообщение "Боб: привет чат".
                if (messageFromClient.getType() == MessageType.TEXT) {
                    generateMessage = new Message(MessageType.TEXT, String.format("%s: %s", userName, messageFromClient.getData()));
//3. Отправлять сформированное сообщение всем клиентам с помощью метода sendBroadcastMessage().
                    // И САМОМУ СЕБЕ может исправить метод sendBroadcastMessage() по анологии с notifyUsers()  ?!
                    sendBroadcastMessage(generateMessage);
                }
                //ДОБАВИЛ ОТОБРАЖЕНИЕ ЮЗЕРОВ ПОКИДАЮЩИХ ЧАТ
                if (messageFromClient.getType() == MessageType.USER_REMOVED){
                    generateMessage = new Message(MessageType.USER_REMOVED, userName);
                    sendBroadcastMessage(generateMessage);
                }
                else
                    ConsoleHelper.writeMessage(socket.getRemoteSocketAddress() + " : Тип сообщение не является \"MessageType.TEXT\"\t-\tНе удалось отправить сообщение.");
//4. Если принятое сообщение не является текстом, вывести сообщение об ошибке
//5. Организовать бесконечный цикл, внутрь которого перенести функционал пунктов 10.1-10.4.
            }
        }

    }

    //    метод должен отправлять сообщение message всем соединениям из connectionMap.
    public static void sendBroadcastMessage(Message message){
        for(ConcurrentMap.Entry<String, Connection> connection : connectionMap.entrySet()){
            try {
                connection.getValue().send(message);
            } catch (IOException e) {
                ConsoleHelper.writeMessage("Не удалось отправить сообщение.");
            }
        }

    }

}

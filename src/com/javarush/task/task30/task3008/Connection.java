package com.javarush.task.task30.task3008;

//Connection - класс соединения между клиентом и сервером.

//  Класс Connection будет выполнять роль обертки над классом java.net.Socket
//  которая должна будет уметь сериализовать и десериализовать объекты типа
//  Message в сокет.

//  Методы этого класса должны быть готовы к вызову из разных потоков.

import java.io.Closeable;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketAddress;

public class Connection implements Closeable {
    private final Socket socket;
    private final ObjectOutputStream out;
    private final ObjectInputStream in;

    public Connection(Socket socket) throws IOException {
        this.socket = socket;
        this.out = new ObjectOutputStream(socket.getOutputStream());
        this.in = new ObjectInputStream(socket.getInputStream());
    }

    public void send(Message message) throws IOException{
        synchronized (out){
            out.writeObject(message);
        }
    }

    public Message receive() throws IOException, ClassNotFoundException{
        synchronized (in){
            return (Message) in.readObject();
        }
    }

//    возвращающий удаленный адрес сокетного соединения
    public SocketAddress getRemoteSocketAddress(){
        return socket.getRemoteSocketAddress();
    }

//    должен закрывать все ресурсы класса.
    public void close() throws IOException{
        in.close();
        out.close();
        socket.close();
    }
}

package com.javarush.task.task30.task3008;

/*  MessageType - enum, который
 отвечает за тип сообщений пересылаемых между клиентом и сервером.*/
public enum MessageType {
    NAME_REQUEST,   // - запрос имени.
    USER_NAME,      // - имя пользователя.
    NAME_ACCEPTED,  // - имя принято.
    TEXT,           // - текстовое сообщение.
    USER_ADDED,     // - пользователь добавлен.
    USER_REMOVED,   // - пользователь удален.
    USER_LEFT       // - отправляется на сервер, когда клиент ввел exit
}
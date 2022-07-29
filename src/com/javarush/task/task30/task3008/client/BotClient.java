package com.javarush.task.task30.task3008.client;

import com.javarush.task.task30.task3008.ConsoleHelper;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class BotClient extends Client{

    public class BotSocketThread extends SocketThread{

        @Override
        protected void clientMainLoop() throws IOException, ClassNotFoundException {
// С помощью метода sendTextMessage() отправь сообщение с текстом
            BotClient.this.sendTextMessage("Привет чатику. Я бот. Понимаю команды: дата, день, месяц, год, время, час, минуты, секунды."
            );
// Вызови реализацию clientMainLoop() родительского класса.
            super.clientMainLoop();
        }

        @Override
        protected void processIncomingMessage(String message) {
//Вывести в консоль текст полученного сообщения message.
            ConsoleHelper.writeMessage(message);
//Получить из message имя отправителя и текст сообщения. Они разделены ": ".
            String[] data = message.split(": ");
            if(data.length != 2)
                return;
            String userName = data[0];
            String textMessage = data[1];
            String pattern = null;
            switch (textMessage){
                case "дата":  pattern = "d.MM.YYYY";
                    break;
                case "день": pattern = "d";
                    break;
                case "месяц": pattern = "MMMM";
                    break;
                case "год": pattern = "YYYY";
                    break;
                case "время": pattern = "H:mm:ss";
                    break;
                case "час": pattern = "H";
                    break;
                case "минуты": pattern = "m";
                    break;
                case "секунды": pattern = "s";
                    break;
            }

            Date date = Calendar.getInstance().getTime();
            if(pattern != null) {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
                String textAnswer = String.format("Информация для %s: %s", userName, simpleDateFormat.format(date));
                BotClient.this.sendTextMessage(textAnswer);
            }
        }

    }

    //должен создавать и возвращать объект класса BotSocketThread.
    @Override
    protected SocketThread getSocketThread() {
        return new BotSocketThread();
    }

    //должен всегда возвращать false. Мы не хотим, чтобы бот отправлял текст введенный в консоль.
    @Override
    protected boolean shouldSendTextFromConsole() {
        return false;
    }

    //генерировать новое имя бота, например: date_bot_X, где X - любое
    //число от 0 до 99. Для генерации X используй метод Math.random().
    @Override
    protected String getUserName() {
        return String.format("date_bot_%d", (int)(Math.random()*100));
    }


    public static void main(String[] args) {
        BotClient botDate = new BotClient();
        botDate.run();
    }

}

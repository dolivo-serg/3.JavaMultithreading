package com.javarush.task.task35.task3513;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        Model model = new Model();
        Controller controller = new Controller(model);
        
        JFrame myGame2048 = new JFrame();
        myGame2048.setTitle("2048");
        myGame2048.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        myGame2048.setSize(450, 500);
        myGame2048.setResizable(false);

        myGame2048.add(controller.getView());
        
        myGame2048.setLocationRelativeTo(null);
        myGame2048.setVisible(true);
    }
}

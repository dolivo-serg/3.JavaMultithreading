package com.javarush.task.task21.task2113;

import java.util.ArrayList;
import java.util.List;

public class Hippodrome {
    static Hippodrome game;
    private List<Horse> horses;

    public void run() throws InterruptedException {
        for(int i = 0; i < 100; i++){
            move();
            print();
            Thread.sleep(200);
        }
    }
    public void move(){
        for(Horse horse : horses){
            horse.move();
        }
    }
    public void print(){
        for(Horse horse : horses) {
            horse.print();
        }
        for (int i = 0; i < 10; i++) {
            System.out.println();
        }
    }

    public Horse getWinner(){
        Horse winner = null;
        for (int i = 0; i < horses.size()-1; i++){
            if(horses.get(i).getDistance() < horses.get(i+1).getDistance()){
                winner = horses.get(i+1);
            }
        }
        return winner;
    }
    public void printWinner(){
        System.out.println(String.format("Winner is %s!",getWinner().getName()));
    }

    public Hippodrome(List<Horse> horses) {
        this.horses = horses;
    }

    public List<Horse> getHorses() {
        return horses;
    }
    
    public static void main(String[] args) throws InterruptedException {
        List<Horse> horses = new ArrayList<>();
        horses.add(new Horse("Ally", 3, 0));
        horses.add(new Horse("Polly", 3, 0));
        horses.add(new Horse("Molly", 3, 0));
        Hippodrome hippodrome = new Hippodrome(horses);
        game = hippodrome;
        game.run();
        game.printWinner();
    }
}

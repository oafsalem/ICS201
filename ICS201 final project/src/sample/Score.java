package sample;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;

public class Score {
    private int score;

    public Score() {
        this.score = 0;
    }

    public int getScore() {
        return score;
    }
    public void addScore(int score) {
        this.score += score;
    }

    public void addOne(){
        score+=1;
    }
    public void saveToFile() throws FileNotFoundException {
        PrintWriter printWriter = new PrintWriter(new FileOutputStream("scores.txt",true));
        printWriter.println(this.getScore());
        printWriter.close();
    }
    public static ArrayList<Integer> getScores() throws FileNotFoundException {
        Scanner fileScanner = new Scanner(new FileInputStream("scores.txt"));
        ArrayList<Integer> scores = new ArrayList<>();
        while (fileScanner.hasNextInt()){
            scores.add(fileScanner.nextInt());
        }
        scores.sort(new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o2-o1;
            }
        });
        return scores;
    }
}

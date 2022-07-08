/**
 * For Qouick Sort algorithm, benefited from https://www.geeksforgeeks.org/quick-sort/
 * */

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void Swap(List<String> arr, int i, int j){
        //Collections.swap(arr,i,j);
        String temp = arr.get(i);
        arr.set(i, arr.get(j));
        arr.set(j, temp);
    }

    public static int Partition(List<String> arr, int low, int high){
        String [] split = arr.get(high).split("\t");
        int pivot = Integer.parseInt(split[1]);
        int smallerIndex = low-1;

        for(int j = low; j <= high-1; j++){
            split = arr.get(j).split("\t");
            int element = Integer.parseInt(split[1]);

            if(element < pivot){
                smallerIndex++;
                Swap(arr, smallerIndex, j);
            }
        }

        //Swap pivot to true position
        Swap(arr, smallerIndex + 1, high);
        return (smallerIndex + 1);

    }

    public static void QuickSort(List<String> scoreList, int low, int high){
        if(low < high){

            //partition part
            int pi = Partition(scoreList, low, high);
            QuickSort(scoreList, low, pi-1);
            QuickSort(scoreList, pi+1, high);
        }
    }

    public static void ShowHighScore(){

        int highScore = 0;
        List<String> scores = new ArrayList<>();

        try{
            BufferedReader reader = new BufferedReader(new FileReader("minesweeper_scores.txt"));
            String s;
            while((s = reader.readLine()) != null){
                scores.add(s);
            }
            reader.close();
        } catch(IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        //Sort score array by QuickSort
        System.out.println("before sort: ");

        for (String item : scores
             ) { System.out.println(item);

        }
        QuickSort(scores, 0, scores.size()-1);

        System.out.println("\nafter sort: ");

        for (int i = scores.size()-1; i >= 0; i--) {
            System.out.println(scores.get(i));
        }


    }

    public static void main (String[] args){

        Scanner scan = new Scanner(System.in);
        int row, column;
        String nickName;

        System.out.println("Welcome to Mine Sweeper!");
        System.out.println("Please enter your nickname: ");
        nickName = scan.nextLine();
        System.out.println("Please, enter the game board size you would like to play. ");
        System.out.print("Row Value: ");
        row = scan.nextInt();
        System.out.print("Column Value: ");
        column = scan.nextInt();

        MayinTarlasi mayin = new MayinTarlasi(row, column);
        mayin.run();
        mayin.saveScore(nickName, mayin.score);

        ShowHighScore();

    }


}


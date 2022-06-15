import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.io.File;
import java.io.IOException;  // Import the IOException class to handle errors

import static java.nio.file.StandardOpenOption.APPEND;
import static java.nio.file.StandardOpenOption.CREATE;

public class MayinTarlasi {

    int rowValue , colValue, size, score;
    int [][] gameMap;
    int [][] gameBoard;
    boolean gameOn = false;
    boolean checkArrayBound = false;

    Scanner scan = new Scanner(System.in);
    Random rand = new Random();

    //Constructor
    public MayinTarlasi(int rowValue, int colValue){
        this.rowValue = rowValue;
        this.colValue = colValue;
        this.size = rowValue * colValue;
        this.score = 0;

        this.gameMap = new int[rowValue][colValue];
        this.gameBoard = new int[rowValue][colValue];

    }

    //A method which run the game
    public void run(){
        int row = 0, col = 0;
        prepareGame();
        //display(gameMap);
        gameOn = true;
        System.out.println('\n' + "  --- GAME START ---  " + '\n');
        display(gameBoard);

        while (gameOn){
            //Prompt user to enter the game board size
            System.out.print("Row:  ");
            row = scan.nextInt();
            System.out.print("Column:  ");
            col = scan.nextInt();

            checkArrayBound = checkArrayBound(row, col);

            if(checkArrayBound == false)
                continue;

            if(gameMap[row][col] != -1){
                //openField(row,col);
                checkMayin(row, col);
                score++;
                gameFinish(score);
            } else {
                System.out.println("Ooops! You stepped on a mine!");
                System.out.println('\n' + "--- GAME OVER ---" + '\n');
                break;
            }
            display(gameBoard);
        }

        System.out.println("Your total score is " + score);

    }

    private void gameFinish (int score){
        int finishScore = this.size - (this.size/4);

        if(score == finishScore){
            System.out.println("You win!");
            this.gameOn = false;
        }
    }


    /**
     * //Search secure area after clicking a safe place, this will be developed later.
     * private void openField(int r, int c){
        if( r < 0 || r >= this.rowValue || c < 0 || c >= this.colValue){
            return;
        } else if( gameBoard[r][c] != 0 && gameBoard[r][c] != -2 && gameMap[r][c] == -1) {
            checkMayin(r, c);
            //gameMap[r][c] = -3;
        } else if(gameBoard[r][c] == -3){
            return;
        }
        else{
            checkMayin(r, c);
            gameBoard[r][c] = -3;
            openField(r-1, c);
            openField(r, c-1);
            openField(r, c+1);
            openField(r+1, c);
        }
    }*/

    private void checkMayin(int row, int col) {

        if(gameBoard[row][col] == 0){
            if( (row > 0) && (col > 0) && (gameMap[row-1][col-1] == -1) ) gameBoard[row][col]++;
            if( (row > 0) && (gameMap[row-1][col] == -1) ) gameBoard[row][col]++;
            if( (row > 0) && (col < this.colValue-1) && (gameMap[row-1][col+1] == -1) ) gameBoard[row][col]++;
            if( (col > 0) && (gameMap[row][col-1] == -1) ) gameBoard[row][col]++;
            if( (col < this.colValue-1) && (gameMap[row][col+1] == -1) ) gameBoard[row][col]++;
            if( (row < this.rowValue-1) && (col > 0) && (gameMap[row+1][col-1] == -1) ) gameBoard[row][col]++;
            if( (row < this.rowValue-1) && (gameMap[row+1][col] == -1) ) gameBoard[row][col]++;
            if( (row < this.rowValue-1) && (col < this.colValue-1) && (gameMap[row+1][col+1] == -1) ) gameBoard[row][col]++;

            if(gameBoard[row][col] == 0) gameBoard[row][col] = -2;

        }

    }
    private boolean checkArrayBound(int row, int col) {
        if(row < 0 || row >= this.rowValue || col < 0 || col >= this.colValue){
            System.out.println("Please enter a suitable index number.");
            return false;
        }
        return true;
    }

    //A method prepare the game board.
    public void prepareGame(){
        int count = 0, randRow = 0, randCol = 0;

        while(count != size/4) {
            randRow = rand.nextInt(rowValue);
            randCol = rand.nextInt(colValue);

            //Count the exact mine amount by avoiding reassign -1 to its already assigned places.
            if( gameMap[randRow][randCol] != -1 ){
                gameMap[randRow][randCol] = -1;
                count++;
            }
        }
    }

    public void display(int [][] map){
        //Print the game map on console
        for(int i = 0; i < rowValue; i++){
            for(int k = 0; k< colValue; k++)
                System.out.print("-----");

            System.out.println();
            for(int j = 0; j < colValue; j++){
                if(map[i][j] < 0) {
                    System.out.print( " " + map[i][j] +"  ");
                } else{
                System.out.print( "  " + map[i][j] +"  ");
                }
            }
           // System.out.print("|");
            System.out.println();
        }

        for(int k = 0; k< colValue; k++)
            System.out.print("-----");

        System.out.println();
    }

    public void saveScore (String nickname, int score){
        String scoreValue = String.valueOf(score);
        List<String> data = new ArrayList<>();
        data.add(nickname);
        data.add(scoreValue);

        try{
            BufferedWriter writer = new BufferedWriter(new FileWriter("minesweeper_scores.txt", true));
            for (String s: data
                 ) {
                writer.append(s + "\t");
            }
            writer.newLine();
            writer.close();

            /*if (scoreFile.createNewFile()) {
                System.out.println("File created: " + scoreFile.getName());
            } else {
                System.out.println("File already exists.");
            }*/
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}

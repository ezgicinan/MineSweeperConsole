import java.util.Scanner;

public class Main {

    public static void main (String[] args){

        Scanner scan = new Scanner(System.in);
        int row, column;

        System.out.println("Welcome to Mine Sweeper!");
        System.out.println("Please, enter the game board size you would like to play. ");
        System.out.print("Row Value: ");
        row = scan.nextInt();
        System.out.print("Column Value: ");
        column = scan.nextInt();

        MayinTarlasi mayin = new MayinTarlasi(row, column);
        mayin.run();

    }
}

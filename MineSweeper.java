package main;

import java.util.Scanner;
import java.util.Random;

public class MineSweeper {
    private int row, col, numMines;
    private int[][] board;
    private boolean[][] visible;

    public MineSweeper(int row, int col) {
        this.row = row;
        this.col = col;
        board = new int[row][col];
        visible = new boolean[row][col];
        numMines = (row * col) / 4;
        placeMines();
    }

    private void placeMines() {
        int count = 0;
        Random rand = new Random();
        while (count < numMines) {
            int i = rand.nextInt(row);
            int j = rand.nextInt(col);
            if (board[i][j] == 0) {
                board[i][j] = -1;
                count++;
            }
        }
    }

    private boolean isValid(int i, int j) {
        return i >= 0 && i < row && j >= 0 && j < col;
    }

    private int countMines(int i, int j) {
        int count = 0;
        for (int r = i - 1; r <= i + 1; r++) {
            for (int c = j - 1; c <= j + 1; c++) {
                if (isValid(r, c) && board[r][c] == -1) {
                    count++;
                }
            }
        }
        return count;
    }

    private void display() {
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                if (visible[i][j]) {
                    System.out.print(board[i][j] + " ");
                } else {
                    System.out.print("- ");
                }
            }
            System.out.println();
        }
    }

    private void play() {
        Scanner input = new Scanner(System.in);
        int remaining = row * col - numMines;
        while (remaining > 0) {
            System.out.print("Enter row and column: ");
            int i = input.nextInt();
            int j = input.nextInt();
            if (!isValid(i, j)) {
                System.out.println("Invalid input! Try again.");
                continue;
            }
            if (board[i][j] == -1) {
                System.out.println("Game over!");
                visible[i][j] = true;
                display();
                return;
            }
            if (visible[i][j]) {
                System.out.println("You already selected that square!");
                continue;
            }
            int count = countMines(i, j);
            board[i][j] = count;
            visible[i][j] = true;
            remaining--;
            if (count == 0) {
                for (int r = i - 1; r <= i + 1; r++) {
                    for (int c = j - 1; c <= j + 1; c++) {
                        if (isValid(r, c) && !visible[r][c]) {
                            visible[r][c] = true;
                            remaining--;
                        }
                    }
                }
            }
            display();
        }
        System.out.println("You won!");
    }

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.print("Enter number of rows: ");
        int row = input.nextInt();
        System.out.print("Enter number of columns: ");
        int col = input.nextInt();
        MineSweeper game = new MineSweeper(row, col);
        game.display();

        game.play();
    }}

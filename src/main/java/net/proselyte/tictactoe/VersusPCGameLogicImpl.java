package net.proselyte.tictactoe;

import java.util.Random;
import java.util.Scanner;

public class VersusPCGameLogicImpl implements GameLogic {

    private final Field field = new Field();
    private final Scanner scanner = new Scanner(System.in);
    private final Random random = new Random();

    private final PlayerSymbol HUMAN_SYMBOL = PlayerSymbol.X;
    private final PlayerSymbol PC_SYMBOL = PlayerSymbol.O;

    private boolean checkWinner(PlayerSymbol symbol) {
        PlayerSymbol[] symbols = field.getStorage();
        int[][] winnerCombinations = {{0, 1, 2}, {3, 4, 5}, {6, 7, 8},
                {0, 3, 6}, {1, 4, 7}, {2, 5, 8},
                {0, 4, 8}, {2, 4, 6}};

        for (int[] combination : winnerCombinations) {
            if (symbols[combination[0]] == symbol
                    && symbols[combination[1]] == symbol
                    && symbols[combination[2]] == symbol) {
                return true;
            }
        }
        return false;
    }

    private boolean validateMove(int position) {
        return position >= 1 && position <= 9 && field.getStorage()[position - 1] == PlayerSymbol.BLANK;
    }

    private void humanMove() {
        int position;

        while (true) {
            System.out.println("Human move " + HUMAN_SYMBOL.getValue() + " enter position (1-9)");

            position = scanner.nextInt();
            if(validateMove(position)) {
                field.getStorage()[position - 1] = HUMAN_SYMBOL;
                break;
            }
            System.out.println("Incorrect input. Try again");
        }
    }


    private void pcMove() {
        int position;
        do {
            position = random.nextInt(9) + 1;
        } while (!validateMove(position));
        field.getStorage()[position - 1] = PC_SYMBOL;
    }

    @Override
    public void play() {
        PlayerSymbol currentPlayer = HUMAN_SYMBOL;
        while (true) {
            field.printBoard();
            if (currentPlayer == HUMAN_SYMBOL) {
                humanMove();

                if (checkWinner(HUMAN_SYMBOL)) {
                    System.out.println("Player " + HUMAN_SYMBOL.getValue() + " won!!!");
                    break;
                }
            }

            if (currentPlayer == PC_SYMBOL) {
                System.out.println("PC move");

                pcMove();

                if (checkWinner(PC_SYMBOL)) {
                    System.out.println("Player " + PC_SYMBOL.getValue() + " won!!!");
                    break;
                }
            }
            currentPlayer = (currentPlayer == HUMAN_SYMBOL) ? PC_SYMBOL : HUMAN_SYMBOL;
        }
    }
}

import java.util.*;

public class NoughtsAndCrosses {
    char[][] gameGrid = new char[3][3];
    boolean doWeHaveAWinner;
    boolean chooseYourOpponent;
    boolean whoseTurn;
    boolean playerFirst;
    char playerMark;
    char cpuMark;
    ArrayList<Integer> tickTackToes = new ArrayList<>();

    NoughtsAndCrosses() {
        for (int i = 0; i < 3; i++)
           Arrays.fill(gameGrid[i],' ');
        doWeHaveAWinner = false;
        for (int i = 0; i < 8; i++)
            tickTackToes.add(i);
        whoseTurn = true;
    }
    //Experiment code

    public String checkRoute(int route) {
        StringBuilder stringBuilder = new StringBuilder();
        switch (route) {
            case 0:
                stringBuilder.append(gameGrid[0][0]);
                stringBuilder.append(gameGrid[0][1]);
                stringBuilder.append(gameGrid[0][2]);
                break;
            case 1:
                stringBuilder.append(gameGrid[1][0]);
                stringBuilder.append(gameGrid[1][1]);
                stringBuilder.append(gameGrid[1][2]);
                break;
            case 2:
                stringBuilder.append(gameGrid[2][0]);
                stringBuilder.append(gameGrid[2][1]);
                stringBuilder.append(gameGrid[2][2]);
                break;
            case 3:
                stringBuilder.append(gameGrid[0][0]);
                stringBuilder.append(gameGrid[1][0]);
                stringBuilder.append(gameGrid[2][0]);
                break;
            case 4:
                stringBuilder.append(gameGrid[0][1]);
                stringBuilder.append(gameGrid[1][1]);
                stringBuilder.append(gameGrid[2][1]);
                break;
            case 5:
                stringBuilder.append(gameGrid[0][2]);
                stringBuilder.append(gameGrid[1][2]);
                stringBuilder.append(gameGrid[2][2]);
                break;
            case 6:
                stringBuilder.append(gameGrid[0][0]);
                stringBuilder.append(gameGrid[1][1]);
                stringBuilder.append(gameGrid[2][2]);
                break;
            case 7:
                stringBuilder.append(gameGrid[2][0]);
                stringBuilder.append(gameGrid[1][1]);
                stringBuilder.append(gameGrid[0][2]);
                break;
        }
        return stringBuilder.toString();
    }

    //Remove routes that cannot lead to win

    public void eliminateRoute() {
        StringBuilder charToCheck = new StringBuilder();
        charToCheck.append(playerMark);
        for (int i = 0; i < tickTackToes.size(); i++) {
            if (checkRoute(tickTackToes.get(i)).contains(charToCheck.toString())) {
                tickTackToes.remove(i);
                i--;
            }
        }
    }

    public boolean stopOpponentsWin() {
        StringBuilder charToCheck = new StringBuilder();
        charToCheck.append(playerMark);
        int opponentWinPath = -1;
        String movesPlaced;
        for (int i = 0; i < 8; i++) {
            movesPlaced = checkRoute(i).replaceAll(charToCheck.toString(), "");
            if (movesPlaced.equals(" ")) {
                opponentWinPath = i;
                break;
            }
        }
        if (opponentWinPath >= 0 && opponentWinPath < 3) {
            for (int i = 0; i < 3; i++) {
                if (gameGrid[opponentWinPath][i] == ' ') {
                    gameGrid[opponentWinPath][i] = cpuMark;
                    return true;
                }
            }
        } else if (opponentWinPath >= 3 && opponentWinPath < 6) {
            for (int i = 0; i < 3; i++) {
                if (gameGrid[i][opponentWinPath - 3] == ' ') {
                    gameGrid[i][opponentWinPath - 3] = cpuMark;
                    return true;
                }
            }
        } else if (opponentWinPath == 6) {
            for (int i = 0; i < 3; i++) {
                if (gameGrid[i][i] == ' ') {
                    gameGrid[i][i] = cpuMark;
                    return true;
                }
            }
        } else if (opponentWinPath == 7) {
            for (int i = 0; i < 3; i++) {
                if (gameGrid[i][2 - i] == ' ') {
                    gameGrid[i][2 - i] = cpuMark;
                    return true;
                }
            }
        }
        return false;
    }

    //Check for routes that only require one move to win

    public boolean checkForWinningMove() {
        StringBuilder charToCheck = new StringBuilder();
        charToCheck.append(cpuMark);
        int winPath = -1;
        String winningLine;
        for (int i = 0; i < tickTackToes.size(); i++) {
            winningLine = checkRoute(tickTackToes.get(i)).replaceAll(charToCheck.toString(), "");
            if (winningLine.equals(" ")) {
                winPath = tickTackToes.get(i);
            }
        }
        if (winPath >= 0 && winPath < 3) {
            for (int i = 0; i < 3; i++) {
                if (gameGrid[winPath][i] == ' ') {
                    gameGrid[winPath][i] = cpuMark;
                    return true;
                }
            }
        } else if (winPath >= 3 && winPath < 6) {
            for (int i = 0; i < 3; i++) {
                if (gameGrid[i][winPath - 3] == ' ') {
                    gameGrid[i][winPath - 3] = cpuMark;
                    return true;
                }
            }
        } else if (winPath == 6) {
            for (int i = 0; i < 3; i++) {
                if (gameGrid[i][i] == ' ') {
                    gameGrid[i][i] = cpuMark;
                    return true;
                }
            }
        } else if (winPath == 7) {
            for (int i = 0; i < 3; i++) {
                if (gameGrid[i][2 - i] == ' ') {
                    gameGrid[i][2 - i] = cpuMark;
                    return true;
                }
            }
        }
        return false;
    }

    //Check for possible win routes that already have a cpu mark in.

    public boolean checkForBestMove() {
        ArrayList<Integer> goodMoves = new ArrayList<Integer>();
        Random rng = new Random();
        boolean foundEmpty = false;
        int routeChosen;
        int squareChosen;

        for (Integer tickTackToe : tickTackToes) {
            if (checkRoute(tickTackToe).contains("O")) {
                goodMoves.add(tickTackToe);
            }
        }
        if (goodMoves.size() == 0) {
            return false;

        } else {
            routeChosen = goodMoves.get(rng.nextInt(goodMoves.size()));
            while(!foundEmpty) {
                squareChosen = rng.nextInt(3);
                if (routeChosen >= 0 && routeChosen < 3) {

                    if (gameGrid[routeChosen][squareChosen] == ' ') {
                        gameGrid[routeChosen][squareChosen] = cpuMark;
                        foundEmpty = true;
                    }

                } else if (routeChosen >= 3 && routeChosen < 6) {

                    if (gameGrid[squareChosen][routeChosen - 3] == ' ') {
                        gameGrid[squareChosen][routeChosen - 3] = cpuMark;
                        foundEmpty = true;
                    }

                } else if (routeChosen == 6) {

                    if (gameGrid[squareChosen][squareChosen] == ' ') {
                        gameGrid[squareChosen][squareChosen] = cpuMark;
                        foundEmpty = true;
                    }

                } else if (routeChosen == 7) {

                    if (gameGrid[squareChosen][2 - squareChosen] == ' ') {
                        gameGrid[squareChosen][2 - squareChosen] = cpuMark;
                        foundEmpty = true;
                    }

                }
            }
        }
        return true;
    }

    public void cpuNoBetterMoves() {
        Random rng = new Random();
        boolean foundEmpty = false;
        int routeChosen;
        int squareChosen;


        if(tickTackToes.size() < 1) {
            boolean anyRandomMoveMade = false;
            while (!anyRandomMoveMade) {
                int row = rng.nextInt(3);
                int column = rng.nextInt(3);
                if (gameGrid[row][column] == ' ') {
                    gameGrid[row][column] = cpuMark;
                    anyRandomMoveMade = true;
                }
            }
        } else {
            routeChosen = tickTackToes.get(rng.nextInt(tickTackToes.size()));
            while (!foundEmpty) {
                squareChosen = rng.nextInt(3);
                if (routeChosen >= 0 && routeChosen < 3) {

                    if (gameGrid[routeChosen][squareChosen] == ' ') {
                        gameGrid[routeChosen][squareChosen] = cpuMark;
                        foundEmpty = true;
                    }

                } else if (routeChosen >= 3 && routeChosen < 6) {

                    if (gameGrid[squareChosen][routeChosen - 3] == ' ') {
                        gameGrid[squareChosen][routeChosen - 3] = cpuMark;
                        foundEmpty = true;
                    }

                } else if (routeChosen == 6) {

                    if (gameGrid[squareChosen][squareChosen] == ' ') {
                        gameGrid[squareChosen][squareChosen] = cpuMark;
                        foundEmpty = true;
                    }

                } else if (routeChosen == 7) {

                    if (gameGrid[squareChosen][2 - squareChosen] == ' ') {
                        gameGrid[squareChosen][2 - squareChosen] = cpuMark;
                        foundEmpty = true;
                    }
                }
            }
        }
    }

    public void cpuTurn() {
        eliminateRoute();
        System.out.println("Computer's turn!");
        if (checkForWinningMove())
            return;
        else if (stopOpponentsWin())
            return;
        else if (checkForBestMove())
            return;
        else cpuNoBetterMoves();
    }

//Standard 2 player game code

    public void drawGrid() {
        System.out.println(" " + gameGrid[0][0] + " | " + gameGrid[0][1] + " | " + gameGrid[0][2]);
        System.out.println("---+---+---");
        System.out.println(" " + gameGrid[1][0] + " | " + gameGrid[1][1] + " | " + gameGrid[1][2]);
        System.out.println("---+---+---");
        System.out.println(" " + gameGrid[2][0] + " | " + gameGrid[2][1] + " | " + gameGrid[2][2] + "\n");
    }

    public int userInput() {
        Scanner scanner = new Scanner(System.in);
        int input = 0;
        try {
            input = scanner.nextInt();
        } catch (InputMismatchException e) {
            System.out.println("Invalid input.");
        }
        return input;
    }

    public void gameSetup() {
        boolean opponentChosen = false;
        int input;

        while (!opponentChosen) {
            System.out.println("1: VS Player    2: VS Computer");
            input = userInput();
            switch (input) {
                case 1:
                    chooseYourOpponent = true;
                    opponentChosen = true;
                    break;
                case 2:
                    chooseYourOpponent = false;
                    opponentChosen = true;
                    break;
                default:
                    System.out.println("Invalid input.");
            }
        }
        if (!chooseYourOpponent) {
            boolean markChosen = false;
            while (!markChosen) {
                System.out.println("1: Noughts    2: Crosses");
                input = userInput();
                switch (input) {
                    case 1:
                        playerMark = 'O';
                        cpuMark = 'X';
                        playerFirst = true;
                        markChosen = true;
                        break;
                    case 2:
                        playerMark = 'X';
                        cpuMark = 'O';
                        playerFirst = false;
                        markChosen = true;
                        break;
                    default:
                        System.out.println("Invalid input.");
                }
            }
        }
    }

    public boolean checkForWinner(boolean player) {
        char oOrX;
        if (player)
            oOrX = 'O';
        else
            oOrX = 'X';
        for (int i = 0; i < 3; i++) {
            if (gameGrid[i][0] == oOrX && gameGrid[i][1] == oOrX && gameGrid[i][2] == oOrX)
                return true;
        }
        for (int i = 0; i < 3; i++) {
            if (gameGrid[0][i] == oOrX && gameGrid[1][i] == oOrX && gameGrid[2][i] == oOrX)
                return true;
        }
        if (gameGrid[0][0] == oOrX && gameGrid[1][1] == oOrX && gameGrid[2][2] == oOrX)
            return true;
        else return gameGrid[0][2] == oOrX && gameGrid[1][1] == oOrX && gameGrid[2][0] == oOrX;
    }

    public boolean placeOOrX(int row, int column, boolean player) {
        if (gameGrid[row - 1][column - 1] == ' ') {
            if (player)
                gameGrid[row - 1][column - 1] = 'O';
            else
                gameGrid[row - 1][column - 1] = 'X';
            return true;
        } else
            return false;
    }

    public void playerTurn(boolean player) {
        boolean validInt = false;
        int row;
        int column;
        if (player)
            System.out.println("Noughts make your move.");
        else
            System.out.println("Crosses make your move.");

        while(!validInt) {
            row = 0;
            column = 0;
            while (row < 1 || row > 3) {
                System.out.println("Which row?");
                System.out.println("1: Top   2: Middle   3: Bottom");
                row = userInput();
                if (row < 1 || row > 3)
                    System.out.println("Invalid input.");
            }
            while (column < 1 || column > 3) {
                System.out.println("Which column?");
                System.out.println("1: Left   2: Middle   3: Right");
                column = userInput();
                if (column < 1 || column > 3)
                    System.out.println("Invalid input.");
            }
            validInt = placeOOrX(row, column, player);
            if (!validInt)
                System.out.println("That square is taken. Try another.");
        }
    }

    public static void main(String[] args) {
        NoughtsAndCrosses newGame = new NoughtsAndCrosses();
        newGame.gameSetup();
        newGame.drawGrid();
        boolean noWinner = false;
        int turnNumber = 0;
        while(!newGame.doWeHaveAWinner) {
            if(newGame.whoseTurn == newGame.playerFirst || newGame.chooseYourOpponent)
                newGame.playerTurn(newGame.whoseTurn);
            else
                newGame.cpuTurn();
                newGame.doWeHaveAWinner = newGame.checkForWinner(newGame.whoseTurn);
                newGame.drawGrid();
                newGame.whoseTurn = !newGame.whoseTurn;
            turnNumber++;
            if (turnNumber > 8 && !newGame.doWeHaveAWinner) {
                noWinner = true;
                break;
            }
        }
        if (noWinner)
            System.out.println("Stalemate!");
        else if (newGame.whoseTurn)
            System.out.println("Crosses win!");
        else
            System.out.println("Noughts win!");
    }
}

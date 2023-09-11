import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static final String RED = "\033[38;5;196m";
    public static final String GREEN = "\033[38;5;82m";
    public static final String GOLD = "\033[38;5;3m";
    public static final String YELLOW = "\033[38;5;226m";
    public static final String BLUE = "\033[38;5;21m";
    public static final String PURPLE = "\033[38;5;93m";
    public static final String CYAN = "\033[38;5;51m";
    public static final String PINK = "\033[38;5;201m";
    public static final String ORANGE = "\033[38;5;202m";
    public static final String BLACK = "\033[38;5;232m";
    public static final String SCORE_COLOR = "\033[38;5;161m";
    public static final String Percentage_COLOR = "\033[38;5;40m";
    public static final String TIME_COLOR = "\033[38;5;4m";
    public static final String GAMEOVER_COLOR = "\033[38;5;88m";
    public static final String Menu_COLOR = "\033[38;5;80m";
    public static final String Width_COLOR = "\033[38;5;22m";
    public static final String Leaderboard1_COLOR = "\033[38;5;85m";
    public static final String Leaderboard2_COLOR = "\033[38;5;86m";
    public static final String RESET = "\033[0m"; // Text Reset
    public static final String WHITE_BACKGROUND = "\033[48;5;253m";
    public static final String DARKGREEN_BACKGROUND = "\033[48;5;23m";
    public static final String MOVE_COLOR = "\033[38;5;118m";
    public static final String WHITE_BACKGROUND_BLINKING = "\033[6;46;5;17m";

    static Scanner scanner = new Scanner(System.in);
    static int n = 19;
    static int m = n * 3;
    static int[][] numbers = new int[n + 2][m + 2];

    static int row = n / 2 + 1;
    static int column = m / 2 + 1;
    static int score = 0;
    static int up;
    static int down;
    static int left;
    static int right;
    static int up_left;
    static int up_right;
    static int down_left;
    static int down_right;
    static boolean end = false;
    static long start;
    static long finish;
    static long timeElapsed;
    static String username;

    static boolean Exit = false;
    public static void main(String[] args) throws InterruptedException {
        while (!Exit)
        {
            print_Menu();
            String action = scanner.next();
            System.out.print(RESET);
            if (action.equals("1")){                    //New Game
                int max = 100;
                System.out.println(GREEN + "loading..." .indent(55));
                for (int i = 0; i <= max; i++) {
                    Thread.sleep(20);
                    System.out.print(String.format("\r%s", progressBar(i, max)));
                }
                SystemCLS();
                start = System.currentTimeMillis();         //timer start
                play();

            } else if (action.equals("2")) {                //Changing width
                SystemCLS();
                System.out.print(Width_COLOR);
                System.out.println("^ The width of the playing field must be at least 19 and Odd ^");
                do {
                    System.out.print("Enter the width of the playing field :");
                    n = scanner.nextInt();
                    if (n >= 19 && n % 2 == 1){
                        break;
                    }
                    else {
                        SystemCLS();
                        System.out.println("Pay Attention! The Width Must Be At Least 19 and Odd.");
                    }
                }while (true);
                System.out.println(RESET);
                print_Menu();
            }
            else if (action.equals("3")){               //Leaderboard
                SystemCLS();
                System.out.println(Leaderboard2_COLOR + "Username:     Score:     time:".indent(30) + RESET);
                System.out.print(Leaderboard1_COLOR);
                read_records();
                System.out.print(RESET);
                System.out.print("\t\t\t   Want to do something else?");
                String YESorNO = scanner.next().toLowerCase();
                if (YESorNO.equals("no") || YESorNO.equals("n")) {
                    Exit = true;
                }
            }
            else if (action.equals("4")) {
                break;
            }
        }
        SystemCLS();
        System.out.println(RED + "Thanks for playing :)");
    }

    public static String progressBar(int currentValue, int maxValue) {
        int progressBarLength = 33; //
        if (progressBarLength < 9 || progressBarLength % 2 == 0) {
            throw new ArithmeticException("formattedPercent.length() = 9! + even number of chars (one for each side)");
        }
        int currentProgressBarIndex = (int) Math.ceil(((double) progressBarLength / maxValue) * currentValue);
        String formattedPercent = String.format(" %5.1f %% ", (100 * currentProgressBarIndex) / (double) progressBarLength);
        int percentStartIndex = ((progressBarLength - formattedPercent.length()) / 2);

        StringBuilder sb = new StringBuilder();
        sb.append("                                           [");
        for (int progressBarIndex = 0; progressBarIndex < progressBarLength; progressBarIndex++) {
            if (progressBarIndex <= percentStartIndex - 1
                    ||  progressBarIndex >= percentStartIndex + formattedPercent.length()) {
                sb.append(currentProgressBarIndex <= progressBarIndex ? " " : "=");
            } else if (progressBarIndex == percentStartIndex) {
                sb.append(formattedPercent);
            }
        }
        sb.append("]");
        return sb.toString();
    }

    public static void save_records(String Username , int Score , long time){
        try {
//            File file = new File("C:\\Users\\Pooria\\Desktop\\Code\\Java\\Project\\Greed\\Records.txt");
            File file = new File("Records.txt");
            if (file.createNewFile()) {
                System.out.println("File created: " + file.getName());
            } else {
                System.out.println("File already exists.");
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        long minutes = time / 60000;
        try {
//            FileWriter file = new FileWriter("C:\\Users\\Pooria\\Desktop\\Code\\Java\\Project\\Greed\\Records.txt" , true);
            FileWriter file = new FileWriter("Records.txt" , true);
            file.append(Username + "          " + Score + "       " + minutes + ":" + (time - minutes * 60000) / 1000 + "\n");
            file.close();
            System.out.println("save Successfully");
        }catch (IOException e){
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public static void read_records(){
        try{
//            File file = new File("C:\\Users\\Pooria\\Desktop\\Code\\Java\\Project\\Greed\\Records.txt");
            File file = new File("Records.txt");
            Scanner reader = new Scanner(file);
            while (reader.hasNextLine()){
                String record = reader.nextLine();
                System.out.println(record.indent(30));
            }
            reader.close();
        }catch (FileNotFoundException e){
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public static void print_Menu(){
        SystemCLS();
        System.out.println(Menu_COLOR + "@ @ @  GREED  @ @ @".indent(50));
        System.out.println("Welcome to the Number Eater game".indent(43));
        System.out.println(
                        "          00000           00000          \n".indent(40) +
                        "         0000000         0000000         \n".indent(40) +
                        "         0000000         0000000         \n".indent(40) +
                        "          00000           00000          \n".indent(40) +
                        "  1111                             1111  \n".indent(40) +
                        " 222222                           222222 \n".indent(40) +
                        "  333333                         333333  \n".indent(40) +
                        "   444444                       444444   \n".indent(40) +
                        "    5555555                   5555555    \n".indent(40) +
                        "      6666666               6666666      \n".indent(40) +
                        "         777777           777777         \n".indent(40) +
                        "            88888888888888888            \n".indent(40) +
                        "              9999999999999              ".indent(40));
        System.out.println("1.New Game".indent(35));
        System.out.println("2.Changing the width of the playing field".indent(35));
        System.out.println("3.Leaderboard".indent(35));
        System.out.println("4.Exit the Game".indent(35));
        System.out.print("\t\t\t\t   Action :" + GREEN);
    }

    public static void setGlobal() {
        if (row > 1)
            up = numbers[row - 1][column];
        if (row < n)
            down = numbers[row + 1][column];
        if (column > 1)
            left = numbers[row][column - 1];
        if (column < m)
            right = numbers[row][column + 1];
        if (row > 1 && column > 1)
            up_left = numbers[row - 1][column - 1];
        if (row > 1 && column < m)
            up_right = numbers[row - 1][column + 1];
        if (row < n && column > 1)
            down_left = numbers[row + 1][column - 1];
        if (row < n && column < m)
            down_right = numbers[row + 1][column + 1];
    }

    public static void setTheGameBoard() {
        end = false;
        m = n * 3;
        numbers = new int[n + 2][m + 2];
        row = n / 2 + 1;
        column = m / 2 + 1;
        for (int i = 0; i <= n + 1; i++) {
            for (int j = 0; j <= m + 1; j++) {
                if (i == 0 || i == n + 1 || j == 0 || j == m + 1) {
                    numbers[i][j] = -1;
                } else {
                    numbers[i][j] = (int) (Math.random() * 9 + 1);
                }
            }
        }
        numbers[row][column] = 0;
        score = 0;
        printBoard();
    }

    public static void printBoard() {
        SystemCLS();
        setGlobal();
        for (int a = 1; a <= n; a++) {
            for (int b = 1; b <= m; b++) {
                if (movecheck(a, b)) {
                    if (numbers[a][b] != -1) {
                        if (numbers[a][b] == 1) {
                            System.out.print(WHITE_BACKGROUND + RED + numbers[a][b] + " " + RESET);
                        } else if (numbers[a][b] == 2) {
                            System.out.print(WHITE_BACKGROUND + GREEN + numbers[a][b] + " " + RESET);
                        } else if (numbers[a][b] == 3) {
                            System.out.print(WHITE_BACKGROUND + GOLD + numbers[a][b] + " " + RESET);
                        } else if (numbers[a][b] == 4) {
                            System.out.print(WHITE_BACKGROUND + YELLOW + numbers[a][b] + " " + RESET);
                        } else if (numbers[a][b] == 5) {
                            System.out.print(WHITE_BACKGROUND + BLUE + numbers[a][b] + " " + RESET);
                        } else if (numbers[a][b] == 6) {
                            System.out.print(WHITE_BACKGROUND + PURPLE + numbers[a][b] + " " + RESET);
                        } else if (numbers[a][b] == 7) {
                            System.out.print(WHITE_BACKGROUND + CYAN + numbers[a][b] + " " + RESET);
                        } else if (numbers[a][b] == 8) {
                            System.out.print(WHITE_BACKGROUND + PINK + numbers[a][b] + " " + RESET);
                        } else if (numbers[a][b] == 9) {
                            System.out.print(WHITE_BACKGROUND + ORANGE + numbers[a][b] + " " + RESET);
                        }
                    } else {
                        System.out.print(WHITE_BACKGROUND + "  " + RESET);
                    }
                } else {
                    if (numbers[a][b] == 1) {
                        System.out.print(RED + numbers[a][b] + " ");
                    } else if (numbers[a][b] == 2) {
                        System.out.print(GREEN + numbers[a][b] + " ");
                    } else if (numbers[a][b] == 3) {
                        System.out.print(GOLD + numbers[a][b] + " ");
                    } else if (numbers[a][b] == 4) {
                        System.out.print(YELLOW + numbers[a][b] + " ");
                    } else if (numbers[a][b] == 5) {
                        System.out.print(BLUE + numbers[a][b] + " ");
                    } else if (numbers[a][b] == 6) {
                        System.out.print(PURPLE + numbers[a][b] + " ");
                    } else if (numbers[a][b] == 7) {
                        System.out.print(CYAN + numbers[a][b] + " ");
                    } else if (numbers[a][b] == 8) {
                        System.out.print(PINK + numbers[a][b] + " ");
                    } else if (numbers[a][b] == 9) {
                        System.out.print(ORANGE + numbers[a][b] + " ");
                    } else if (numbers[a][b] == 0) {
                        System.out.print(WHITE_BACKGROUND_BLINKING + "@" + " " + RESET);
                    } else if (a == 0 || a == n + 1) {
                        System.out.print(numbers[a][b] + RESET);
                    } else if (numbers[a][b] == -1) {
                        System.out.print("  ");
                    } else if (numbers[a][b] == 100) {
                        System.out.print(WHITE_BACKGROUND_BLINKING + "* " + RESET);
                    }
                }
            }
            System.out.println(RESET);
        }

        System.out.print("\n\t\t\t" + SCORE_COLOR + "Score: " + score + RESET + Percentage_COLOR + "\t Percentage Eaten: ");
        System.out.printf("%.2f", ((double) score / (3 * n * n)) * 100);
        System.out.print("%" + RESET + "\t" + TIME_COLOR);
        finish = System.currentTimeMillis();
        timeElapsed = finish - start;
        long minutes = timeElapsed / 60000;
        System.out.printf("time: %02d:%02d", minutes, (timeElapsed - minutes * 60000) / 1000);
        System.out.println(RESET);

        System.out.print(DARKGREEN_BACKGROUND + "Move to:" + RESET + " " + MOVE_COLOR);
    }

    public static void endGame() {

        boolean checkW = false;
        boolean checkS = false;
        boolean checkA = false;
        boolean checkD = false;
        boolean checkQ = false;
        boolean checkE = false;
        boolean checkZ = false;
        boolean checkC = false;

        if (up == -1 || row - up < 1)
            checkW = true;
        if (down == -1 || row + down >= n + 1)
            checkS = true;
        if (left == -1 || column - left < 1)
            checkA = true;
        if (right == -1 || column + right >= m + 1)
            checkD = true;
        if (up_left == -1 || (row - up_left < 1 || column - up_left < 1))
            checkQ = true;
        if (up_right == -1 || (row - up_right < 1 || column + up_right >= m + 1))
            checkE = true;
        if (down_left == -1 || (row + down_left >= n + 1 || column - down_left < 1))
            checkZ = true;
        if (down_right == -1 || (row + down_right >= n + 1 || column + down_right >= m + 1))
            checkC = true;

        if (checkW && checkS && checkA && checkD && checkQ && checkE && checkZ && checkC) {
            end = true;
        }
    }

    public static boolean movecheck(int a, int b) {
        boolean flag = false;
        if (row - up >= 1) {
            for (int i = row - 1; i >= row - up; i--)
                if (a == i && b == column)
                    flag = true;
        }
        if (row + down < n + 1) {
            for (int i = row + 1; i <= row + down; i++)
                if (a == i && b == column)
                    flag = true;
        }
        if (column - left >= 1) {
            for (int i = column - 1; i >= column - left; i--)
                if (a == row && b == i)
                    flag = true;
        }
        if (column + right < m + 1) {
            for (int i = column + 1; i <= column + right; i++)
                if (a == row && b == i)
                    flag = true;
        }
        if (row - up_left >= 1 && column - up_left >= 1) {
            for (int i = row - 1, j = column - 1; i >= row - up_left && j >= column - up_left; i--, j--)
                if (a == i && b == j)
                    flag = true;
        }
        if (row - up_right >= 1 && column + up_right < m + 1) {
            for (int i = row - 1, j = column + 1; i >= row - up_right && j <= column + up_right; i--, j++)
                if (a == i && b == j)
                    flag = true;
        }
        if (row + down_left < n + 1 && column - down_left >= 1) {
            for (int i = row + 1, j = column - 1; i <= row + down_left && j >= column - down_left; i++, j--)
                if (a == i && b == j)
                    flag = true;
        }
        if (row + down_right < n + 1 && column + down_right < m + 1) {
            for (int i = row + 1, j = column + 1; i <= row + down_right && j <= column + down_right; i++, j++)
                if (a == i && b == j)
                    flag = true;
        }
        return flag;
    }

    public static void play() {
        setTheGameBoard();
        String input;
        while (!end){
            printBoard();
            input = scanner.next().toLowerCase();
            int move = 0;                           //The amount to be moved
            if (input.equals("w")) {
                if (up == -1 || row - up < 1)
                    continue;

                numbers[row][column] = -1;
                move = numbers[row - 1][column];
                for (int i = 0; i < move; i++) {
                    if (numbers[row - 1][column] != -1) {
                        score++;
                    }
                    numbers[row - 1][column] = -1;
                    row--;
                }
                numbers[row][column] = 0;
            }
            else if (input.equals("s")) {
                if (down == -1 || row + down >= n + 1)
                    continue;

                numbers[row][column] = -1;
                move = numbers[row + 1][column];
                for (int i = 0; i < move; i++) {
                    if (numbers[row + 1][column] != -1) {
                        score++;
                    }
                    numbers[row + 1][column] = -1;
                    row++;
                }
                numbers[row][column] = 0;
            }
            else if (input.equals("a")) {
                if (left == -1 || column - left < 1)
                    continue;

                numbers[row][column] = -1;
                move = numbers[row][column - 1];
                for (int i = 0; i < move; i++) {
                    if (numbers[row][column - 1] != -1) {
                        score++;
                    }
                    numbers[row][column - 1] = -1;
                    column--;
                }
                numbers[row][column] = 0;
            }
            else if (input.equals("d")) {
                if (right == -1 || column + right >= m + 1)
                    continue;

                numbers[row][column] = -1;
                move = numbers[row][column + 1];
                for (int i = 0; i < move; i++) {
                    if (numbers[row][column + 1] != -1) {
                        score++;
                    }
                    numbers[row][column + 1] = -1;
                    column++;
                }
                numbers[row][column] = 0;
            }
            else if (input.equals("q")) {
                if (up_left == -1 || (row - up_left < 1 || column - up_left < 1))
                    continue;

                numbers[row][column] = -1;
                move = numbers[row - 1][column - 1];
                for (int i = 0; i < move; i++) {
                    if (numbers[row - 1][column - 1] != -1) {
                        score++;
                    }
                    numbers[row - 1][column - 1] = -1;
                    row--;
                    column--;
                }
                numbers[row][column] = 0;
            }
            else if (input.equals("e")) {
                if (up_right == -1 || (row - up_right < 1 || column + up_right >= m + 1))
                    continue;

                numbers[row][column] = -1;
                move = numbers[row - 1][column + 1];
                for (int i = 0; i < move; i++) {
                    if (numbers[row - 1][column + 1] != -1) {
                        score++;
                    }
                    numbers[row - 1][column + 1] = -1;
                    row--;
                    column++;
                }
                numbers[row][column] = 0;
            }
            else if (input.equals("z")) {
                if (down_left == -1 || (row + down_left >= n + 1 || column - down_left < 1))
                    continue;

                numbers[row][column] = -1;
                move = numbers[row + 1][column - 1];
                for (int i = 0; i < move; i++) {
                    if (numbers[row + 1][column - 1] != -1) {
                        score++;
                    }
                    numbers[row + 1][column - 1] = -1;
                    row++;
                    column--;
                }
                numbers[row][column] = 0;
            }
            else if (input.equals("c")) {
                if (down_right == -1 || (row + down_right >= n + 1 || column + down_right >= m + 1))
                    continue;

                numbers[row][column] = -1;
                move = numbers[row + 1][column + 1];
                for (int i = 0; i < move; i++) {
                    if (numbers[row + 1][column + 1] != -1) {
                        score++;
                    }
                    numbers[row + 1][column + 1] = -1;
                    row++;
                    column++;
                }
                numbers[row][column] = 0;
            }
            else {
                numbers[row][column] = 0;
                continue;
            }
            printBoard();
            endGame();
        }

        numbers[row][column] = 100;             //for * in the end
        printBoard();
        if (score == (n * m))
            System.out.println(GAMEOVER_COLOR + "$$$ WINNER WINNER CHICKEN DINNER $$$".indent(50) + RESET);
        else
            System.out.println(GAMEOVER_COLOR + "*** GAME OVER ***".indent(50) + RESET);
        System.out.println(Menu_COLOR + "Enter Your Username :" + GREEN);
        username = scanner.next();
        System.out.print(RESET);
        save_records(username , score , timeElapsed);

        System.out.println("Want to do something else?");
        String YESorNO = scanner.next().toLowerCase();
        if (YESorNO.equals("no") || YESorNO.equals("n")) {
            Exit = true;
        }
    }

    public static void SystemCLS() {
        //way 1:
//        try {
//            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
//        } catch (InterruptedException | IOException e) {
//            throw new RuntimeException(e);
//        }
        //way 2:
        System.out.print("\033\143");
    }
}
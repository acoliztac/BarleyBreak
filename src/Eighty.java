import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Eighty {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        int a, b;
        int counter = -1;

        System.out.println("Добро пожаловать в игру \"Восьмяшки\"!");
        System.out.println("");
        System.out.println("Вам предстоит расставить все числа по возрастанию (слева-направо и сверху-вниз), а число \"0\" поместить в конец таблицы");
        System.out.println("");
        System.out.println("Для полного счастья введите желаемый размер поля.");

        String sHeight = "Высота : ";
        String sWidth = "Ширина : ";

        a = setSize(reader, sHeight);
        b = setSize(reader, sWidth);

        int[][] matrix = new int[a][b];
        int[][] result = new int[a][b];

        fillingMatrix(matrix, 0, a, b, true);
        fillingMatrix(result, 1, a, b, false);
        showMatrix(matrix, a, b);
//        showMatrix(result, a, b);

        boolean exit = false;
        boolean finish = false;

        while (!exit) {
            if (finish)
                break;

            int inner;
            System.out.print("Ваш следующий ход ... ");
            String s = reader.readLine();
            System.out.println("");

            if (s.equals("exit")) {
                exit = true;
                continue;
            }

            try {
                inner = Integer.parseInt(s);
                if (inner > a * b - 1 || inner < 1)
                    throw new NumberFormatException();
            } catch (NumberFormatException e) {
                System.out.println("Повторите ввод числа.");
                continue;
            }

            int x = -1;
            int y = -1;
            for (int i = 0; i < a; i++){
                for (int j = 0; j < b; j++){
                    if (inner == matrix[i][j]){
                        x = i;
                        y = j;
                    }
                }
            }

            if (x != -1 || y != -1){
                //ищем соседей
                String[] dimension = {"Слева", "Справа", "Снизу", "Сверху"};
                int[] xs = {x, x, x + 1, x - 1};
                int[] ys = {y - 1, y + 1, y, y};
                for (int i = 0; i < 4; i++){
                    try {
                        if (matrix[xs[i]][ys[i]] == 0) {
                            int buf = matrix[x][y];
                            matrix[x][y] = matrix[xs[i]][ys[i]];
                            matrix[xs[i]][ys[i]] = buf;
                            showMatrix(matrix, a, b);
                            counter++;
                            break;
                        }
                    } catch (Exception e) {
                    }
                }
            }
            finish = isWin(matrix, result, a, b);
            if (finish){
                System.out.println("Победа!");
                String action;
                if (counter % 10 == 1)
                    action = " действие.";
                else if (counter % 10 < 5 && counter % 10 != 0)
                    action = " действия.";
                else action = " действий.";
                System.out.println("Вы справились с заданием за " + counter + action);
            }
        }
        reader.close();
    }

    private static int setSize(BufferedReader reader, String s) throws IOException {

        String width;
        int a;
        while (true) {
            try {
                System.out.print(s);
                width = reader.readLine();
                a = Integer.parseInt(width);
                if (a > 10 || a < 3)
                    throw new NumberFormatException();
                break;
            } catch (NumberFormatException e) {
                System.out.println("Что-то пошло не так. Давай ещё раз! Я жду от тебя целое число от 3 до 10");
            }
        }
        return a;
    }
    private static boolean isWin(int[][] matrix, int[][] result, Integer a, Integer b) {
        boolean win = true;
        for (int i = 0; i < a; i++){
            for (int j = 0; j < b; j++){
                if (matrix[i][j] != result[i][j])
                    win = false;
            }
        }
        return win;
    }
    private static void fillingMatrix(int[][] matrix, Integer k, Integer a, Integer b, boolean b1) {
        ArrayList<Integer> arrayList = new ArrayList<Integer>();
        if (!b1) {
            for (int i = 0; i < a * b; i++){
                arrayList.add(i);
            }

        } else {
            while (arrayList.size() < a * b){
                int i = (int) (Math.random()*(a * b));
                if (arrayList.contains(i))
                    continue;
                arrayList.add(i);
            }
        }



        for (int i = 0; i < a; i++){
            for (int j = 0; j < b; j++){
                matrix[i][j] = arrayList.get(k++);
                if (k == a * b)
                    k = 0;
            }
        }
    }
    private static void showMatrix(int[][] matrix, Integer a, Integer b) {
        String mi = "+---";
        if (a * b > 10)
            mi = "+----";
        for (int i = 0; i < b; i++){
            System.out.print(mi);
        }
        System.out.println("+");
        for (int i = 0; i < a; i++){
            System.out.print("| ");
            for (int j = 0; j < b; j++){
                if (a * b > 9){
                    if (matrix[i][j] < 10)
                        System.out.print(" ");
                }
                System.out.print(matrix[i][j]);
                System.out.print(" | ");
            }
            System.out.println("");
            for (int j = 0; j < b; j++){
                System.out.print(mi);
            }
            System.out.println("+");
        }
    }
}

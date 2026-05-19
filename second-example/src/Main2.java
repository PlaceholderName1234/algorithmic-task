import java.util.ArrayList;
import java.util.Scanner;

public class Main2 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Введите размер массива N: ");
        int N = Integer.parseInt(scanner.nextLine());

        ArrayList<Double> array = new ArrayList<>();

        System.out.println("Введите " + N + " вещественных чисел:");
        for (int i = 0; i < N; i++) {
            double num = Double.parseDouble(scanner.nextLine());
            array.add(num);
        }

        int counter = 0;
        for (double num : array) {
            if (num < 0) {
                counter++;
            }
        }

        System.out.println("Массив: " + array);
        System.out.println("Количество отрицательных элементов: " + counter);
        scanner.close();
    }
}

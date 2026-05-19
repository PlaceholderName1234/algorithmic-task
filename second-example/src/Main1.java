import java.util.ArrayList;

public class Main1 {
    public static void main(String[] args) {
        String memo = "1 2 3 4 5 6 7 8 9 10";

        String[] numbersStr = memo.split(" ");

        ArrayList<Integer> numbers = new ArrayList<>();

        for (String numStr : numbersStr) {
            int num = Integer.parseInt(numStr);
            numbers.add(num);
        }

        System.out.println("Исходный массив: " + numbers);

        int sumEven = 0;
        for (int num : numbers) {
            if (num % 2 == 0) {
                sumEven += num;
                System.out.println("Чётное число: " + num);
            }
        }

        System.out.println("Сумма чётных элементов: " + sumEven);
    }
}

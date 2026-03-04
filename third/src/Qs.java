import java.util.ArrayDeque;
import java.util.Queue;

public class Qs {
    public static void main(String[] args) {
        int temp;
        int[] numbers = new int[] {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};

        Queue<Integer> oddQueue = new ArrayDeque<>();
        Queue<Integer> evenQueue = new ArrayDeque<>();

        for (int i = 0; i < 10; i++) {
            if ((i) % 2 == 0) {
                evenQueue.add(numbers[i]);
            } else {
                oddQueue.add(numbers[i]);
            }
        }
        if (!evenQueue.isEmpty()) {
            temp = evenQueue.poll();
            System.out.println("Первый элемент четной: " + temp);
            while (!evenQueue.isEmpty()) {
                temp = evenQueue.poll();
            }
            System.out.println("Последний элемент четной: " + temp);
        }
        else {
            System.out.println("В четной нет элементов");
        }

        if (!oddQueue.isEmpty()) {
            temp = oddQueue.poll();
            System.out.println("Первый элемент нечетной: " + temp);
            while (!oddQueue.isEmpty()) {
                temp = oddQueue.poll();
            }
            System.out.println("Последний элемент нечетной: " + temp);
        }
        else {
            System.out.println("В нечетной нет элементов");
        }
    }
}

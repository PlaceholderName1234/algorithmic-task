import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Scanner;

public class List {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        LinkedList<Character> list = new LinkedList<>();

        System.out.println("Введите символы (0 для выхода):");

        while (true) {
            String input = scanner.nextLine();
            if (input.equalsIgnoreCase("0")) {
                break;
            }
            list.add(input.charAt(0));
        }

        System.out.println("\nИсходный список");
        for (char c : list) {
            System.out.print(c + " ");
        }

        int i = 0;
        while (i < list.size() - 1) {
            if (list.get(i) == '&') {
                list.remove(i + 1);
            }
            i++;

        }

        System.out.println("\nНовый список");
        for (char c : list) {
            System.out.print(c + " ");
        }
    }
}

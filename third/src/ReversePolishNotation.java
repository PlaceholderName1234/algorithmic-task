import java.util.Stack;
import java.util.Scanner;

public class ReversePolishNotation {
    public static void main(String[] args) {
        double a, b, res;
        Scanner scanner = new Scanner(System.in);

        System.out.println("Введите выражение в обратной польской записи:");
        String expression = scanner.nextLine();
        scanner.close();

        Stack<Double> st = new Stack<>();
        String[] signs = expression.split(" ");

        for (String sign : signs) {
            switch (sign) {
                case "+":
                    b = st.pop();
                    a = st.pop();
                    st.push(a + b);
                    break;
                case "-":
                    b = st.pop();
                    a = st.pop();
                    st.push(a - b);
                    break;
                case "*":
                    b = st.pop();
                    a = st.pop();
                    st.push(a * b);
                    break;
                case "/":
                    b = st.pop();
                    a = st.pop();
                    st.push(a / b);
                    break;
                default:
                    st.push(Double.parseDouble(sign));
                    break;
            }
        }
        res = st.pop();
        System.out.println("Результат: " + res);
    }
}
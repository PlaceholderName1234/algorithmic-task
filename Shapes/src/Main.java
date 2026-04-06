public class Main {
    public static void main(String[] args) {

        Circle circle = new Circle("Круг1", Color.RED, 5.0);
        Rectangle rectangle = new Rectangle("Прямоулогьник", Color.BLUE, 4.0, 6.0);
        Triangle triangle = new Triangle("Треугольник", Color.GREEN, 3.0, 4.0, 5.0);

        System.out.printf(circle.toString());
        circle.setRadius(7.0);
        circle.setColor(Color.BLUE);
        System.out.printf(circle.toString());
        System.out.println();

        try {
            circle.setRadius(-7.0);
        } catch (IllegalArgumentException e) {
            System.out.println("Ошибка: " + e.getMessage());
        }

        try {
            triangle.setSides(1, 1, 3);
        } catch (IllegalArgumentException e) {
            System.out.println("Ошибка: " + e.getMessage());
        }

//        triangle.setSides(4, 12, 13);
//        double[] arr = triangle.getSides();
//        System.out.println(Arrays.toString(arr));

        System.out.printf(rectangle.toString());
        System.out.printf(triangle.toString());
    }
}
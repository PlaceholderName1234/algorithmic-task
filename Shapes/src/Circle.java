public class Circle extends AbstractShape {
    private double radius;

    public Circle(String name, Color color, double radius) {
        super(name, color);
        if (radius <= 0) {
            throw new IllegalArgumentException("Радиус должен быть положительным числом\n" );
        }
        this.radius = radius;
    }

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        if (radius <= 0) {
            throw new IllegalArgumentException("Радиус должен быть положительным числом\n");
        }
        this.radius = radius;
    }

    @Override
    public double calcArea() {
        return Math.PI * radius * radius;
    }

    @Override
    public double calcPerimeter() {
        return 2 * Math.PI * radius;
    }

    @Override
    public String toString() {
        return String.format("%s (цвет: %s, радиус: %.2f, площадь: %.2f, периметр: %.2f)%n",
                getName(), getColor(), radius, calcArea(), calcPerimeter());
    }
}

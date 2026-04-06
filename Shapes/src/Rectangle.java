public class Rectangle extends AbstractShape {
    private double width;
    private double height;

    public Rectangle(String name, Color color, double width, double height) {
        super(name, color);
        if (width <= 0 || height <= 0) {
            throw new IllegalArgumentException("Ширина и высота должны быть положительными числами\n");
        }
        this.width = width;
        this.height = height;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        if (width <= 0) {
            throw new IllegalArgumentException("Ширина должна быть положительным числом\n");
        }
        this.width = width;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        if (height <= 0) {
            throw new IllegalArgumentException("Высота должна быть положительным числом\n");
        }
        this.height = height;
    }

    @Override
    public double calcArea() {
        return width * height;
    }

    @Override
    public double calcPerimeter() {
        return 2 * (width + height);
    }

    @Override
    public String toString() {
        return String.format("%s (цвет: %s, ширина: %.2f, высота: %.2f, площадь: %.2f, периметр: %.2f)%n",
                getName(), getColor(), width, height, calcArea(), calcPerimeter());
    }
}

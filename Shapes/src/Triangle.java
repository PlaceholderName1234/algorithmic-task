public class Triangle extends AbstractShape {
    private double sideA;
    private double sideB;
    private double sideC;

    public Triangle(String name, Color color, double sideA, double sideB, double sideC) {
        super(name, color);
        if (!isValid(sideA, sideB, sideC)) {
            throw new IllegalArgumentException("Некорректные стороны треугольника\n");
        }
        this.sideA = sideA;
        this.sideB = sideB;
        this.sideC = sideC;
        double[] sides = {sideA, sideB, sideC};
    }

    private boolean isValid(double a, double b, double c) {
        return a > 0 && b > 0 && c > 0 &&
                (a + b > c) && (a + c > b) && (b + c > a);
    }

    public double[] getSides() {
        return new double[]{sideA, sideB, sideC};
    }

    public void setSides(double sideA, double sideB, double sideC) {
        if (isValid(sideA, sideB, sideC)) {
            this.sideA = sideA;
            this.sideB = sideB;
            this.sideC = sideC;
        } else {
            throw new IllegalArgumentException("Некорректные стороны треугольника\n");
        }
    }

    @Override
    public double calcArea() {
        double p = calcPerimeter() / 2;
        return Math.sqrt(p * (p - sideA) * (p - sideB) * (p - sideC));
    }

    @Override
    public double calcPerimeter() {
        return sideA + sideB + sideC;
    }

    @Override
    public String toString() {
        return String.format("%s (цвет: %s, стороны: [%.2f, %.2f, %.2f], площадь: %.2f, периметр: %.2f)%n",
                getName(), getColor(), sideA, sideB, sideC, calcArea(), calcPerimeter());
    }
}

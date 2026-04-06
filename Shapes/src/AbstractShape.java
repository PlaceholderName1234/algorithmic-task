public abstract class AbstractShape implements Shape {

    private final String name;

    private Color color;

    public AbstractShape(String name, Color color) {
        this.name = name;
        this.color = color;
    }

    @Override
    public String getName() {
        return name;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    @Override
    public String toString() {
        return "{" +
                "name='" + name + '\'' +
                ", color=" + color +
                '}';
    }
}

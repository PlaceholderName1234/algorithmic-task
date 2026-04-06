import java.io.Serializable;

public class Element implements Cloneable, Serializable {

    private int intValue;

    public Element(int intValue) {
        this.intValue = intValue;
    }

    public Element(Element element) {
        this(element.intValue);
    }

    public int getIntValue() {
        return intValue;
    }

    public void setIntValue(int intValue) {
        this.intValue = intValue;
    }

    @Override
    public String toString() {
        return String.valueOf(intValue);
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
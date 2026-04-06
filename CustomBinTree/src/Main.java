import java.util.Collection;
import java.util.Iterator;

public class Main {

    public static void main(String[] args) throws CloneNotSupportedException {
        Element rootElement = new Element(50);
        Collection tree = new CustomBinaryTree(rootElement);

        for (int i = 0; i < 20; i++) {
            tree.add(new Element((int) (Math.random() * 101)));
        }
        System.out.println("Размер: " + tree.size());

        System.out.println("\n" + tree);

        Element searchElement = new Element(50);
        System.out.println("Содержит " + searchElement.getIntValue() + "? " + tree.contains(searchElement));

        searchElement = new Element(500);
        System.out.println("Содержит " + searchElement.getIntValue() + "? " + tree.contains(searchElement));

        System.out.println("\nОбход через итератор:");
        Iterator iterator = tree.iterator();
        while (iterator.hasNext()) {
            CustomBinaryTree.Node node = (CustomBinaryTree.Node) iterator.next();
            System.out.print(node.getValue() + " ");
        }
        System.out.println();

        System.out.println("\nУдаление узла 50.");
        Element toRemove = new Element(50);
        tree.remove(toRemove);
        System.out.println("Размер после удаления: " + tree.size());

        System.out.println("\nОчистка дерева");
        tree.clear();
        System.out.println(tree);
    }
}
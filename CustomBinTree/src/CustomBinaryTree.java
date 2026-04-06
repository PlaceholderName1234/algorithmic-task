import java.util.AbstractCollection;
import java.util.Iterator;

public class CustomBinaryTree extends AbstractCollection implements Cloneable {

    private Node root;
    private int size;

    public static class Node {
        private Element value;
        private Node left;
        private Node right;

        public Node(Element value, Node left, Node right) {
            this.value = value;
            this.left = left;
            this.right = right;
        }

        public Element getValue() {
            return value;
        }

        public void setValue(Element value) {
            this.value = value;
        }

        public Node getLeft() {
            return left;
        }

        public Node getRight() {
            return right;
        }

        public void setLeft(Node left) {
            this.left = left;
        }

        public void setRight(Node right) {
            this.right = right;
        }

        public Node deepCopy() throws CloneNotSupportedException {
            Node leftCopy = left != null ? left.deepCopy() : null;
            Node rightCopy = right != null ? right.deepCopy() : null;
            return new Node((Element) value.clone(), leftCopy, rightCopy);
        }

        @Override
        protected Object clone() throws CloneNotSupportedException {
            return deepCopy();
        }

        @Override
        public String toString() {
            return String.valueOf(value.getIntValue());
        }
    }

    public CustomBinaryTree() {
        root = null;
        size = 0;
    }

    public CustomBinaryTree(Element rootValue) {
        root = new Node(rootValue, null, null);
        size = 1;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    private int compare(Element a, Element b) {
        return Integer.compare(a.getIntValue(), b.getIntValue());
    }

    @Override
    public boolean add(Object value) {
        Element element = (Element) value;
        root = addRecursive(root, element);
        return true;
    }

    private Node addRecursive(Node node, Element element) {
        if (node == null) {
            size++;
            return new Node(element, null, null);
        }

        int cmp = compare(element, node.value);
        if (cmp <= 0) {
            node.left = addRecursive(node.left, element);
        } else {
            node.right = addRecursive(node.right, element);
        }
        return node;
    }

    @Override
    public boolean contains(Object value) {
        if (!(value instanceof Element)) {
            return false;
        }
        Element element = (Element) value;
        return containsRecursive(root, element);
    }

    private boolean containsRecursive(Node node, Element element) {
        if (node == null) {
            return false;
        }

        int cmp = compare(element, node.value);
        if (cmp < 0) {
            return containsRecursive(node.left, element);
        } else if (cmp > 0) {
            return containsRecursive(node.right, element);
        } else {
            return true;
        }
    }

    @Override
    public boolean remove(Object value) {
        if (!(value instanceof Element)) {
            return false;
        }
        Element element = (Element) value;
        int oldSize = size;
        root = removeRecursive(root, element);
        return size < oldSize;
    }

    private Node removeRecursive(Node node, Element element) {
        if (node == null) {
            return null;
        }

        int cmp = compare(element, node.value);
        if (cmp < 0) {
            node.left = removeRecursive(node.left, element);
        } else if (cmp > 0) {
            node.right = removeRecursive(node.right, element);
        } else {
            if (node.left == null) {
                size--;
                return node.right;
            }
            if (node.right == null) {
                size--;
                return node.left;
            }

            Node smallest = findSmallest(node.right);
            node.value = smallest.value;
            node.right = removeRecursive(node.right, smallest.value);
        }
        return node;
    }

    private Node findSmallest(Node node) {
        return node.left == null ? node : findSmallest(node.left);
    }

    @Override
    public void clear() {
        root = null;
        size = 0;
    }

    @Override
    public Iterator iterator() {
        return new Iterator() {
            private Node[] stack = new Node[100];
            private int top = -1;

            {
                Node current = root;
                while (current != null) {
                    push(current);
                    current = current.left;
                }
            }

            private void push(Node node) {
                if (top + 1 >= stack.length) {
                    Node[] newStack = new Node[stack.length * 2];
                    System.arraycopy(stack, 0, newStack, 0, stack.length);
                    stack = newStack;
                }
                stack[++top] = node;
            }

            private Node pop() {
                return stack[top--];
            }

            private boolean isEmpty() {
                return top == -1;
            }

            @Override
            public boolean hasNext() {
                return !isEmpty();
            }

            @Override
            public Object next() {
                Node node = pop();
                if (node.right != null) {
                    Node current = node.right;
                    while (current != null) {
                        push(current);
                        current = current.left;
                    }
                }
                return node;
            }
        };
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        CustomBinaryTree cloned = (CustomBinaryTree) super.clone();
        if (root != null) {
            cloned.root = root.deepCopy();
        }
        cloned.size = this.size;
        return cloned;
    }

    public Node getRoot() {
        return root;
    }

    @Override
    public String toString() {
        if (root == null) {
            return "Empty";
        }
        return new StringBuilder()
                .append("Size = ")
                .append(size)
                .append("\nElements: ")
                .append(ascendingToString(root))
                .append("\n")
                .toString();
    }

    private String ascendingToString(Node node) {
        if (node == null) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        sb.append(ascendingToString(node.left));
        sb.append(node.value);
        sb.append(" ");
        sb.append(ascendingToString(node.right));
        return sb.toString();
    }
}
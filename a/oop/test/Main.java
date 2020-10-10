class SearchSet {
    private int mElements;
    private BinaryTreeNode mHead;

    public static void main(String[] args)
    {
        SearchSet s = new SearchSet();

        System.out.println(s.contains(5));
        System.out.println(s.getNumberElements());
        s.insert(5);
        System.out.println(s.contains(5));
        System.out.println(s.getNumberElements());
    }

    public SearchSet() {
        mElements = 0;
        mHead = null;
    }

    public void insert(int element) {
        if (this.mHead == null) {
            this.mHead = new BinaryTreeNode(element);
            this.mElements++;
            return;
        }
        BinaryTreeNode checkNode = this.mHead;
        while (true) {
            if (element < checkNode.getValue()) {
                if (checkNode.getLeft() == null) {
                    checkNode.setLeft(new BinaryTreeNode(element));
                    this.mElements++;
                } else {
                    checkNode = checkNode.getLeft();
                }
            }
            else if (element > checkNode.getValue()) {
                if (checkNode.getRight() == null) {
                    checkNode.setRight(new BinaryTreeNode(element));
                    this.mElements++;
                } else {
                    checkNode = checkNode.getRight();
                }
            } else {
                return;
            }
        }
    }

    public int getNumberElements() {
        return this.mElements;
    }

    public boolean contains(int element) {
        BinaryTreeNode checkNode = this.mHead;
        while (true) {
            if (checkNode == null) {
                return false;
            }
            else if (element == checkNode.getValue()) {
                return true;
            }
            else if (element < checkNode.getValue()) {
                checkNode = checkNode.getLeft();
            }
            else if (checkNode.getValue() < element) {
                checkNode = checkNode.getRight();
            }
        }
    }
}

class BinaryTreeNode {
    private int mValue;
    private BinaryTreeNode mLeft;
    private BinaryTreeNode mRight;

    public BinaryTreeNode(int value) {
        this.mValue = value;
        this.mLeft = null;
        this.mRight = null;
    }

    public int getValue() {
        return this.mValue;
    }

    public void setValue(int value) {
        this.mValue = value;
    }

    public BinaryTreeNode getLeft() {
        return this.mLeft;
    }

    public BinaryTreeNode getRight() {
        return this.mRight;
    }

    public void setLeft(BinaryTreeNode node) {
        this.mLeft = node;
    }

    public void setRight(BinaryTreeNode node) {
        this.mRight = node;
    }
}

class FunctionalArray {
    private int size;
    private BinaryTreeNode head;

    public FunctionalArray(int size) {
        this.size = size;
        if (size > 0) {
            this.head = new BinaryTreeNode(0);
            BinaryTreeNode end = this.head;
            for (int jNode = 0; jNode < size; jNode++) {
                end.setRight(new BinaryTreeNode(0));
                end = end.getRight();
            }
        } else {
            this.head = null;
        }
    }

    public void set(int index, int value) {
        if (index < 0 || index >= this.size) {
            throw new ArrayIndexOutOfBoundsException();
        }
        BinaryTreeNode checkNode = this.head;
        while (true) {
            if (index == 0) {
                checkNode.setValue(value);
                return;
            } else {
                checkNode = checkNode.getRight();
                index--;
            }
        }
    }

    public int get(int index) {
        if (index < 0 || index >= this.size) {
            throw new ArrayIndexOutOfBoundsException();
        }
        BinaryTreeNode checkNode = this.head;
        while (true) {
            if (index == 0) {
                return checkNode.getValue();
            } else {
                checkNode = checkNode.getRight();
                index--;
            }
        }
    }
}

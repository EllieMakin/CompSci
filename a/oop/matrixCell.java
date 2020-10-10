class Cell implements Comparable<Cell> {
    private int mWidth;
    private int mHeight;
    private int row;
    private int col;

    public Cell(int row, int col, int mWidth, int mHeight) {
        this.mWidth = mWidth;
        this.mHeight = mHeight;
        this.row = row;
        this.col = col;
    }

    public String getrow() {
        return this.row;
    }

    public int getcol() {
        return this.col;
    }

    @Override
    public int compareTo(Cell other) {

    }
}

class Main {
    public static void main(String[] args) {
        ArrayList<Cell> pList = new ArrayList<Cell>();

       collections.sort(pList);
        for (int jCell = 0; jCell < pList.length; jCell ++) {
            System.out.println(jCell.row + jCell.col);
        }
    }
}

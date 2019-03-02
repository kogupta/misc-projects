import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
  private final int width;
  private final WeightedQuickUnionUF uf;
  private final WeightedQuickUnionUF uf2;
  private final boolean[] isOpen;
  private final int top;
  private final int bot;
  private int open;

  // create n-by-n grid, with all sites blocked
  public Percolation(int n) {
    if (n <= 0) throw new IllegalArgumentException();
    width = n;

    // assumption:
    // top -> n*n
    // bottom -> n*n + 1
    top = width * width;
    bot = top + 1;
    uf = new WeightedQuickUnionUF(top + 2);
    uf2 = new WeightedQuickUnionUF(top + 1);
    isOpen = new boolean[top];
  }

  private int index(int row, int col) {
    return (row - 1) * width + (col - 1);
  }

  // open site (row, col) if it is not open already
  public void open(int row, int col) {
    validate(row, col);

    int idx = index(row, col);
    if (isOpen[idx]) return;

    isOpen[idx] = true;
    open++;

    if (row == 1) {
      // connect to top
      uf.union(idx, top);
      uf2.union(idx, top);
    }

    if (row == width) uf.union(idx, bot);

    // left
    if (col > 1) connectIfOpen(idx, index(row, col - 1));

    // right
    if (col < width) connectIfOpen(idx, index(row, col + 1));

    // top
    if (row > 1) connectIfOpen(idx, index(row - 1, col));

    // bottom
    if (row < width) connectIfOpen(idx, index(row + 1, col));
  }

  private void validate(int n) {
    if (n <= 0) throw new IllegalArgumentException();
    if (n > width) throw new IllegalArgumentException();
  }

  private void validate(int row, int col) {
    validate(row);
    validate(col);
  }

  private void connectIfOpen(int current, int other) {
    if (isOpen[other]) {
      uf.union(current, other);
      uf2.union(current, other);
    }
  }

  // is site (row, col) open?
  public boolean isOpen(int row, int col) {
    validate(row, col);
    return isOpen[index(row, col)];
  }

  // A full site is an open site that can be connected
  // to an open site in the top row via a chain of
  // neighboring (left, right, up, down) open sites.
  // is site (row, col) full?
  public boolean isFull(int row, int col) {
    validate(row, col);

    int idx = index(row, col);
    return uf2.connected(idx, top);
  }

  // number of open sites
  public int numberOfOpenSites() {
    return open;
  }

  // We say the system percolates if there is a full site in the bottom row.
  // In other words, a system percolates if we fill all open sites
  // connected to the top row and that process fills some open site on the bottom row.
  // does the system percolate?
  public boolean percolates() {
    return uf.connected(top, bot);
  }

  // test client (optional)
  //UncommentedEmptyMethodBody
  public static void main(String[] args) {
    System.out.println("lame pmd warning eats a point!");
  }
}
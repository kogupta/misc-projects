import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
  private final double mean;
  private final double stdDev;
  private final double confHi;
  private final double confLo;

  // perform trials independent experiments on an n-by-n grid
  public PercolationStats(int n, int trials) {
    if (n <= 0) throw new IllegalArgumentException();
    if (trials <= 0) throw new IllegalArgumentException();

    double[] thresholds = new double[trials];
    for (int i = 0; i < trials; i++)
      thresholds[i] = experiment(n);


    mean = StdStats.mean(thresholds);
    stdDev = StdStats.stddev(thresholds);

    confLo = mean - 1.96 * stdDev / Math.sqrt(trials);
    confHi = mean + 1.96 * stdDev / Math.sqrt(trials);
  }

  private static double experiment(int n) {
    Percolation percolation = new Percolation(n);
    while (!percolation.percolates()) {
      int row = StdRandom.uniform(1, n + 1);
      int col = StdRandom.uniform(1, n + 1);
      if (!percolation.isOpen(row, col)) {
        percolation.open(row, col);
      }
    }

    return (double) percolation.numberOfOpenSites() / (n * n);
  }

  // sample mean of percolation threshold
  public double mean() {
    return mean;
  }

  // sample standard deviation of percolation threshold
  public double stddev() {
    return stdDev;
  }

  // low  endpoint of 95% confidence interval
  public double confidenceLo() {
    return confLo;
  }

  // high endpoint of 95% confidence interval
  public double confidenceHi() {
    return confHi;
  }

  @Override
  public String toString() {
    return String.format("mean                   = %,5f%n" +
            "stddev                 = %,5f%n" +
            "95 confidence interval = [%,5f, %,5f]",
        mean, stdDev, confLo, confHi);
  }

  // test client (described below)
  public static void main(String[] args) {
    for (int i = 1; i <= 10; i++) {
      System.out.println("    iteration " + i);
      System.out.println("-------------------");
      PercolationStats stats = new PercolationStats(200, 100);
      System.out.println(stats.toString());
      System.out.println();
    }
  }
}
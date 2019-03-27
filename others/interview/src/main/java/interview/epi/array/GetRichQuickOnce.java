package interview.epi.array;

public final class GetRichQuickOnce {
  public static void main(String[] args) {
    assertionStatus();
    int[] prices = new int[]{310, 315, 275, 295, 260, 270, 290, 230, 255, 250};

    int minPrice = Integer.MAX_VALUE;
    int maxProfit = Integer.MIN_VALUE;

    for (int price : prices) {
      minPrice = Math.min(minPrice, price);
      int currProfit = price - minPrice;
      maxProfit = Math.max(maxProfit, currProfit);
    }

    System.out.println("Max profit: " + maxProfit);
  }

  private static void assertionStatus() {
    String status = GetRichQuickOnce.class.desiredAssertionStatus() ? "enabled" : "disabled";
    System.out.println("Assertion: " + status);
    System.out.println("====================");
  }
}

package interview.epi.sorting;

import java.util.Arrays;

import static interview.epi.array.SortFunctions.assertionStatus;

// given: salaries of individuals, and target payroll
// find: the salary cap such that total payroll DOES NOT exceed target
//
// sort salaries, cap[c] is somewhere between k and (k+1)th salary
// total = sum(A[i], 0 <= i <= k) + (n - k) * c
// solving for c,
// (total - sum(A[i], 0 <= i <= k))/(n - k)
public final class SalaryCap {
  public static void main(String[] args) {
    assertionStatus();

    int[] salaries = {20, 30, 40, 90, 100};

    int[] targets = {210, 280, 281, 28};
    double[] es = {60, 100, -1, 5.6};

    for (int i = 0; i < targets.length; i++) {
      int target = targets[i];
      double expected = es[i];

      double cap = findSalaryCap(salaries, target);
      assert cap == expected :
          "Expected: " + expected + ", got: " + cap;
    }
  }

  private static double findSalaryCap(int[] salaries, int targetPayroll) {
    Arrays.sort(salaries);
    int sum = 0;
    for (int i = 0; i < salaries.length; i++) {
      int salary = salaries[i];
      int rest = salary * (salaries.length - i);
      if (sum + rest >= targetPayroll) {
        return (double) (targetPayroll - sum) / (salaries.length - i);
      } else
        sum += salary;
    }

    assert sum < targetPayroll;

    return -1;
  }
}

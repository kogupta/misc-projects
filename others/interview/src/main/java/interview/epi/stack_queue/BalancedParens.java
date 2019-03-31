package interview.epi.stack_queue;

import java.util.ArrayDeque;
import java.util.Deque;

import static java.lang.System.out;

public final class BalancedParens {
  public static void main(String[] args) {
    _assertionStatus();

    assert check("()");
    assert check("()[]");
    assert !check(")([]");
    assert !check("()[])(][}{");
  }

  private static boolean check(String brackets) {
    Deque<Character> opened = new ArrayDeque<>();
    for (char c : brackets.toCharArray()) {
      if (isOpen(c)) { opened.push(c); }
      else {
        if (opened.isEmpty() || !match(opened.peek(), c)) {
          return false;
        } else {
          opened.pop();
        }
      }
    }

    return opened.isEmpty();
  }

  private static boolean isOpen(char c) {
    return c == '(' || c == '[' || c == '{';
  }

  private static boolean match(char inStack, char c) {
    switch (inStack) {
      case '(': return c == ')';
      case '[': return c == ']';
      case '{': return c == '}';
      default:
        return false;
    }
  }

  //<editor-fold desc="assertion status">
  private static void _assertionStatus() {
    String status = BalancedParens.class.desiredAssertionStatus() ? "enabled" : "disabled";
    out.println("Assertion: " + status);
    out.println("====================");
  }
  //</editor-fold>
}

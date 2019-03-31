package interview.epi.stack_queue;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.function.BiFunction;

import static interview.epi.stack_queue.RPNEvaluator.Operator.START_PAREN;
import static interview.epi.stack_queue.RPNEvaluator.Operator.toChar;
import static java.lang.System.out;
import static java.util.stream.Collectors.joining;

public final class RPNEvaluator {
  public static void main(String[] args) {
    _assertionStatus();

    test("3 4 + 2 * 1 +", RPNEvaluator::postfixEval, 15);
    test("15 7 1 1 + - / 3 * 2 1 1 + + -", RPNEvaluator::postfixEval, 5);
    test("2 3 * 5 4 * + 9 -", RPNEvaluator::postfixEval, 17);

    test("- + * 2 3 * 5 4 9", RPNEvaluator::prefixEval, 17);

    assert "1 2 3 * + 4 5 * -".equals(toPostfixExpr("1 + 2 * 3 - 4 * 5", " "));
    assert "1 2 + 3 * 4 - 5 *".equals(toPostfixExpr("( ( 1 + 2 ) * 3 - 4 ) * 5", " "));
    assert "1 2 3 + *".equals(toPostfixExpr("1 * ( 2 + 3 )", " "));

    test("1 + 2 * 3 - 4 * 5", -13);
    test("( ( 1 + 2 ) * 3 - 4 ) * 5", 25);
    test("1 * ( 2 + 3 )", 5);
  }

  private static void test(String infixExpr, int expected) {
    System.out.println("infix: " + infixExpr);
    String postFixExpr = toPostfixExpr(infixExpr, " ");
    System.out.println("postfix: " + postFixExpr);
    System.out.println();

    int obtained = postfixEval(postFixExpr, " ");
    assert obtained == expected : "Expected " + expected + ", got: " + obtained;
  }

  private static void test(String expression, BiFunction<String, String, Integer> evaluator, int expected) {
    int obtained = evaluator.apply(expression, " ");
    assert obtained == expected: "Expected " + expected + ", got: " + obtained;
  }

  @SuppressWarnings("SameParameterValue")
  private static int postfixEval(String expression, String delimiter) {
    String[] tokens = expression.split(delimiter);

    Deque<Integer> stack = new ArrayDeque<>();
    for (String token : tokens) {
      Integer maybeInt = tryParse(token);
      if (maybeInt != null) {
        stack.push(maybeInt);
      } else {
        Operator operator = Operator.tryParse(token);
        if (operator == null)
          throw new IllegalArgumentException("malformed expr; invalid token: " + token);

        // NOTE: order of extraction
        Integer b = stack.pop();
        Integer a = stack.pop();
        stack.push(Operator.apply(operator, a, b));
      }
    }
    return stack.pop();
  }

  @SuppressWarnings("SameParameterValue")
  private static int prefixEval(String expression, String delimiter) {
    String[] tokens = expression.split(delimiter);

    Deque<Integer> stack = new ArrayDeque<>();
    for (int i = tokens.length - 1; i >= 0; i--) {
      String token = tokens[i];
      Integer maybeInt = tryParse(token);
      if (maybeInt != null) {
        stack.push(maybeInt);
      } else {
        Operator operator = Operator.tryParse(token);
        if (operator == null)
          throw new IllegalArgumentException("malformed expr; invalid token: " + token);

        // NOTE: order of extraction
        Integer a = stack.pop();
        Integer b = stack.pop();
        stack.push(Operator.apply(operator, a, b));
      }
    }
    return stack.pop();
  }

  private static Integer tryParse(String token) {
    try {
      return Integer.parseInt(token);
    } catch (NumberFormatException e) {
      return null;
    }
  }

  enum Operator {
    Multiply,
    Divide,
    Add,
    Subtract,
    START_PAREN,
    END_PAREN,
    ;

    public static int apply(Operator op, int a, int b) {
      switch (op) {
        case Add:
          return a + b;
        case Subtract:
          return a - b;
        case Multiply:
          return a * b;
        case Divide:
          return a / b;
      }

      throw new IllegalArgumentException("Invalid binary operator: " + op);
    }

    public static Operator tryParse(String token) {
      assert token.trim().length() == 1;
      char c = token.trim().charAt(0);
      switch (c) {
        case '+': return Add;
        case '-': return Subtract;
        case '*': return Multiply;
        case '/': return Divide;
        case '(': return START_PAREN;
        case ')': return END_PAREN;
        default: return null;
      }
    }

    public static String toChar(Operator op) {
      switch (op) {
        case Multiply:
        return "*";
        case Divide:
        return "/";
        case Add:
        return "+";
        case Subtract:
        return "-";
        case START_PAREN:
          return "(";
        case END_PAREN:
          return ")";
      }

      throw new IllegalArgumentException("unknown operator: op");
    }
  }

  @SuppressWarnings("SameParameterValue")
  private static String toPostfixExpr(String infixExpr, String delimiter) {
    List<String> xs = new ArrayList<>();
    Deque<Operator> operators = new ArrayDeque<>();

    for (String token : infixExpr.split(delimiter)) {
      Integer maybeInt = tryParse(token);
      if (maybeInt != null) {
        xs.add(token);
      } else {
        Operator op = Operator.tryParse(token);
        if (op == null)
          throw new IllegalArgumentException("malformed infix expression; invalid token: " + token);

        if (operators.isEmpty()) operators.push(op);
        else {
          switch (op) {
            case START_PAREN:
              operators.push(START_PAREN);
              break;

            case END_PAREN:
              while (operators.peek() != START_PAREN) {
                Operator operator = operators.pop();
                xs.add(toChar(operator));
              }
              operators.pop(); // START_PAREN
              break;

            default:
              // ordinal and enum definition order
              // gets rid of star paren predicate
              while (!operators.isEmpty() && operators.peek().ordinal() < op.ordinal()) {
                Operator higherPrecOp = operators.pop();
                xs.add(toChar(higherPrecOp));
              }
              operators.push(op);
          }
        }
      }
    }

    for (Operator operator : operators) {
      xs.add(toChar(operator));
    }

    return xs.stream().collect(joining(" "));
  }

  //<editor-fold desc="assertion status">
  private static void _assertionStatus() {
    String status = RPNEvaluator.class.desiredAssertionStatus() ? "enabled" : "disabled";
    out.println("Assertion: " + status);
    out.println("====================");
  }
  //</editor-fold>
}

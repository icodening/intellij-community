// "Replace with 'null'" "true"
import java.util.stream.*;

class Test {
  public static void method() {
    Object obj = null;
    Stream<Object> stream = Stream.of(<caret>obj);
  }
}
// "Adapt using 'Paths.get()'" "true"
import java.nio.file.*;

class Test {

  Path m() {
    return "/<caret>etc/passwd";
  }

}
// "Suppress unused warning if annotated by 'org.jetbrains.annotations.NonNls'" "false"
// ACTION: Create test
// ACTION: Do not show return expression hints
// ACTION: Safe delete 'foo'
import org.jetbrains.annotations.NonNls

@NonNls
fun <caret>foo() {

}
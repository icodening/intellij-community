// "Replace 'if' expression with safe access expression" "false"
// ACTION: Add non-null asserted (!!) call
// ACTION: Do not show return expression hints
// ACTION: Introduce local variable
// DISABLE-ERRORS
class Test {
    var x: String? = ""

    fun test() {
        if (x != null) {
            bar()
            <caret>x.length
        }
    }

    fun bar() {}
}
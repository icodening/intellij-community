// "Add non-null asserted (!!) call" "false"
// ACTION: Do not show return expression hints
// ACTION: Replace with a 'forEach' function call
// ACTION: Surround with null check
// ERROR: Not nullable value required to call an 'iterator()' method on for-loop range

class Some {
    fun iterator(): Iterator<Int> = null!!
}

fun foo() {
    val test: Some? = Some()
    for (i in <caret>test) { }
}

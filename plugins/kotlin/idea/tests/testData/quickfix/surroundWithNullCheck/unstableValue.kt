// "Surround with null check" "false"
// ACTION: Add non-null asserted (!!) call
// ACTION: Convert to run
// ACTION: Convert to with
// ACTION: Do not show return expression hints
// ACTION: Introduce local variable
// ACTION: Replace with safe (?.) call
// ERROR: Only safe (?.) or non-null asserted (!!.) calls are allowed on a nullable receiver of type Int?

class My(var x: Int?) {

    fun foo() {
        x<caret>.hashCode()
    }
}
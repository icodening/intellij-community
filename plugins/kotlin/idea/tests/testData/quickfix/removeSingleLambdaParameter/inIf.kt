// "Remove single lambda parameter declaration" "false"
// ACTION: Add braces to 'if' statement
// ACTION: Add braces to all 'if' statements
// ACTION: Convert to anonymous function
// ACTION: Convert to multi-line lambda
// ACTION: Do not show implicit receiver and parameter hints
// ACTION: Enable a trailing comma by default in the formatter
// ACTION: Remove explicit lambda parameter types (may break code)
// ACTION: Rename to _
fun test(i: Int) {
    val p: (String) -> Boolean =
        if (i == 1) { <caret>s: String -> true } else { s: String -> false }
}
// "Replace with assignment (original is empty)" "false"
// TOOL: org.jetbrains.kotlin.idea.inspections.SuspiciousCollectionReassignmentInspection
// ACTION: Change type to mutable
// ACTION: Do not show return expression hints
// ACTION: Replace overloaded operator with function call
// ACTION: Replace with filter
// ACTION: Replace with ordinary assignment
// WITH_STDLIB
fun test(otherList: List<Int>) {
    var list = emptyList<Int>()
    foo()
    bar()
    list <caret>-= otherList
}

fun foo() {}
fun bar() {}
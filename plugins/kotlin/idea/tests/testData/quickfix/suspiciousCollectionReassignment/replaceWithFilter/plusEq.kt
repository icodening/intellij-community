// "Replace with filter" "false"
// TOOL: org.jetbrains.kotlin.idea.inspections.SuspiciousCollectionReassignmentInspection
// ACTION: Change type to mutable
// ACTION: Do not show return expression hints
// ACTION: Join with initializer
// ACTION: Replace overloaded operator with function call
// ACTION: Replace with ordinary assignment
// WITH_STDLIB
fun test() {
    var list = listOf(1, 2, 3)
    list +=<caret> listOf (4)
}
// "Join with initializer" "false"
// TOOL: org.jetbrains.kotlin.idea.inspections.SuspiciousCollectionReassignmentInspection
// ACTION: Do not show return expression hints
// ACTION: Replace overloaded operator with function call
// ACTION: Replace with ordinary assignment
// WITH_STDLIB
class Test {
    var list = createList()

    fun test(otherList: List<Int>) {
        // comment
        list <caret>+= otherList
    }

    fun createList(): List<Int> = listOf(1, 2, 3)
}
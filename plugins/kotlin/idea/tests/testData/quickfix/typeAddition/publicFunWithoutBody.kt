// "Specify return type explicitly" "false"
// ERROR: Function 'foo' without a body must be abstract
// ACTION: Add function body
// ACTION: Convert member to extension
// ACTION: Do not show return expression hints
// ACTION: Make 'foo' 'abstract'

package a

class A() {
    public fun <caret>foo()
}
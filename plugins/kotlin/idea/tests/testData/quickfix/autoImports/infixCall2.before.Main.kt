// "Import" "false"
// ERROR: Unresolved reference: foo
// ACTION: Create extension function 'H.foo'
// ACTION: Create member function 'H.foo'
// ACTION: Do not show return expression hints
// ACTION: Replace infix call with ordinary call

package h

interface H

fun f(h: H) {
    h <caret>foo h
}
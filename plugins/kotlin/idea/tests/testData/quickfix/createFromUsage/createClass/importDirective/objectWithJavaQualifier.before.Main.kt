// "Create object 'A'" "false"
// ERROR: Unresolved reference: A
// ACTION: Create annotation 'A'
// ACTION: Create class 'A'
// ACTION: Create enum 'A'
// ACTION: Create interface 'A'
// ACTION: Do not show return expression hints
// ACTION: Rename reference
import J.<caret>A

class X {

}
// "Create class 'Foo'" "false"
// ACTION: Create extension function 'Int.Foo'
// ACTION: Do not show return expression hints
// ACTION: Rename reference
// ERROR: Unresolved reference: Foo
// WITH_STDLIB

fun test() {
    val a = 2.<caret>Foo(1)
}
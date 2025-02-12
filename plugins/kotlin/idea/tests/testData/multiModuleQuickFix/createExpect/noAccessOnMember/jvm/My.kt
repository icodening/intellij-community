// "Create expected function in common module testModule_Common" "false"
// ACTION: Apply all 'Remove modifier' fixes in file
// ACTION: Convert member to extension
// ACTION: Convert to block body
// ACTION: Create expected class in common module testModule_Common
// ACTION: Do not show return expression hints
// ACTION: Move to companion object
// ACTION: Remove 'actual' modifier
// ACTION: Remove explicit type specification
// DISABLE-ERRORS

actual class My {
    actual fun <caret>foo(param: String): Int = 42

    actual fun String.bar(y: Double): Boolean = true

    actual fun baz() {}

    actual constructor(flag: Boolean) {}

    actual val isGood: Boolean
        get() = true
    actual var status: Int
        get() = 0
        set(value) {}

}
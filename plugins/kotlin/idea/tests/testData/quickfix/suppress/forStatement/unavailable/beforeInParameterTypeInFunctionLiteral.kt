// "class com.intellij.codeInspection.SuppressIntentionAction" "false"
// ACTION: Suppress 'REDUNDANT_NULLABLE' for fun foo
// ACTION: Suppress 'REDUNDANT_NULLABLE' for parameter x

fun foo() {
    any {
        x: String?<caret>? ->
    }
}

fun any(a: Any?) {}
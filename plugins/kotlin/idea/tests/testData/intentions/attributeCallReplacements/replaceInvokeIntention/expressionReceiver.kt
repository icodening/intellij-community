fun test() {
    class Test(val v: Int)

    fun Test.invoke(): Unit = Unit

    Test(1).i<caret>nvoke()
}

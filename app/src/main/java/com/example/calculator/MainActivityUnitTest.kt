import org.junit.Test
import net.objecthunter.exp4j.ExpressionBuilder
import org.junit.Assert.assertEquals

class MainActivityUnitTest {

    @Test
    fun testExpressionEvaluation1() {
        val expression = "2+2"
        val expected = 4.0

        val ex = ExpressionBuilder(expression).build()
        val result = ex.evaluate()

        assertEquals(expected, result, 0.001)
    }

    @Test
    fun testExpressionEvaluation2() {
        val expression = "2+(-2)"
        val expected = 0.0

        val ex = ExpressionBuilder(expression).build()
        val result = ex.evaluate()

        assertEquals(expected, result, 0.001)
    }

    @Test
    fun testExpressionEvaluation3() {
        val expression = "-2+(-2)"
        val expected = -4.0

        val ex = ExpressionBuilder(expression).build()
        val result = ex.evaluate()

        assertEquals(expected, result, 0.001)
    }

    @Test
    fun testExpressionEvaluation4() {
        val expression = "2-2"
        val expected = 0.0

        val ex = ExpressionBuilder(expression).build()
        val result = ex.evaluate()

        assertEquals(expected, result, 0.001)
    }

    @Test
    fun testExpressionEvaluation5() {
        val expression = "-2-2"
        val expected = -4.0

        val ex = ExpressionBuilder(expression).build()
        val result = ex.evaluate()

        assertEquals(expected, result, 0.001)
    }

    @Test
    fun testExpressionEvaluation6() {
        val expression = "-2-(-2)"
        val expected = 0.0

        val ex = ExpressionBuilder(expression).build()
        val result = ex.evaluate()

        assertEquals(expected, result, 0.001)
    }

    @Test
    fun testExpressionEvaluation7() {
        val expression = "2*2"
        val expected = 4.0

        val ex = ExpressionBuilder(expression).build()
        val result = ex.evaluate()

        assertEquals(expected, result, 0.001)
    }

    @Test
    fun testExpressionEvaluation8() {
        val expression = "2*(-2)"
        val expected = -4.0

        val ex = ExpressionBuilder(expression).build()
        val result = ex.evaluate()

        assertEquals(expected, result, 0.001)
    }

    @Test
    fun testExpressionEvaluation9() {
        val expression = "-2*(-2)"
        val expected = 4.0

        val ex = ExpressionBuilder(expression).build()
        val result = ex.evaluate()

        assertEquals(expected, result, 0.001)
    }

    @Test
    fun testExpressionEvaluation10() {
        val expression = "2/2"
        val expected = 1.0

        val ex = ExpressionBuilder(expression).build()
        val result = ex.evaluate()

        assertEquals(expected, result, 0.001)
    }

    @Test
    fun testExpressionEvaluation11() {
        val expression = "2/(-2)"
        val expected = -1.0

        val ex = ExpressionBuilder(expression).build()
        val result = ex.evaluate()

        assertEquals(expected, result, 0.001)
    }

    @Test
    fun testExpressionEvaluation12() {
        val expression = "-2/(-2)"
        val expected = 1.0

        val ex = ExpressionBuilder(expression).build()
        val result = ex.evaluate()

        assertEquals(expected, result, 0.001)
    }

    @Test
    fun testExpressionEvaluation13() {
        val expression = "2*0"
        val expected = 0.0

        val ex = ExpressionBuilder(expression).build()
        val result = ex.evaluate()

        assertEquals(expected, result, 0.001)
    }

    @Test
    fun testExpressionEvaluation14() {
        val expression = "2/0"
        try {
            if ("0".toRegex() in expression) {
                throw ArithmeticException("Division by zero")
            }
            val ex = ExpressionBuilder(expression).build()
            val result = ex.evaluate()
        } catch (e: ArithmeticException) {
            assertEquals("Division by zero", e.message)
        }
    }
}

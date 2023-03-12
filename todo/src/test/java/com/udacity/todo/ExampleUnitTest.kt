package com.udacity.todo

import androidx.core.math.MathUtils
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        val result = MathUtils.multiplyExact(3,2)
        assertEquals(6,result)
    }
}
package com.example.kinogramm.view.exit

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class ExitViewModelTest {
    private lateinit var SUT: ExitViewModel

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        SUT = ExitViewModel()
    }

    @Test
    fun testExitLiveDataIsNullByDefault() {
        Assert.assertNull(SUT.exit.value)
    }

    @Test
    fun testExitAppChangesExitLiveDataValue() {
        SUT.exitApp()
        Assert.assertTrue(SUT.exit.value == Unit)
    }
}
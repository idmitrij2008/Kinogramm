package com.example.kinogramm.view

import org.junit.Assert
import org.junit.Before
import org.junit.Test

private const val DEFAULT_LAST_CLICKED_FILM_ID = 0
private const val CHANGED_LAST_CLICKED_FILM_ID = 1

class MainViewModelTest {
    private lateinit var SUT: MainViewModel

    @Before
    fun setUp() {
        SUT = MainViewModel()
    }

    @Test
    fun testLastClickedFilmIdIsZeroByDefault() {
        Assert.assertTrue(SUT.lastClickedFilmId == DEFAULT_LAST_CLICKED_FILM_ID)
    }

    @Test
    fun testLastClickedFilmIdCanBeChanged() {
        SUT.lastClickedFilmId = CHANGED_LAST_CLICKED_FILM_ID

        Assert.assertTrue(SUT.lastClickedFilmId == CHANGED_LAST_CLICKED_FILM_ID)
    }
}
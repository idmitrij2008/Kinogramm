package com.example.kinogramm.domain.usecases

import com.example.kinogramm.domain.IFilmsRepository
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.anyInt
import org.mockito.Mockito.times

private const val FILM_REMOTE_ID = 0

class GetFilmUseCaseTest {
    private lateinit var SUT: GetFilmUseCase
    private lateinit var mockedRepo: IFilmsRepository

    @Before
    fun setUp() {
        mockedRepo = Mockito.mock(IFilmsRepository::class.java)
        SUT = GetFilmUseCase(mockedRepo)
    }

    @Test
    fun testGetFilmTriggersRepoGetFilm() {
        SUT.getFilm(FILM_REMOTE_ID)
        Mockito.verify(mockedRepo, times(1)).getFilm(anyInt())
    }

    @Test
    fun testGetFilmTriggersRepoGetFilmWithSameFilmId() {
        SUT.getFilm(FILM_REMOTE_ID)
        Mockito.verify(mockedRepo, times(1)).getFilm(FILM_REMOTE_ID)
    }
}
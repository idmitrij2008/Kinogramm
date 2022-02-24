package com.example.kinogramm.view.favourites

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.kinogramm.domain.Film
import com.example.kinogramm.domain.usecases.GetFilmsUseCase
import com.example.kinogramm.domain.usecases.GetLikedFilmsUseCase
import com.example.kinogramm.domain.usecases.LikeFilmUseCase
import io.reactivex.rxjava3.android.plugins.RxAndroidPlugins
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.runBlocking
import org.junit.*
import org.mockito.Mockito

class FavouriteFilmsViewModelTest {
    private lateinit var SUT: FavouriteFilmsViewModel
    private lateinit var mockedGetLikedFilmsUseCase: GetLikedFilmsUseCase
    private lateinit var mockedGetFilmsUseCase: GetFilmsUseCase
    private lateinit var mockedLikeFilmUseCase: LikeFilmUseCase

    private val likedFilms = listOf(
        Film(1, 1, "film1", "", ""),
        Film(3, 3, "film3", "", ""),
        Film(5, 5, "film5", "", ""),
    )

    private val nonLikedFilms = listOf(
        Film(2, 2, "film2", "", ""),
        Film(4, 4, "film4", "", ""),
    )

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline() }

        mockedGetLikedFilmsUseCase = Mockito.mock(GetLikedFilmsUseCase::class.java)
        mockedGetFilmsUseCase = Mockito.mock(GetFilmsUseCase::class.java)
        mockedLikeFilmUseCase = Mockito.mock(LikeFilmUseCase::class.java)

        Mockito.`when`(mockedGetLikedFilmsUseCase.getLikedFilms())
            .thenReturn(Observable.just(likedFilms.map { it.id }))

        Mockito.`when`(mockedGetFilmsUseCase.getFilmsList())
            .thenReturn(Single.just(likedFilms + nonLikedFilms))

        SUT = FavouriteFilmsViewModel(
            getLikedFilmsUseCase = mockedGetLikedFilmsUseCase,
            getFilmsUseCase = mockedGetFilmsUseCase,
            likeFilmUseCase = mockedLikeFilmUseCase
        )
    }

    @After
    fun tearDown() {
        RxAndroidPlugins.reset()
    }

    @Test
    fun testFavFilmsLiveDataContainsAllFavouriteFilms() {
        runBlocking {
            SUT.favouriteFilms.value?.let {
                Assert.assertTrue(it.containsAll(likedFilms))
            } ?: Assert.assertTrue("No films in favouriteFilmsLD", false)
        }
    }

    @Test
    fun testFavFilmsLiveDataContainsOnlyFavouriteFilms() {
        runBlocking {
            SUT.favouriteFilms.value?.let {
                Assert.assertFalse(it.contains(nonLikedFilms[0]) || it.contains(nonLikedFilms[1]))
            } ?: Assert.assertTrue("No films in favouriteFilmsLD", false)
        }
    }
}
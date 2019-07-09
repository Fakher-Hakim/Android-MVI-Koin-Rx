package com.fakher.mvi.data

import com.fakher.mvi.data.models.Album
import com.fakher.mvi.data.models.AlbumPhoto
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.reactivex.Observable
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import io.reactivex.schedulers.TestScheduler
import org.junit.Before
import org.junit.Test

class DataProviderTest {

    @MockK
    lateinit var apiService: ApiService

    private val testSchedulers = TestScheduler()

    private val photo = AlbumPhoto(1, 1, "title", "", "")
    private val photos = listOf(photo, photo, photo)
    private val album = Album(1, "title", photos.toMutableList())
    private val albums = listOf(album, album, album)

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true)

        RxJavaPlugins.setIoSchedulerHandler { Schedulers.trampoline() }
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline() }
        RxJavaPlugins.setComputationSchedulerHandler { testSchedulers }
    }

    @Test
    fun should_return_albums_force_update() {
        //Given
        val dataProvider = DataProvider(apiService)

        every { apiService.loadPhotos() } returns Observable.just(photos)
        every { apiService.loadAlbums() } returns Observable.just(albums)

        //When
        dataProvider.loadAlbums(true)
            .test().assertResult(albums)
    }

    @Test
    fun should_return_albums_no_update() {
        //Given
        val dataProvider = DataProvider(apiService)

        every { apiService.loadPhotos() } returns Observable.just(photos)
        every { apiService.loadAlbums() } returns Observable.just(albums)

        //When
        dataProvider.loadAlbums(false)
            .test().assertResult(albums)
    }
}
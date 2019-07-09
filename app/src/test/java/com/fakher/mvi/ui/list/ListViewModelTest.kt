package com.fakher.mvi.ui.list

import com.fakher.mvi.data.DataProvider
import com.fakher.mvi.data.models.Album
import com.fakher.mvi.ui.list.ListContext.Error.AlbumError
import com.fakher.mvi.ui.list.ListContext.ListAction.LoadAlbums
import com.fakher.mvi.ui.list.ListContext.ListState
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

class ListViewModelTest {

    @MockK
    lateinit var dataProvider: DataProvider

    private val testSchedulers = TestScheduler()

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true)

        RxJavaPlugins.setIoSchedulerHandler { Schedulers.trampoline() }
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline() }
        RxJavaPlugins.setComputationSchedulerHandler { testSchedulers }
    }

    @Test
    fun should_load_albums() {

        //Given
        val album = Album(1, "title", mutableListOf())
        val albums = listOf(album, album)
        val initState = ListState()
        val updatedState = initState.copy(albums = albums)
        val listViewModel = ListViewModel(dataProvider)

        every { dataProvider.loadAlbums(any()) } returns Observable.just(albums)

        //When
        val testObserver = listViewModel.state.take(2).test()
        listViewModel.handleAction(LoadAlbums)

        //Then
        testObserver.assertResult(initState, updatedState)
    }

    @Test
    fun should_not_load_albums_error() {

        //Given
        val initState = ListState()
        val errorState = initState.copy(error = AlbumError)
        val listViewModel = ListViewModel(dataProvider)

        every { dataProvider.loadAlbums(any()) } returns Observable.error(Exception())

        //When
        val testObserver = listViewModel.state.take(2).test()
        listViewModel.handleAction(LoadAlbums)

        //Then
        testObserver.assertResult(initState, errorState)
    }
}
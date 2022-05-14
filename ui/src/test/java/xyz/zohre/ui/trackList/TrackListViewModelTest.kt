package xyz.zohre.ui.trackList

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import kotlinx.coroutines.delay
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import xyz.zohre.ui.LiveDataTestUtil
import xyz.zohre.ui.MainCoroutineRule
import xyz.zohre.ui.TestData
import xyz.zohre.ui.runBlockingTest

class TrackListViewModelTest{

    @get:Rule
    var coroutineRule = MainCoroutineRule()

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()


    private lateinit var viewModel: TrackListViewModel

    private lateinit var repository: FakeTrackListRepository

    @Before
    fun setup() {
        repository = FakeTrackListRepository(coroutineRule.testDispatcher)
        viewModel = TrackListViewModel(repository)
    }

    @Test
    fun checkRemoteWorkProperly() = coroutineRule.runBlockingTest {
        // when
        val firstEmit = TestData.trackResponse
        repository.setEmits(firstEmit)

        // given
        val results = LiveDataTestUtil.getValuesLater(viewModel.tracks)
        viewModel.loadTracks()
        delay(510)

        // then
        assertEquals(results[0], firstEmit.data.sessions)
    }
}
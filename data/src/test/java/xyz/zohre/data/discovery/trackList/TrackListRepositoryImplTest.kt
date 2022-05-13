package xyz.zohre.data.discovery.trackList

import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import retrofit2.Response
import xyz.zohre.data.MainCoroutineRule
import xyz.zohre.data.NetworkTestData
import xyz.zohre.data.TestData
import xyz.zohre.data.discovery.RemoteDataSource
import xyz.zohre.data.model.TrackResponse
import xyz.zohre.domain.entities.ApiResult

class TrackListRepositoryImplTest {
    private lateinit var movieListRepositoryImpl: TrackListRepositoryImpl

    @MockK
    lateinit var remoteDataSource: RemoteDataSource

    @MockK
    lateinit var remoteResult: Response<TrackResponse>

    @get:Rule
    var coroutineRule = MainCoroutineRule()

    @Before
    fun setup() {
        MockKAnnotations.init(this, relaxUnitFun = true, relaxed = true)
        movieListRepositoryImpl = TrackListRepositoryImpl(
            remoteDataSource,
            coroutineRule.testDispatcher,
            coroutineRule.testDispatcher
        )
    }

    @Test
    fun checkRepositoryEmitCorrectListSize() = runBlockingTest {

        //When
        coEvery { remoteDataSource.getTracks() } returns remoteResult
        every { remoteResult.isSuccessful } returns true
        every { remoteResult.body() } returns NetworkTestData.remotePaginatedMovie()
        val parameterName = Any()


        //Given
        val results: List<ApiResult<TrackResponse>> = movieListRepositoryImpl.execute(parameters = parameterName).toList()


        //Then
        assertEquals(1, results.size)
    }

    @Test
    fun checkRepositoryEmitCorrectData() = runBlockingTest {

        //When
        val expectedRemote = TestData.trackResponse
        val parameterName = Any()

        coEvery { remoteDataSource.getTracks() } returns remoteResult
        every { remoteResult.isSuccessful } returns true
        every { remoteResult.body() } returns NetworkTestData.remotePaginatedMovie()

        //Given
        val results: List<ApiResult<TrackResponse>> = movieListRepositoryImpl.execute(parameters = parameterName).toList()

        //Then
        assertEquals((results[0] as ApiResult.Success).data.data.sessions, expectedRemote.data.sessions)
        assertEquals(expectedRemote.data, (results[0] as ApiResult.Success).data.data)
    }
}
package com.jakelaurie.squadspook

import com.jakelaurie.squadspook.data.repository.ServerRepository
import com.jakelaurie.squadspook.model.Server
import com.jakelaurie.squadspook.ui.viewmodel.serverlist.ServerListViewModel
import org.junit.Before
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class ServerListActivityViewModelTest {
    @Mock
    private lateinit var mockRepository: ServerRepository

    private lateinit var serverListViewModel: ServerListViewModel

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        serverListViewModel = ServerListViewModel(mockRepository, schedulerProvider)
    }

    @Test
    fun showDataFromApi() {
        Mockito.`when`(mockRepository.getDataFromApi())
                .thenReturn(Single.just(IpAddress("20.0.0.0")))

        val testObserver = TestObserver<IpAddress>()

        mainActivityViewModel.showDataFromApi()
                .subscribe(testObserver)

        testObserver.assertNoErrors()
        testObserver.assertValue { ipAddress -> ipAddress.ip.equals("20.0.0.0") }
    }
}
import com.appsinvo.bigadstv.data.remote.networkUtils.NetworkResult

interface Paginator<Key, Item> {
    suspend fun loadNextItems(pageNo: Key, isSearch: Boolean = false, isPaginating : Boolean = false, onRequest:  suspend () -> NetworkResult<Item>, )

}
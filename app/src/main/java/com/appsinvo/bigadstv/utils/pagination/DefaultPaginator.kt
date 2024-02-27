import android.annotation.SuppressLint
import android.util.Log
import com.appsinvo.bigadstv.data.remote.networkUtils.NetworkResult
import com.appsinvo.bigadstv.utils.Constants.PAGINATION_DELAY
import kotlinx.coroutines.delay

class DefaultPaginator<Key, Item>(
    private inline val onLoadUpdated: suspend (isLoading: Boolean, isSearch: Boolean) -> Unit,
    private inline val onError: suspend (message: String) -> Unit,
    private inline val onSuccess: suspend (item: Item, newKey: Key, isSearch: Boolean) -> Unit
) : Paginator<Key, Item> {

    private var isMakingRequest = false

    @SuppressLint("SuspiciousIndentation")
    override suspend fun loadNextItems(pageNo: Key, isSearch: Boolean, isPaginating: Boolean, onRequest: suspend () -> NetworkResult<Item>) {
        try {

            Log.d("Fvmfkvmf","loadNestItems")
            if (isMakingRequest) {
              //  return
            }
            isMakingRequest = true

            onLoadUpdated(true, isSearch)

            Log.d("fvkfvmkf","onRequest")
            val pageResult = onRequest()

                when (pageResult) {
                    is NetworkResult.Loading -> {
                        onLoadUpdated(true, isSearch)
                    }
                    is NetworkResult.Error -> {
                        Log.e("TAG", "loadNextItems: $pageResult", )
                        onLoadUpdated(false, isSearch)
                        onError(pageResult.error ?: "TRY_AGAIN")
                    }
                    is NetworkResult.Success -> {
                        if (isPaginating) {
                            delay(PAGINATION_DELAY)
                        }
                        isMakingRequest = false
                        onLoadUpdated(false, isSearch)

                        pageResult.data?.let {
                            onSuccess(it, pageNo, isSearch)
                        }
                    }
                }

        } catch (e: Exception) {
            Log.d("Fvmfkvmf","loadNestItems ${e.message}")
            isMakingRequest = false
        }
    }
}
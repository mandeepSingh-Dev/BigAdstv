package com.appsinvo.bigadstv.presentation.ui.fragments

import android.annotation.SuppressLint
import android.media.session.PlaybackState
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.media3.common.Player
import androidx.media3.datasource.HttpDataSource
import androidx.navigation.fragment.navArgs
import coil.load
import coil.transform.CircleCropTransformation
import coil.transform.Transformation
import com.appsinvo.bigadstv.R
import com.appsinvo.bigadstv.base.BaseFragment
import com.appsinvo.bigadstv.data.remote.model.ads.trackAds.requestBody.TrackAdsRequestBody
import com.appsinvo.bigadstv.data.remote.networkUtils.NetworkResult
import com.appsinvo.bigadstv.databinding.FragmentPlayerBinding
import com.appsinvo.bigadstv.databinding.PlayerControllerIdBinding
import com.appsinvo.bigadstv.presentation.ui.viewmodels.HomeViewmodel
import com.appsinvo.bigadstv.utils.MExoplayer
import com.appsinvo.bigadstv.utils.formatMillisToDateTime
import com.appsinvo.bigadstv.utils.formatSecondsToHMS
import com.appsinvo.bigadstv.utils.inVisible
import com.appsinvo.bigadstv.utils.showSnackbar
import com.appsinvo.bigadstv.utils.visible
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@SuppressLint("SetTextI18n")
@AndroidEntryPoint
class PlayerFragment : BaseFragment() {


    private var _binding : FragmentPlayerBinding? = null
    private val binding : FragmentPlayerBinding get() = _binding!!

    private var playerControllerIdBinding : PlayerControllerIdBinding? = null

    private val navArgs : PlayerFragmentArgs by navArgs()


    private val homeViewmodel : HomeViewmodel by viewModels()


        var username : TextView ? = null
        var location : TextView ? = null
        var userImageview : ImageView ? = null
        var name : TextView ? = null
        var dateTime: TextView ? = null
        var videoTimer: TextView ? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentPlayerBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

       // val adsData = navArgs.adsData

        lifecycleScope.launch {
            homeViewmodel.getCurrentWorldDateTime()
        }
        playerControllerIdBinding = PlayerControllerIdBinding.inflate(layoutInflater)

        username = view.findViewById(R.id.username)
        location = view.findViewById(R.id.location)
        userImageview = view.findViewById(R.id.userImageview)
        name = view.findViewById(R.id.name)
        dateTime= view.findViewById(R.id.dateTime)
        videoTimer= view.findViewById(R.id.videoTimer)


       // binding.playerView.useController = false
       // binding.playerView.addView(playerControllerIdBinding?.root)

    /*     binding.location.text
        binding.username.text = adsData.userId
        binding.name.text = adsData.title
        binding.dateTime.text = "${adsData.startDate} ${adsData.startTime}"
 */
        setUpExoplayer()

       /*  binding.videoView.setVideoPath(adsData.filePath)

        val mediaControllerr = MediaController(requireContext())
        mediaControllerr.setAnchorView(binding.videoView)
        mediaControllerr.setMediaPlayer(binding.videoView)



        binding.videoView.setOnPreparedListener {
            binding.progressBar.inVisible()
            binding.videoView.start()
        }
        binding.videoView.setOnErrorListener { mp, what, extra ->
            binding.progressBar.inVisible()
            return@setOnErrorListener true
        }
        binding.videoView.setOnCompletionListener {

        } */
    }

    @OptIn(DelicateCoroutinesApi::class)
    private val mExoplayer : MExoplayer by lazy {
        MExoplayer( onEventss = {player,events ->},
            onMediaItemTransitionn = {mediaItem, reason->
                val adsData = navArgs.adsDataList.find { it.filePath == mediaItem?.localConfiguration?.uri.toString()}

              name?.text = adsData?.title
              dateTime?.text = "${adsData?.startDate} ${adsData?.startTime}"
              username?.text = adsData?.userName ?: ""
              location?.text = adsData?.userLocation ?: ""
                userImageview?.load(adsData?.userImg/* "https://file-examples.com/storage/fedf16213165ce2d096e19a/2017/10/file_example_JPG_2500kB.jpg" */){
                    placeholder(R.drawable.user)
                    crossfade(1200)
                    transformations(CircleCropTransformation())
                    error(R.drawable.user)
                }



                mExoplayer.currentStartTime = System.currentTimeMillis().toLong()


                val trackAdsRequestBody = TrackAdsRequestBody(advertisementId = adsData?.advertisementId.toString(),endTime = null,id = null/* adsData?.userId */,startTime  = mExoplayer.currentStartTime.formatMillisToDateTime(), watchTime = mExoplayer.videoWatchedTime?.toInt())

              /*   GlobalScope.launch {
                    homeViewmodel.trackAds(trackAdsRequestBody)
                } */


            },
            onPlaybackStateChangedd = {playBackState, currentUri ->
                val adsData = navArgs.adsDataList.find { it.filePath == currentUri}
                when(playBackState){
                    Player.STATE_IDLE ->{
                        Log.d("flvjkfvnf",mExoplayer.videoWatchedTime.toString() + "  STATE_IDLE")
                        Log.d("fjkvnkfjvnfv","STATE_IDLE")

                        val trackAdsRequestBody = TrackAdsRequestBody(advertisementId = adsData?.advertisementId.toString(),endTime = System.currentTimeMillis().formatMillisToDateTime(),id = null/* adsData?.userId */,startTime  = mExoplayer.currentStartTime.formatMillisToDateTime(), watchTime = mExoplayer?.videoWatchedTime?.toInt())

                        GlobalScope.launch {
                            homeViewmodel.trackAds(trackAdsRequestBody)
                        }

                    }
                    Player.STATE_BUFFERING -> {
                        binding.progressBar.visible()
                    }
                    Player.STATE_ENDED -> {
                        binding.progressBar.inVisible()
                        Log.d("fjkvnkfjvnfv","STATE_ENDED")
                        Log.d("flvjkfvnf",mExoplayer.videoWatchedTime.toString() + "  STATE_ENDED")

                        val trackAdsRequestBody = TrackAdsRequestBody(advertisementId = adsData?.advertisementId.toString(),endTime = System.currentTimeMillis().formatMillisToDateTime(),id = null/* adsData?.userId */,startTime  = mExoplayer.currentStartTime.formatMillisToDateTime(), watchTime = mExoplayer?.videoWatchedTime?.toInt())

                        GlobalScope.launch {
                            homeViewmodel.trackAds(trackAdsRequestBody)
                        }


                        val isLastitem = mExoplayer.itemsList.last() == currentUri

                        Log.d("jfnvjfv",isLastitem.toString())
                        Log.d("fkbmknvf",mExoplayer.itemsList.last().toString())
                        Log.d("flvmkvf",currentUri.toString())
                        if(isLastitem) {
                            mExoplayer.repeatList()
                        }





                    }
                    Player.STATE_READY -> {
                        binding.progressBar.inVisible()

                        mExoplayer.startTimer()

                        name?.text = adsData?.title
                        dateTime?.text = "${adsData?.startDate} ${adsData?.startTime}"
                        username?.text = adsData?.userName ?: ""
                        location?.text = adsData?.userLocation ?: ""
                        userImageview?.load(adsData?.userImg/* "https://file-examples.com/storage/fedf16213165ce2d096e19a/2017/10/file_example_JPG_2500kB.jpg" */){
                            placeholder(R.drawable.user)
                            transformations(CircleCropTransformation())
                            crossfade(1200)
                            error(R.drawable.user)
                        }

                        val isPlaying = mExoplayer.getPlayer().isPlaying

                        Log.d("fjkvnkfjvnfv","STATE_READY")
                    }
                    PlaybackState.STATE_PLAYING -> {
                        Log.d("fjkvnkfjvnfv","STATE_PLAYING")
                    }
                    PlaybackState.STATE_STOPPED -> {
                        Log.d("flvjkfvnf",mExoplayer.videoWatchedTime.toString() + "  STATE_STOPPED")
                        Log.d("fjkvnkfjvnfv","STATE_STOPPED")
                    }
                    PlaybackState.STATE_PAUSED ->{
                        Log.d("flvjkfvnf",mExoplayer.videoWatchedTime.toString() + "  STATE_PAUSED")
                        Log.d("fjkvnkfjvnfv","STATE_PAUSED")
                    }
                }
            },
            onTracksChangedd = {},
            onIsPlayingChangedd = {isPlaying -> },
            onPlayerErrorr = {error ->
                val cause = error.cause
                if (cause is HttpDataSource.HttpDataSourceException) {
                    // An HTTP error occurred.
                    val httpError = cause
                    // It's possible to find out more about the error both by casting and by querying
                    // the cause.
                    if (httpError is HttpDataSource.InvalidResponseCodeException) {
                        // Cast to InvalidResponseCodeException and retrieve the response code, message
                        // and headers.
                    } else {
                        // Try calling httpError.getCause() to retrieve the underlying cause, although
                        // note that it may be null.
                    }
                }
            },
            onTimer = {
           videoTimer?.text =  it.toInt().formatSecondsToHMS()
            }
        )
    }

    private fun setUpExoplayer(){
        mExoplayer.initPlayer(requireContext())

        binding.playerView.player = mExoplayer.getPlayer()
        mExoplayer.addListener()

        val adsDataList = navArgs.adsDataList

        val links = adsDataList.map { it.filePath }

     //   mExoplayer.setMediaItems(mutableListOf("https://v3.cdnpk.net/videvo_files/video/free/2015-09/large_preview/Countdown1.mp4","https://app-dev-appsinvo.s3.amazonaws.com/BigAdsAdmin/2851f01409f.mp4")/* links.toMutableList() */)
        mExoplayer.setMediaItems(links.toMutableList())
        mExoplayer.startPlayer()
    }

    override fun onDestroy() {
        super.onDestroy()
        mExoplayer.releaseplayer()
    }


    private suspend fun observeTrackAdsApiResponse(){
        homeViewmodel.trackAdsResponse.collect{networkResult ->
            when(networkResult){
                is NetworkResult.Loading -> {
                    showLoading()
                }
                is NetworkResult.Success -> {
                    hideLoading()

                }
                is NetworkResult.Error -> {
                    hideLoading()
                    binding.root.showSnackbar(message = networkResult.error.toString())
                }
            }
        }
    }
}



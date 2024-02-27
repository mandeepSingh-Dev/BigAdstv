package com.appsinvo.bigadstv.presentation.ui.fragments

import android.annotation.SuppressLint
import android.app.ActivityManager
import android.content.Context.ACTIVITY_SERVICE
import android.media.session.PlaybackState
import android.os.Bundle
import android.provider.Settings.Global
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.datasource.HttpDataSource
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import coil.load
import coil.transform.CircleCropTransformation
import com.appsinvo.bigadstv.R
import com.appsinvo.bigadstv.base.BaseFragment
import com.appsinvo.bigadstv.data.remote.model.ads.trackAds.requestBody.TrackAdsRequestBody
import com.appsinvo.bigadstv.data.remote.networkUtils.NetworkResult
import com.appsinvo.bigadstv.databinding.FragmentPlayerBinding
import com.appsinvo.bigadstv.databinding.PlayerControllerIdBinding
import com.appsinvo.bigadstv.presentation.ui.viewmodels.HomeViewmodel
import com.appsinvo.bigadstv.presentation.ui.viewmodels.PlayerViewmodel
import com.appsinvo.bigadstv.utils.MExoplayer
import com.appsinvo.bigadstv.utils.formatMillisToDateTime
import com.appsinvo.bigadstv.utils.formatSecondsToHMS
import com.appsinvo.bigadstv.utils.inVisible
import com.appsinvo.bigadstv.utils.showSnackbar
import com.appsinvo.bigadstv.utils.visible
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

@SuppressLint("SetTextI18n", "SuspiciousIndentation")
@AndroidEntryPoint
class PlayerFragment : BaseFragment() {


    private var _binding : FragmentPlayerBinding? = null
    private val binding : FragmentPlayerBinding get() = _binding!!

    private var playerControllerIdBinding : PlayerControllerIdBinding? = null

    private val navArgs : PlayerFragmentArgs by navArgs()

    private val playerViewmodel : PlayerViewmodel by viewModels()


      private var controllerRootLayout : ConstraintLayout ? = null
      private var username : TextView ? = null
      private var location : TextView ? = null
      private var userImageview : ImageView ? = null
      private var name : TextView ? = null
      private var dateTime: TextView ? = null
      private var videoTimer: TextView ? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentPlayerBinding.inflate(layoutInflater)
        return binding.root
    }

    @SuppressLint("ClickableViewAccessibility")
    @androidx.annotation.OptIn(UnstableApi::class)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val trackAdsRequestBody = TrackAdsRequestBody(
            advertisementId = "156565",
            endTime = "System.currentTimeMillis().formatMillisToDateTime()",
            startTime  = "mExoplayer.currentStartTime.formatMillisToDateTime()",
            watchTime = 4
        )

        GlobalScope.launch {
            playerViewmodel.insertTrackAd(trackAdsRequestBody = trackAdsRequestBody)
        }

       // val adsData = navArgs.adsData

        lifecycleScope.launch {
            playerViewmodel.getCurrentWorldDateTime()
        }
        playerControllerIdBinding = PlayerControllerIdBinding.inflate(layoutInflater)

        controllerRootLayout = view.findViewById(R.id.controller_root_layout)
        username = view.findViewById(R.id.username)
        location = view.findViewById(R.id.location)
        userImageview = view.findViewById(R.id.userImageview)
        name = view.findViewById(R.id.name)
        dateTime= view.findViewById(R.id.dateTime)
        videoTimer= view.findViewById(R.id.videoTimer)

        binding.root.setOnClickListener {
            binding.playerView.controllerHideOnTouch =  true
            if(binding.playerView.isControllerFullyVisible) binding.playerView.hideController() else binding.playerView.showController()
        }
        setUpExoplayer()

        requireActivity().onBackPressedDispatcher.addCallback(onBackPressedCallback)
    }

    val onBackPressedCallback = object : OnBackPressedCallback(true){
        override fun handleOnBackPressed() {
            isBackPressed = true


            findNavController().popBackStack()
        }
    }

    var isBackPressed : Boolean = false

    @OptIn(DelicateCoroutinesApi::class)
    private val mExoplayer : MExoplayer by lazy {
        MExoplayer( onEventss = {player,events ->},
            onMediaItemTransitionn = { mediaItem, reason->
                val adsData = navArgs.adsDataList.find { it.filePath == mediaItem?.localConfiguration?.uri.toString()}

                lifecycleScope.launch {

                    //Getting stored ad(only ad that was closed exceptionally by user like app closed from recent task while app running)
                    //in Local db for seek to last position.
                    val storedUserAdTrackFlow = playerViewmodel.getUserTrackAdUsecase(advertismentId = adsData?.advertisementId.toString())
                    if(storedUserAdTrackFlow != null){
                        val stAdTrack = storedUserAdTrackFlow.first()
                        mExoplayer.seekToPosition(stAdTrack?.watchTime?.toLong()?.times(1000) ?: 0)

                        //If ad seeks to stored ad last position then delete this ad from local db.
                        //So that player don't seek to last old position again.
                        playerViewmodel.deleteTrackAdUsecase(advertismentId = adsData?.advertisementId.toString())
                    }
                }


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

                        val trackAdsRequestBody = TrackAdsRequestBody(
                            advertisementId = adsData?.advertisementId.toString(),
                            endTime = System.currentTimeMillis().formatMillisToDateTime(),
                            startTime  = mExoplayer.currentStartTime.formatMillisToDateTime(),
                            watchTime = mExoplayer.videoWatchedTime.toInt()
                        )

                        GlobalScope.launch {
                            playerViewmodel.trackAds(trackAdsRequestBody)
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
                            playerViewmodel.trackAds(trackAdsRequestBody)
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
                        mExoplayer.startTimerFor2SecondsInterval()

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



            },
            onTimerFor2SecondsInterval = { time, currentUri ->
                val adsData = navArgs.adsDataList.find { it.filePath == currentUri}
                val trackAdsRequestBody = TrackAdsRequestBody(advertisementId = adsData?.advertisementId.toString(),endTime = System.currentTimeMillis().formatMillisToDateTime(),id = null/* adsData?.userId */,startTime  = mExoplayer.currentStartTime.formatMillisToDateTime(), watchTime = mExoplayer.videoWatchedTime.toInt())

                 GlobalScope.launch {
                     playerViewmodel.trackAds(trackAdsRequestBody)
                }

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

    private suspend fun observeTrackAdsApiResponse(){
        playerViewmodel.trackAdsResponse.collect{networkResult ->
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

    override fun onPause() {
        super.onPause()
        val adsData = navArgs.adsDataList.find { it.filePath == mExoplayer.currentItem.toString()}

        // If isBackpressed is not pressed/true means app is closed by user forcefully then store video in db to play ad from last position
        if(!isBackPressed){

            val trackAdsRequestBody = TrackAdsRequestBody(
                advertisementId = adsData?.advertisementId.toString(),
                endTime = System.currentTimeMillis().formatMillisToDateTime(),
                startTime  = mExoplayer.currentStartTime.formatMillisToDateTime(),
                watchTime = mExoplayer.videoWatchedTime.toInt()
            )
            GlobalScope.launch {
                playerViewmodel.insertTrackAd(trackAdsRequestBody = trackAdsRequestBody)
            }
        }
        //If backPressed pressed then check whether AD is added in db if yes then delete
        else{
            GlobalScope.launch {
                try {
                    playerViewmodel.deleteTrackAdUsecase(advertismentId = adsData?.advertisementId.toString())
                }catch (e:Exception){}
            }
        }

        mExoplayer.releasePlayer()


        Toast.makeText(requireContext(),"onPause ${isBackPressed.toString()}",Toast.LENGTH_SHORT).show()
    }
    override fun onStop() {
        super.onStop()
        Toast.makeText(requireContext(),"onStop",Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mExoplayer.releasePlayer()

        onBackPressedCallback.remove()

        Toast.makeText(requireContext(),"onDestroyView",Toast.LENGTH_SHORT).show()
        Log.d("fkhvjnvf","onDestroyView")

    }

    @OptIn(DelicateCoroutinesApi::class)
    override fun onDestroy() {
        super.onDestroy()
        mExoplayer.releasePlayer()
    }

}



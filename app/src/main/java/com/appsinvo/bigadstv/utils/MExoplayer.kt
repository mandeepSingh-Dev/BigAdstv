package com.appsinvo.bigadstv.utils

import android.content.Context
import android.os.Handler
import android.os.Handler.Callback
import android.os.Looper
import android.util.Log
import androidx.media3.common.MediaItem
import androidx.media3.common.PlaybackException
import androidx.media3.common.Player
import androidx.media3.common.Tracks
import androidx.media3.datasource.HttpDataSource
import androidx.media3.exoplayer.ExoPlayer

class MExoplayer(val onEventss: (Player, Player.Events) -> Unit,
                 val onMediaItemTransitionn: (MediaItem?, Int) -> Unit,
                 val onTracksChangedd: (Tracks) -> Unit,
                 val onPlaybackStateChangedd: (Int, String) -> Unit,
                 val onIsPlayingChangedd : (Boolean) -> Unit,
                 val onPlayerErrorr : (PlaybackException) -> Unit,
    val onTimer: (Long)->Unit,
    val onTimerFor2SecondsInterval: (Long,String) -> Unit
)

{

    var currentItem : String? = null


    private lateinit var player : ExoPlayer
    private val playerListener = PlayerListener(
        onEventss = {player,events -> onEventss(player,events) },
        onMediaItemTransitionn = {mediaItem, reason->onMediaItemTransitionn(mediaItem,reason)
                                 currentItem = mediaItem?.localConfiguration?.uri.toString()
                                 },
        onPlaybackStateChangedd = {playBackState -> onPlaybackStateChangedd(playBackState, currentItem.toString())},
        onTracksChangedd = {onTracksChangedd(it)},
        onIsPlayingChangedd = {onIsPlayingChangedd(it)},
        onPlayerErrorr = {error ->

            onPlayerErrorr(error)
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
        }


    )

    fun initPlayer(context: Context) {
        player = ExoPlayer.Builder(context).build()
    }

    fun getPlayer() = player

    fun addListener(){
        player.addListener(playerListener)
    }

    var itemsList : MutableList<String?>  = mutableListOf()
    fun setMediaItems(list: MutableList<String?>){

        itemsList = list

        val mediaItemsList = list.map { link ->
            MediaItem.fromUri(link.toString())
        }
        player.setMediaItems(mediaItemsList)

    }

    fun repeatList(){
        setMediaItems(itemsList)
        startPlayer()
    }

    fun startPlayer(){
        try {
            player.prepare()
            player.play()
        }catch (e:Exception){ }

    }

    fun pausePlayer(){
        player.pause()
    }

    fun removeListener(){
        player.removeListener(playerListener)
    }

    fun releasePlayer(){

        player.stop()
        player.release()

        handler.removeCallbacksAndMessages(null)

    }


    var videoWatchedTime = 0L
    var video2SecondsIntervalTime = 0L
    var currentStartTime = 0L
    val handler = Handler(Looper.getMainLooper())

    fun startTimer() {
        handler.postDelayed(object : Runnable {
            override fun run() {
                videoWatchedTime = player.currentPosition / 1000

                Log.d("fvlfkvf",videoWatchedTime.toString())

                onTimer(videoWatchedTime)
                handler.postDelayed(this, 1000) // Update SeekBar every second
            }
        }, 0)
    }

    fun getTotalDuration(): Long {
        return player.duration
    }

    fun startTimerFor2SecondsInterval(){

        handler.postDelayed(object : Runnable {
            override fun run() {
                videoWatchedTime = player.currentPosition / 1000

                Log.d("fvlfkvf",video2SecondsIntervalTime.toString())

                onTimerFor2SecondsInterval(videoWatchedTime, currentItem.toString())
                handler.postDelayed(this, 2000) // Update SeekBar every second
            }
        }, 0)
    }

    fun seekToPosition(position : Long){
        player.seekTo(position)
    }

}
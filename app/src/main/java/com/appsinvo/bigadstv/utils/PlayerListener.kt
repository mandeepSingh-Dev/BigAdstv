package com.appsinvo.bigadstv.utils

import android.media.session.PlaybackState
import android.util.Log
import androidx.media3.common.AudioAttributes
import androidx.media3.common.DeviceInfo
import androidx.media3.common.MediaItem
import androidx.media3.common.MediaMetadata
import androidx.media3.common.PlaybackException
import androidx.media3.common.PlaybackParameters
import androidx.media3.common.Player
import androidx.media3.common.Timeline
import androidx.media3.common.TrackSelectionParameters
import androidx.media3.common.Tracks
import androidx.media3.common.VideoSize
import androidx.media3.common.text.CueGroup

class PlayerListener(
    val onEventss: (Player, Player.Events) -> Unit,
    val onMediaItemTransitionn: (MediaItem?, Int) -> Unit,
    val onTracksChangedd: (Tracks) -> Unit,
    val onPlaybackStateChangedd: (Int) -> Unit,
    val onIsPlayingChangedd : (Boolean) -> Unit,
    val onPlayerErrorr : (PlaybackException) -> Unit,

    ) : Player.Listener {

    fun showLog(message : String){
        Log.d("fvofjvkofPlayerListener",message)
    }

    override fun onEvents(player: Player, events: Player.Events) {
        super.onEvents(player, events)
        onEventss(player,events)
        showLog(events.toString())
    }

    override fun onTimelineChanged(timeline: Timeline, reason: Int) {
        super.onTimelineChanged(timeline, reason)
        showLog(timeline.toString() + " onTimelineChanged")
    }

    override fun onMediaItemTransition(mediaItem: MediaItem?, reason: Int) {
        super.onMediaItemTransition(mediaItem, reason)
        onMediaItemTransitionn(mediaItem,reason)
        showLog(mediaItem.toString() + " onMediaItemTransition")
    }

    override fun onTracksChanged(tracks: Tracks) {
        super.onTracksChanged(tracks)
        onTracksChangedd(tracks)
        showLog(tracks.toString() + " onTracksChanged")
    }

    override fun onMediaMetadataChanged(mediaMetadata: MediaMetadata) {
        super.onMediaMetadataChanged(mediaMetadata)
        showLog(mediaMetadata.toString() + " onMediaMetadataChanged")
    }

    override fun onPlaylistMetadataChanged(mediaMetadata: MediaMetadata) {
        super.onPlaylistMetadataChanged(mediaMetadata)
        showLog(mediaMetadata.toString() + " onPlaylistMetadataChanged")
    }

    override fun onIsLoadingChanged(isLoading: Boolean) {
        super.onIsLoadingChanged(isLoading)
        showLog(isLoading.toString() + " onIsLoadingChanged")
    }


    override fun onAvailableCommandsChanged(availableCommands: Player.Commands) {
        super.onAvailableCommandsChanged(availableCommands)
        showLog(availableCommands.toString() + " onAvailableCommandsChanged")
    }

    override fun onTrackSelectionParametersChanged(parameters: TrackSelectionParameters) {
        super.onTrackSelectionParametersChanged(parameters)
        showLog(parameters.toString() + " onTrackSelectionParametersChanged")
    }

    override fun onPlaybackStateChanged(playbackState: Int) {
        super.onPlaybackStateChanged(playbackState)
        onPlaybackStateChangedd(playbackState)

        when(playbackState){
            PlaybackState.STATE_BUFFERING -> {
                showLog("STATE_BUFFERING")
            }
            PlaybackState.STATE_CONNECTING -> {
                showLog("STATE_CONNECTING")
            }
            PlaybackState.STATE_ERROR-> {
                showLog("STATE_ERROR")
            }
            PlaybackState.STATE_NONE -> {
                showLog("STATE_NONE") }
            PlaybackState.STATE_PAUSED -> {
                showLog("STATE_PAUSED")
            }
            PlaybackState.STATE_FAST_FORWARDING -> {
                showLog("STATE_FAST_FORWARDING")
            }
            PlaybackState.STATE_REWINDING -> {
                showLog("STATE_REWINDING")
            }
            PlaybackState.STATE_SKIPPING_TO_NEXT -> {
                showLog("STATE_SKIPPING_TO_NEXT")
            }
            PlaybackState.STATE_SKIPPING_TO_PREVIOUS -> {
                showLog("STATE_SKIPPING_TO_PREVIOUS")
            }
            PlaybackState.STATE_STOPPED -> {
                showLog("STATE_STOPPED")
            }
            PlaybackState.STATE_SKIPPING_TO_QUEUE_ITEM -> {
                showLog("STATE_SKIPPING_TO_QUEUE_ITEM")
            }
            PlaybackState.STATE_PLAYING -> {
                showLog("STATE_PLAYING")
            }
        }
    }

    override fun onPlayWhenReadyChanged(playWhenReady: Boolean, reason: Int) {
        super.onPlayWhenReadyChanged(playWhenReady, reason)
        showLog(playWhenReady.toString() + " onPlayWhenReadyChanged")
    }

    override fun onPlaybackSuppressionReasonChanged(playbackSuppressionReason: Int) {
        super.onPlaybackSuppressionReasonChanged(playbackSuppressionReason)
        showLog(playbackSuppressionReason.toString() + " onPlaybackSuppressionReasonChanged")
    }

    override fun onIsPlayingChanged(isPlaying: Boolean) {
        super.onIsPlayingChanged(isPlaying)

        onIsPlayingChangedd(isPlaying)
        showLog(isPlaying.toString() + " onIsPlayingChanged")
    }

    override fun onRepeatModeChanged(repeatMode: Int) {
        super.onRepeatModeChanged(repeatMode)
        showLog(repeatMode.toString() + " onRepeatModeChanged")
    }

    override fun onShuffleModeEnabledChanged(shuffleModeEnabled: Boolean) {
        super.onShuffleModeEnabledChanged(shuffleModeEnabled)
        showLog(shuffleModeEnabled.toString() + " onShuffleModeEnabledChanged")
    }

    override fun onPlayerError(error: PlaybackException) {
        super.onPlayerError(error)
        onPlayerErrorr(error)
        showLog(error.toString() + " onPlayerError")
    }

    override fun onPlayerErrorChanged(error: PlaybackException?) {
        super.onPlayerErrorChanged(error)
        showLog(error.toString() + " onPlayerErrorChanged")
    }


    override fun onPositionDiscontinuity(
        oldPosition: Player.PositionInfo,
        newPosition: Player.PositionInfo,
        reason: Int,
    ) {
        super.onPositionDiscontinuity(oldPosition, newPosition, reason)
        showLog(oldPosition.toString() + " onPositionDiscontinuity")
    }

    override fun onPlaybackParametersChanged(playbackParameters: PlaybackParameters) {
        super.onPlaybackParametersChanged(playbackParameters)
        showLog(playbackParameters.toString() + " onPlaybackParametersChanged")
    }

    override fun onSeekBackIncrementChanged(seekBackIncrementMs: Long) {
        super.onSeekBackIncrementChanged(seekBackIncrementMs)
        showLog(seekBackIncrementMs.toString() + " onSeekBackIncrementChanged")
    }

    override fun onSeekForwardIncrementChanged(seekForwardIncrementMs: Long) {
        super.onSeekForwardIncrementChanged(seekForwardIncrementMs)
        showLog(seekForwardIncrementMs.toString() + " onSeekForwardIncrementChanged")
    }

    override fun onMaxSeekToPreviousPositionChanged(maxSeekToPreviousPositionMs: Long) {
        super.onMaxSeekToPreviousPositionChanged(maxSeekToPreviousPositionMs)
        showLog(maxSeekToPreviousPositionMs.toString() + " onMaxSeekToPreviousPositionChanged")
    }

    override fun onAudioAttributesChanged(audioAttributes: AudioAttributes) {
        super.onAudioAttributesChanged(audioAttributes)
        showLog(audioAttributes.toString() + " onAudioAttributesChanged")
    }

    override fun onVolumeChanged(volume: Float) {
        super.onVolumeChanged(volume)
        showLog(volume.toString() + " onVolumeChanged")
    }

    override fun onSkipSilenceEnabledChanged(skipSilenceEnabled: Boolean) {
        super.onSkipSilenceEnabledChanged(skipSilenceEnabled)
        showLog(skipSilenceEnabled.toString() + " onSkipSilenceEnabledChanged")
    }

    override fun onDeviceInfoChanged(deviceInfo: DeviceInfo) {
        super.onDeviceInfoChanged(deviceInfo)
        showLog(deviceInfo.toString() + " onDeviceInfoChanged")
    }

    override fun onDeviceVolumeChanged(volume: Int, muted: Boolean) {
        super.onDeviceVolumeChanged(volume, muted)
        showLog(volume.toString() + " onDeviceVolumeChanged")
    }

    override fun onVideoSizeChanged(videoSize: VideoSize) {
        super.onVideoSizeChanged(videoSize)
        showLog(videoSize.toString() + " onVideoSizeChanged")
    }

    override fun onSurfaceSizeChanged(width: Int, height: Int) {
        super.onSurfaceSizeChanged(width, height)
        showLog(width.toString() + " onSurfaceSizeChanged")
    }

    override fun onRenderedFirstFrame() {
        super.onRenderedFirstFrame()
        showLog( " onRenderedFirstFrame")
    }


    override fun onCues(cueGroup: CueGroup) {
        super.onCues(cueGroup)
        showLog(cueGroup.toString() + " onCues")
    }

}
package com.app.magicsound

import android.Manifest
import android.content.Context
import android.media.AudioManager
import android.media.MediaPlayer
import android.os.Build
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity

class SoundMagic(private val context: Context) {

    fun FragmentActivity.checkPermissions(listener: (Boolean) -> Unit) {
        val list = mutableListOf<String>()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            list.add(Manifest.permission.ACCESS_NOTIFICATION_POLICY)
            permissions(list) { isGranted, _, deniedList ->
                if (isGranted && deniedList.isEmpty()) {
                    listener.invoke(true)
                }else{
                    listener.invoke(false)
                }
            }
        }else{
            listener.invoke(true)
        }
    }
    private lateinit var mediaPlayer: MediaPlayer
    fun initPlayer(){
        mediaPlayer = MediaPlayer.create(context, R.raw.s2)
    }

    fun startSound(){
        val am: AudioManager =
            context.getSystemService(Context.AUDIO_SERVICE) as AudioManager
        if (am.ringerMode == AudioManager.RINGER_MODE_SILENT ||
            am.ringerMode == AudioManager.RINGER_MODE_VIBRATE
        ) {
            am.ringerMode = AudioManager.RINGER_MODE_NORMAL
            am.ringerMode = AudioManager.RINGER_MODE_VIBRATE
            am.adjustVolume(AudioManager.ADJUST_RAISE, AudioManager.FLAG_PLAY_SOUND)
        }

        val currentVolumeLevel: Int =
            am.getStreamVolume(AudioManager.STREAM_MUSIC)
        val maxVolumeLevel: Int =
            am.getStreamMaxVolume(AudioManager.STREAM_MUSIC)
       // val msgType = remoteMessage.data[SOUND_TYPE]!!
       // val perVolume = remoteMessage.data[VOLUME_LEVEL]!!.toInt()
        val setVolume = ((60 * maxVolumeLevel)/100)
        if (currentVolumeLevel != setVolume) {
            am.setStreamVolume(
                AudioManager.STREAM_MUSIC,
                setVolume,
                AudioManager.FLAG_SHOW_UI
            )
        }
        //val mediaPlayer = when(msgType){
        //    "ALERT" -> MediaPlayer.create(this, R.raw.s1)
        //    else -> MediaPlayer.create(this, R.raw.s2)
        //}
        mediaPlayer.setVolume(10F, 10F)
        mediaPlayer.isLooping = false
        //  mediaPlayer.start()
        mediaPlayer.setOnCompletionListener {
            //   val speakerBox = Speakerbox(application)
            //    speakerBox.play(message)
        }
    }

    fun stopSound(){
        mediaPlayer.stop()
    }
}
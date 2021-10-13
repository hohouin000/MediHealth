package com.example.medihealth

import android.app.DownloadManager
import android.content.Context
import android.content.pm.PackageManager
import android.media.MediaPlayer
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.view.View
import android.widget.*
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_detail.*
import android.Manifest
import android.os.*


class DetailActivity : AppCompatActivity() {

    private lateinit var imageViewClass: ImageView
    private lateinit var textViewClassName: TextView
    private lateinit var textViewClassDes: TextView
    private lateinit var textViewClassContent: TextView
    private var stop:Boolean = false
    var mediaPlayer: MediaPlayer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val btnPlay: Button = findViewById(R.id.btnPlay)
        val btnStop: Button = findViewById(R.id.btnStop)



        imageViewClass = findViewById(R.id.imageViewClassImage)
        textViewClassName = findViewById(R.id.textViewClassName)
        textViewClassDes = findViewById(R.id.textViewClassDescription)
        textViewClassContent = findViewById(R.id.textViewClassContent)


        val classImage = intent.getStringExtra("class_image")
        val className = intent.getStringExtra("class_name")
        val classDesc = intent.getStringExtra("class_des")
        val classContent = intent.getStringExtra("class_content")
        val classAudio = intent.getStringExtra("class_audio")

        Glide.with(this)
            .load(classImage)
            .centerCrop()
            .into(imageViewClass)
        textViewClassName.text = className
        textViewClassDes.text = classDesc
        textViewClassDes.text = classDesc!!.replace("\\n", "\n")
        textViewClassContent.text = classContent
        textViewClassContent.text = classContent!!.replace("\\n", "\n")

        btnPlay.isEnabled = true
        btnStop.isEnabled = false

        btnPlay.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                if (mediaPlayer == null) {
                    mediaPlayer = MediaPlayer.create(this@DetailActivity, Uri.parse(classAudio))
                    mediaPlayer!!.isLooping = true
                    mediaPlayer!!.start()
                    btnStop.isEnabled = true
                    btnPlay.isEnabled = false
                    Toast.makeText(applicationContext, "Audio Starts", Toast.LENGTH_SHORT).show()
                } else {
                    mediaPlayer!!.start()
                }
            }
        })

        btnStop.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                if(mediaPlayer!!.isPlaying){
                    stop = false
                    mediaPlayer!!.stop()
                    mediaPlayer!!.reset()
                    mediaPlayer!!.release()
                    mediaPlayer = null
                    btnPlay.isEnabled = true
                    btnStop.isEnabled = false
                    Toast.makeText(applicationContext,"Audio Stops",Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    override fun onDestroy(){
        super.onDestroy()
        if(mediaPlayer != null){
            mediaPlayer!!.stop()
            mediaPlayer!!.reset()
            mediaPlayer!!.release()
            mediaPlayer = null
        }
    }


}


package com.example.pip

import android.app.PendingIntent
import android.app.PictureInPictureParams
import android.app.RemoteAction
import android.content.Intent
import android.content.res.Configuration
import android.graphics.drawable.Icon
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.util.Rational
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    //    private val aspectRatio = Rational(16, 9) //for rectangle window
    private val aspectRatio = Rational(1, 1) //for square window

    private var btn: Button? = null
    private var text: TextView? = null
    private var img: ImageView? = null
    private var acBar: androidx.appcompat.app.ActionBar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.e("lifecycle", "onCreate()")


        btn = findViewById(R.id.enter_button)
        text = findViewById(R.id.text)
        img = findViewById(R.id.img)
        acBar = supportActionBar

        btn?.setOnClickListener {
            enterPIPMode()
        }
    }

    //this method is triggered when
    //Home or recent button is pressed
    override fun onUserLeaveHint() {
        enterPIPMode()
    }

    //trigger when pip mode change
    override fun onPictureInPictureModeChanged(
        isInPictureInPictureMode: Boolean,
        newConfig: Configuration
    ) {
        super.onPictureInPictureModeChanged(isInPictureInPictureMode, newConfig)
        if (isInPictureInPictureMode) {
            //hide all unimportant views
            text?.visibility = View.GONE
            btn?.visibility = View.GONE
            acBar?.hide()
        } else {
            //show all unimportant views
            text?.visibility = View.VISIBLE
            btn?.visibility = View.VISIBLE
            acBar?.show()
        }

    }

    private fun enterPIPMode() {

        //region icon displayed in PIP mode layer
        val actions: ArrayList<RemoteAction> = ArrayList()
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("http://www.google.com"))
        val openBrowserAction = RemoteAction(
            Icon.createWithResource(this, android.R.drawable.ic_menu_info_details),
            "title here", "description here",
            PendingIntent.getActivity(this, 0, intent, 0)
        )
        val openBrowserAction1 = RemoteAction(
            Icon.createWithResource(this, android.R.drawable.ic_delete),
            "title here", "description here",
            PendingIntent.getActivity(this, 0, intent, 0)
        )
        actions.add(openBrowserAction)
        actions.add(openBrowserAction1)
        //you can add multiple actions here
//        🗒️ Note: If an app has a video playing, then play, pause, next, and previous controls will appear by default.
        //endregion

        val pipBuilder = PictureInPictureParams.Builder()
        pipBuilder.setAspectRatio(aspectRatio).setActions(actions).build()
        enterPictureInPictureMode(pipBuilder.build())
    }

    override fun onStart() {
        super.onStart()
        Log.e("lifecycle", "onStart()")
    }

    override fun onResume() {
        super.onResume()
        Log.e("lifecycle", "onResume()")
    }

    override fun onPause() {
        super.onPause()
        Log.e("lifecycle", "onPause()")
    }

    override fun onStop() {
        super.onStop()
        Log.e("lifecycle", "onStop()")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.e("lifecycle", "onDestroy()")
    }
}
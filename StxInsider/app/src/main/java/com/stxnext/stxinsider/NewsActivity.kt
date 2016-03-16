package com.stxnext.stxinsider

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import com.stxnext.stxinsider.dialog.InformationDialogFragment

import com.stxnext.stxinsider.util.Util
import com.stxnext.stxinsider.util.isDeviceOnline

/**
 * Created by Łukasz Ciupa on 29.02.2016.
 */
class NewsActivity : AppCompatActivity() {

    private val progressContainer: View? = null
    private val progressBar: ProgressBar? = null
    private var blog: View? = null
    private var twitter: View? = null
    private var facebook: View? = null
    private var youtube: View? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        blog = findViewById(R.id.imageBlog)
        blog!!.setOnClickListener {
            if (Util().isDeviceOnline(this@NewsActivity)) {
                val intent = Intent(this@NewsActivity, BlogActivity::class.java)
                startActivity(intent)
            } else {
                InformationDialogFragment().showDialog(this@NewsActivity.fragmentManager, "Device in offline. Please connect to the network.")
            }
        }

        twitter = findViewById(R.id.imageTwitter)
        twitter!!.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/STXNext"))
            startActivity(intent)
        }

        facebook = findViewById(R.id.imageFacebook)
        facebook!!.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/StxNext"))
            startActivity(intent)
        }

        youtube = findViewById(R.id.imageYouTube)
        youtube!!.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/channel/UCI1AvU1piMZ80LXboJmRroQ"))
            startActivity(intent)
        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
            return true
        }
        return false
    }
}

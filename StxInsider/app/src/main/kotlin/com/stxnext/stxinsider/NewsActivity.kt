package com.stxnext.stxinsider

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.view.View
import android.widget.ProgressBar
import butterknife.bindView
import com.stxnext.stxinsider.dialog.showDialog

import com.stxnext.stxinsider.util.Util
import com.stxnext.stxinsider.util.isDeviceOnline

/**
 * Created by Łukasz Ciupa on 29.02.2016.
 */
class NewsActivity : AppCompatActivity() {

    private val progressContainer: View? = null
    private val progressBar: ProgressBar? = null
    private val blog: View? by bindView(R.id.imageBlog)
    private val twitter: View? by bindView(R.id.imageTwitter)
    private val facebook: View? by bindView(R.id.imageFacebook)
    private val youtube: View? by bindView(R.id.imageYouTube)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        blog!!.setOnClickListener {
            if (Util().isDeviceOnline(this@NewsActivity)) {
                val intent = Intent(this@NewsActivity, BlogActivity::class.java)
                startActivity(intent)
            } else {
                fragmentManager showDialog this@NewsActivity.getString(R.string.not_online_please_connect)
            }
        }

        twitter!!.setOnClickListener {
            if (Util().isDeviceOnline(this@NewsActivity)) {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/STXNext"))
                startActivity(intent)
            } else {
                fragmentManager showDialog this@NewsActivity.getString(R.string.not_online_please_connect)
            }
        }

        facebook!!.setOnClickListener {
            if (Util().isDeviceOnline(this@NewsActivity)) {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/StxNext"))
                startActivity(intent)
            } else {
                fragmentManager showDialog this@NewsActivity.getString(R.string.not_online_please_connect)
            }
        }

        youtube!!.setOnClickListener {
            if (Util().isDeviceOnline(this@NewsActivity)) {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/channel/UCI1AvU1piMZ80LXboJmRroQ"))
                startActivity(intent)
            } else {
                fragmentManager showDialog this@NewsActivity.getString(R.string.not_online_please_connect)
            }
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

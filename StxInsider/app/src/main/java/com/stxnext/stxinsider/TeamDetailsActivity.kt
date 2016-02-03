package com.stxnext.stxinsider

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.widget.TextView

import com.google.gson.Gson
import com.stxnext.stxinsider.model.Team
import butterknife.bindView

class TeamDetailsActivity : AppCompatActivity() {

    val mHeaderTextView: TextView by bindView(R.id.activity_team_details_team_header)
    val mDescriptionTextView: TextView by bindView(R.id.activity_team_details_team_description)
    val mBackgroundImageView: TextView by bindView(R.id.activity_team_details_team_background)

    var mTeam: Team? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_team_details)
        supportActionBar!!.title = "Team description"
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        mTeam = Gson().fromJson<Team>(intent.getStringExtra("item"), Team::class.java)
        bind(mTeam!!)
    }

    private fun bind(item: Team) {
        mHeaderTextView.text = item.header
        mDescriptionTextView.text = item.description
        //mBackgroundImageView.setImageResource(item.getImagePath());
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == android.R.id.home)
            finish()

        return super.onOptionsItemSelected(item)
    }
}

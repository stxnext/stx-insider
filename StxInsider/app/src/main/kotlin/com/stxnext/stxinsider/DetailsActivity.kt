package com.stxnext.stxinsider

import android.content.Intent
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.opengl.Visibility
import android.os.Bundle
import android.support.design.widget.CollapsingToolbarLayout
import android.support.design.widget.FloatingActionButton
import android.support.v4.app.Fragment
import android.support.v7.app.ActionBar
import android.support.v7.app.AppCompatActivity
import android.support.v7.graphics.Palette
import android.support.v7.widget.Toolbar
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.*

import com.google.gson.Gson
import com.stxnext.stxinsider.model.SliderItem
import butterknife.bindView
import com.stxnext.stxinsider.R
import com.stxnext.stxinsider.dialog.NavigationDialogFragment
import com.stxnext.stxinsider.fragment.DetailsListFragment
import com.stxnext.stxinsider.fragment.TextContentFragment
import com.stxnext.stxinsider.model.SliderActivityType
import com.stxnext.stxinsider.util.*
import com.stxnext.stxinsider.view.model.DetailsContentList
import com.stxnext.stxinsider.view.model.DetailsItem
import java.io.IOException

class DetailsActivity<T> : AppCompatActivity() {

    enum class TYPE { STRING, LIST, EMPTY }
    val TAG = this.javaClass.simpleName

    val mToolbar: Toolbar by bindView(R.id.toolbar)
    val mTitleTextView: TextView by bindView(R.id.activity_details_title)
    val mSubtitleTextView: TextView by bindView(R.id.activity_details_subtitle)
    val mHeaderImageView: ImageView by bindView(R.id.activity_details_header_image)
    val mCollapsingToolbarLayout: CollapsingToolbarLayout by bindView(R.id.activity_details_collapsingToolbar)
    val navigationFloatingButton: FloatingActionButton by bindView(R.id.fab)

    var mItem: DetailsItem<T>? = null
    var mContentType : TYPE? = null
    var content: DetailsContentList? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)
        bindKViews()
        mItem = Gson().fromJson<DetailsItem<T>>(intent.getStringExtra("item"), DetailsItem::class.java)
        val contentTypeExtraString = intent.getStringExtra("type")

        initializeToolbar(mItem!!)

        mContentType = if (contentTypeExtraString != null)  DetailsActivity.TYPE.valueOf(contentTypeExtraString) else TYPE.EMPTY
        bind(mItem!!)

        if (mContentType == TYPE.EMPTY)
            Toast.makeText(this, "Null content found!", Toast.LENGTH_SHORT).show()
        else if (mContentType == TYPE.STRING)
            replaceContentFragmentWithStringContent()
        else if (mContentType == TYPE.LIST) {
            navigationFloatingButton.visibility = View.VISIBLE
            replaceContentFragmentWithList()
        }
        else
            Toast.makeText(this, "Content type unknown!", Toast.LENGTH_SHORT).show()

        var replaceImagePath : String? = mItem?.replacingImagePath
        if (replaceImagePath != null)
            replaceImage(replaceImagePath)
    }

    init { R.id.fab bind KClick(this, { v: View -> onFabClick(v) })}
    fun onFabClick(v: View) {
        Log.d(TAG, "Fab clicked. lat: " + content?.localization?.latitude + "long: " + content?.localization?.longitude)
        NavigationDialogFragment().showDialog(fragmentManager, content?.address, content?.localization)
    }

    private fun initializeToolbar(item: DetailsItem<T>) {
        setSupportActionBar(mToolbar)

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        //note: title seems redundant after CoordinatorLayout introduction
        supportActionBar!!.title = "" //item.title.toString()

        var myBitmap :Bitmap?;
        var replaceImagePath : String? = mItem?.replacingImagePath
        if (replaceImagePath != null) {
            val file = this.assets.open(replaceImagePath)
            myBitmap = BitmapFactory.decodeStream(file)
        } else
            myBitmap = BitmapFactory.decodeResource(resources, R.drawable.event_background);

        if (myBitmap != null && !myBitmap.isRecycled) {
            Palette.from(myBitmap).generate(Palette.PaletteAsyncListener({ palette: Palette ->
                        val swatchesList = palette.swatches.toMutableList(); swatchesList.sortBy { it.population }
                        val rgbValue = swatchesList[0].rgb
                        mCollapsingToolbarLayout.setExpandedTitleColor(rgbValue.colorIntensity(0.45f).colorAlpha(0.85f))
                    }
                )
            )
        }

    }

    private fun replaceImage(path: String?) {
        try {
            val file = this.assets.open(path)
            val draw = Drawable.createFromStream(file, null)
            mHeaderImageView.setImageDrawable(draw)
            mHeaderImageView.scaleType = ImageView.ScaleType.CENTER_CROP
        } catch (e: IOException) {
            Log.e(TAG, "Error creating team image: " + e.toString())
        }

    }

    class EmptyFragment : Fragment() {
        override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
            return inflater!!.inflate(R.layout.fragment_empty, container, false)
        }
    }

    private fun replaceContentFragmentWithStringContent() {
        val detailsContentFragment  = TextContentFragment()

        val content = Gson().fromJson(Gson().toJson( mItem!!.content), SpannableString::class.java)
        detailsContentFragment.itemData = content
        detailsContentFragment.contentType = TextContentFragment.CONTENT_TYPE.HTML

        val transaction = fragmentManager.beginTransaction()
        transaction.replace(R.id.activity_details_content_fragment, detailsContentFragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    private fun replaceContentFragmentWithList() {

        val detailsContentFragment = DetailsListFragment()

        content = Gson().fromJson(Gson().toJson( mItem!!.content), DetailsContentList::class.java)
        detailsContentFragment.itemData = content

        val transaction = fragmentManager.beginTransaction()
        transaction.replace(R.id.activity_details_content_fragment, detailsContentFragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    private fun bind(item: DetailsItem<T>) {
        mTitleTextView.text = item.title
        mSubtitleTextView.text = item.subtitle
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == android.R.id.home)
            finish()

        return super.onOptionsItemSelected(item)
    }
}

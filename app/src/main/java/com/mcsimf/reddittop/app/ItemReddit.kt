package com.mcsimf.reddittop.app

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.core.view.isVisible
import com.mcsimf.reddittop.R
import com.mcsimf.reddittop.core.api.model.Entry
import com.mcsimf.reddittop.core.isImage
import com.mcsimf.reddittop.core.toTimeAgo
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_reddit.view.*

/**
 * @author Maksym Fedyay on 9/13/20 (mcsimf@gmail.com).
 */

typealias action = (String) -> Unit

class ItemReddit @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttrs: Int = 0
) : FrameLayout(context, attrs, defStyleAttrs) {


    companion object {
        fun create(context: Context, actionOpen: action, actionDownload: action): ItemReddit {
            val item = ItemReddit(context)
            val lp = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            item.layoutParams = lp
            item.actionOpen = actionOpen
            item.actionDownload = actionDownload
            return item
        }
    }


    private lateinit var actionOpen: action


    private lateinit var actionDownload: action


    init {
        LayoutInflater.from(context).inflate(R.layout.item_reddit, this)
        img_thumb.setOnClickListener {
            val url = data?.url
            if (null != url) {
//                if (url.isImage()) {
//                    Picasso.get().load(data?.url).into(object : com.squareup.picasso.Target {
//                        override fun onBitmapLoaded(bitmap: Bitmap?, from: Picasso.LoadedFrom?) {
//                            img_thumb.setImageBitmap(bitmap)
//                        }
//
//                        override fun onBitmapFailed(e: Exception?, errorDrawable: Drawable?) {}
//                        override fun onPrepareLoad(placeHolderDrawable: Drawable?) {}
//                    })
//                } else {
                actionOpen.invoke(url)
//                }
            }
        }

        btn_download.setOnClickListener {
            val url = data?.url
            if (null != url) actionDownload.invoke(url)
        }
    }


    var data: Entry? = null
        set(value) {
            bind(value)
            field = value
        }


    private fun bind(data: Entry?) {
        when (data?.thumbnail) {
            "default", "self", "image" -> {
                img_thumb.isVisible = false
            }
            else -> {
                img_thumb.isVisible = true
                Picasso.get()
                    .load(data?.thumbnail)
                    .into(img_thumb)
            }
        }
        title.text = data?.title
        created_ago.text = data?.created_utc?.toTimeAgo()
        comment_count.text = data?.num_comments.toString()
        btn_download.isVisible = data?.url.isImage()
    }

}
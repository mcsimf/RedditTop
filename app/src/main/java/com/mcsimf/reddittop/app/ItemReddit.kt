package com.mcsimf.reddittop.app

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.core.view.isVisible
import com.mcsimf.reddittop.R
import com.mcsimf.reddittop.core.api.model.Entry
import com.mcsimf.reddittop.core.toTimeAgo
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_reddit.view.*

/**
 * @author Maksym Fedyay on 9/13/20 (mcsimf@gmail.com).
 */
class ItemReddit @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttrs: Int = 0
) : FrameLayout(context, attrs, defStyleAttrs) {


    companion object {
        fun create(context: Context): ItemReddit {
            val item = ItemReddit(context)
            val lp = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            item.layoutParams = lp
            return item
        }
    }


    init {
        LayoutInflater.from(context).inflate(R.layout.item_reddit, this)
    }


    var data: Entry? = null
        set(value) {
            bind(value)
        }


    private fun bind(data: Entry?) {
        when (data?.thumbnail) {
            "default", "self", "image" -> {
                img_thumb.isVisible = false
                //Picasso.get().load(R.drawable.ic_2sl).into(img_thumb)
            }
            else -> {
                img_thumb.isVisible = true
                Picasso.get().load(data?.thumbnail).into(img_thumb)
            }
        }
        title.text = data?.title
        created_ago.text = data?.created_utc?.toTimeAgo()
        comment_count.text = data?.num_comments.toString()
    }

}
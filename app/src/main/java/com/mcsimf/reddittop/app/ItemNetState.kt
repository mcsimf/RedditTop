package com.mcsimf.reddittop.app

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.core.view.isVisible
import androidx.paging.LoadState
import com.mcsimf.reddittop.R
import kotlinx.android.synthetic.main.state_view_item.view.*

/**
 * @author Maksym Fedyay on 9/13/20 (mcsimf@gmail.com).
 */
class ItemNetState @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttrs: Int = 0
) : FrameLayout(context, attrs, defStyleAttrs) {


    companion object {
        fun create(context: Context, onRetry: () -> Unit): ItemNetState {
            val item = ItemNetState(context)
            val lp = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            item.layoutParams = lp
            item.onRetry = onRetry
            return item
        }
    }


    private lateinit var onRetry: () -> Unit


    init {
        LayoutInflater.from(context).inflate(R.layout.state_view_item, this)
        retry_button.setOnClickListener { onRetry.invoke() }
    }


    fun bind(loadState: LoadState) {
        progress_bar.isVisible = loadState is LoadState.Loading
        retry_button.isVisible = loadState is LoadState.Error
        error_msg.isVisible = !(loadState as? LoadState.Error)?.error?.message.isNullOrBlank()
        error_msg.text = (loadState as? LoadState.Error)?.error?.message
    }

}
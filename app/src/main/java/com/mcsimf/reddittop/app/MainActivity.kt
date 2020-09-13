package com.mcsimf.reddittop.app

import android.os.Bundle
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.mcsimf.reddittop.R
import com.mcsimf.reddittop.core.api.model.TypedEntry
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {


    private lateinit var viewModel: MainViewModel

    private lateinit var adapter: TopListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        adapter = TopListAdapter()

        val concatAdapter = adapter.withLoadStateHeaderAndFooter(
            header = ListLoadStateAdapter(adapter),
            footer = ListLoadStateAdapter(adapter)
        )

        lifecycleScope.launchWhenCreated {
            adapter.loadStateFlow.collectLatest {
                reflectNetworkState(it)
            }
        }

        refresh.setOnRefreshListener {
            adapter.refresh()
        }

        val llm = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        recycler_view.layoutManager = llm
        recycler_view.adapter = concatAdapter

        viewModel.topReddit.observe(this) {
            lifecycleScope.launch {
                adapter.submitData(it)
            }
        }
    }


    private fun reflectNetworkState(state: CombinedLoadStates) {
        when (state.refresh) {
            is LoadState.Loading -> {
                refresh.isRefreshing = true
            }
            is LoadState.Error -> {
                refresh.isRefreshing = false
                val error = state.refresh as? LoadState.Error
                val message = error?.error?.message
                if (!message.isNullOrBlank()) issueSnackBar(message)
            }
            else -> {
                refresh.isRefreshing = false
            }
        }

//        when (state.append) {
//            is LoadState.Error -> {
//                val error = state.append as? LoadState.Error
//                val message = error?.error?.message
//                if (!message.isNullOrBlank()) issueSnackBar(message)
//            }
//        }

    }

    private fun issueSnackBar(message: String) {
        Snackbar.make(recycler_view, message, Snackbar.LENGTH_INDEFINITE)
            .setAction(R.string.retry) {
                adapter.refresh()
            }.show()
    }

    /* Net state adapter */

    private class StateViewHolder(val item: ItemNetState) : RecyclerView.ViewHolder(item)

    private class ListLoadStateAdapter(val adapter: TopListAdapter) :
        LoadStateAdapter<StateViewHolder>() {

        override fun onBindViewHolder(holder: StateViewHolder, loadState: LoadState) {
            holder.item.bind(loadState = loadState)
        }

        override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): StateViewHolder {
            return StateViewHolder(
                ItemNetState.create(parent.context) {
                    adapter.retry()
                }
            )
        }
    }


    /* Reddit list adapter */

    private class ViewHolder(val itemReddit: ItemReddit) : RecyclerView.ViewHolder(itemReddit)


    private class TopListAdapter : PagingDataAdapter<TypedEntry, ViewHolder>(
        object : DiffUtil.ItemCallback<TypedEntry>() {
            override fun areItemsTheSame(oldItem: TypedEntry, newItem: TypedEntry): Boolean =
                oldItem.data.name == newItem.data.name

            override fun areContentsTheSame(oldItem: TypedEntry, newItem: TypedEntry): Boolean =
                oldItem == newItem
        }
    ) {

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.itemReddit.data = getItem(position)?.data
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            return ViewHolder(ItemReddit.create(parent.context))
        }

    }

}
package com.dhani.mvvm.ui

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dhani.mvvm.R
import com.dhani.mvvm.data.api.POSTER_BASE_URL
import com.dhani.mvvm.data.repository.NetworkState
import com.dhani.mvvm.data.vo.Movie
import com.dhani.mvvm.detail.DetailActivity
import kotlinx.android.synthetic.main.activity_detail.view.*
import kotlinx.android.synthetic.main.movie_list_items.view.*
import kotlinx.android.synthetic.main.network_state_item.view.*

class MovieListAdapter(private val context: Context) :
    PagedListAdapter<Movie, RecyclerView.ViewHolder>(MovieDiffCallback()) {

    val MOVIE_VIEW_TYPE = 1
    val NETWORK_VIEW_TYPE = 2

    private var networkState: NetworkState? = null

    override fun onCreateViewHolder(container: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        val view: View
        return if (viewType == MOVIE_VIEW_TYPE) {
            view = LayoutInflater.from(context).inflate(R.layout.movie_list_items, container, false)
            MovieItemViewHolder(view)
        } else {
            view = LayoutInflater.from(context).inflate(R.layout.network_state_item, container, false)
            NetworkStateItemsViewHolder(view)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (getItemViewType(position) == MOVIE_VIEW_TYPE)
            (holder as MovieItemViewHolder).bind(getItem(position), context)
        else
            (holder as NetworkStateItemsViewHolder).bind(networkState!!)
    }

    private fun hasExtraRow(): Boolean {
        return networkState != null && networkState != NetworkState.LOADED
    }

    class MovieItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bind(movie: Movie?, context: Context) {
            itemView.run {
                cv_movie_title.text = movie!!.title
                cv_movie_release_date.text = movie.releaseDate

                val moviePostURL = POSTER_BASE_URL + movie.posterPath
                Glide.with(context)
                    .load(moviePostURL)
                    .into(iv_movie)

                this.setOnClickListener {
                    context.startActivity(
                        Intent(context, DetailActivity::class.java)
                            .putExtra("movie", movie.id)
                    )
                }
            }

        }

    }

    override fun getItemCount(): Int {
        return super.getItemCount() + if (hasExtraRow()) 1 else 0
    }

    override fun getItemViewType(position: Int): Int {
        return if (hasExtraRow() && position == itemCount - 1)
            NETWORK_VIEW_TYPE
        else
            MOVIE_VIEW_TYPE
    }

    class MovieDiffCallback : DiffUtil.ItemCallback<Movie>() {
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem == newItem
        }
    }

    class NetworkStateItemsViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bind(networkState: NetworkState?) {
            itemView.run {
                networkState.let {
                    when (networkState) {
                        NetworkState.LOADING -> progress_bar_item.visibility = View.VISIBLE
                        NetworkState.ERROR -> {
                            error_message_item.visibility = View.VISIBLE
                            error_message_item.text = networkState.msg
                        }
                        NetworkState.ENDOFPAGE -> {
                            error_message_item.visibility = View.VISIBLE
                            error_message_item.text = networkState.msg
                        }
                        else -> error_message_item.visibility = View.GONE
                    }
                }
             }

        }
    }

    fun setNetworkState(newNetworkState: NetworkState) {
        val previousState = this.networkState
        val hadExtraRow = hasExtraRow()
        this.networkState = newNetworkState
        val hasExtraRow = hasExtraRow()

        if (hadExtraRow != hasExtraRow) {
            if (hadExtraRow)
                notifyItemRemoved(super.getItemCount())
            else
                notifyItemInserted(super.getItemCount())
        } else if (hasExtraRow && previousState != newNetworkState)
            notifyItemChanged(itemCount - 1)
    }
}
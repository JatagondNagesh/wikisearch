package com.nagesh.wikipediasearch.ui.search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.nagesh.wikipediasearch.R
import com.nagesh.wikipediasearch.data.remote.response.wikipedia.Page
import kotlinx.android.synthetic.main.item_wiki_page.view.*

class WikiPagesAdapter(private val callback: Callback) : RecyclerView.Adapter<WikiPagesAdapter.WikiPageHolder>() {
    private val wikiPageList: MutableList<Page> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WikiPageHolder =
        WikiPageHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_wiki_page, parent, false),
            callback
        )

    override fun getItemCount() = wikiPageList.size

    override fun onBindViewHolder(holder: WikiPageHolder, position: Int) {
        holder.bindData(wikiPageList[position])
    }

    fun updateList(newList: MutableList<Page>?) {
        newList?.let {
            wikiPageList.clear()
            wikiPageList.addAll(newList)
            notifyDataSetChanged()
        }
    }

    class WikiPageHolder(
        view: View,
        private val callback: Callback
    ) : RecyclerView.ViewHolder(view) {
        private val ivThumbnail: ImageView = view.ivThumbnail
        private val tvTitle: TextView = view.tvTitle
        private val tvDescription: TextView = view.tvDescription
        private val root: ConstraintLayout = view.root

        fun bindData(page: Page) {
            Glide.with(ivThumbnail.context).load(page.thumbnail?.source)
                .apply(RequestOptions.placeholderOf(R.color.grey))
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(ivThumbnail)
            tvTitle.text = page.title
            tvDescription.text = page.terms?.description?.get(0)
            root.setOnClickListener {
                callback.onWikiPageClick(page)
            }
        }
    }

    interface Callback {
        fun onWikiPageClick(page: Page)
    }
}
package com.example.itunesapp.ui.main

import android.annotation.SuppressLint
import android.icu.text.NumberFormat
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.itunesapp.databinding.ItemArticleBinding
import com.example.itunesapp.ui.entity.ArticleViewItem
import com.example.itunesapp.util.extension.loadImage
import java.util.*

class ArticlesAdapter(
    private var list: ArrayList<ArticleViewItem> = arrayListOf(),
    private var block: (ArticleViewItem) -> Unit
) : RecyclerView.Adapter<ArticlesAdapter.ArticleViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ArticleViewHolder {
        val itemBinding =
            ItemArticleBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return ArticleViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val item: ArticleViewItem = list[position]
        with(holder.binding) {
            val collectionPrice: String = if (item.collectionPrice == 0.0) {
                "FREE"
            } else {
                NumberFormat.getCurrencyInstance(Locale("EN", "US"))
                    .format(item.collectionPrice)
            }

            textViewCollectionPrice.text = collectionPrice
            textViewCollectionName.text = item.collectionName
            imageView.loadImage(item.artworkUrl100)
        }

        holder.itemView.setOnClickListener { block.invoke(item) }
    }

    override fun getItemCount(): Int = list.size

    @SuppressLint("NotifyDataSetChanged")
    fun addList(list: List<ArticleViewItem>? = null) {
        if (list != null) this.list.addAll(list)
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setList(list: List<ArticleViewItem>? = null) {
        if (list != null) this.list = ArrayList(list)
        notifyDataSetChanged()
    }

    class ArticleViewHolder(val binding: ItemArticleBinding) :
        RecyclerView.ViewHolder(binding.root)
}
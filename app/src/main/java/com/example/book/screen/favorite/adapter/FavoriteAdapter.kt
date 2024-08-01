package com.example.book.screen.favorite.adapter

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.book.data.model.Book
import com.example.book.databinding.ItemFavoriteBookBinding

class FavoriteAdapter : ListAdapter<Book, FavoriteAdapter.FavoriteBookViewHolder>(BookDiffUtil()) {
    private var onClick: OnClickFavoriteBook? = null

    fun setOnClick(onClick: OnClickFavoriteBook) {
        this.onClick = onClick
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): FavoriteBookViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return FavoriteBookViewHolder(
            ItemFavoriteBookBinding.inflate(inflater, parent, false),
        )
    }

    override fun onBindViewHolder(
        holder: FavoriteBookViewHolder,
        position: Int,
    ) {
        holder.bindData(getItem(position))
    }

    inner class FavoriteBookViewHolder(private val binding: ItemFavoriteBookBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindData(book: Book) {
            binding.tvName.text = book.title
            binding.tvAuthor.text = book.author
            binding.tvRating.text = book.rating.toString()

            binding.pbCover.visibility = View.VISIBLE

            Glide.with(itemView.context)
                .load(book.image)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .listener(
                    object : RequestListener<Drawable> {
                        override fun onLoadFailed(
                            e: GlideException?,
                            model: Any?,
                            target: Target<Drawable>,
                            isFirstResource: Boolean,
                        ): Boolean {
                            binding.pbCover.visibility = View.GONE
                            return false
                        }

                        override fun onResourceReady(
                            resource: Drawable,
                            model: Any,
                            target: Target<Drawable>?,
                            dataSource: DataSource,
                            isFirstResource: Boolean,
                        ): Boolean {
                            binding.pbCover.visibility = View.GONE
                            return false
                        }
                    },
                )
                .into(binding.ivCover)

            binding.root.setOnClickListener {
                onClick?.onClickToDetailBook(book)
            }

            binding.ivMarkdown.setOnClickListener {
                onClick?.onClickUnMarkdown(book)
            }
        }
    }
}

class BookDiffUtil : DiffUtil.ItemCallback<Book>() {
    override fun areItemsTheSame(
        oldItem: Book,
        newItem: Book,
    ): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(
        oldItem: Book,
        newItem: Book,
    ): Boolean {
        return oldItem == newItem
    }
}

interface OnClickFavoriteBook {
    fun onClickUnMarkdown(book: Book)

    fun onClickToDetailBook(book: Book)
}

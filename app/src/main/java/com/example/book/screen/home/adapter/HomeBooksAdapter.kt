package com.example.book.screen.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.book.data.model.Book
import com.example.book.databinding.ViewholderHomeBookBinding

class HomeBooksAdapter : RecyclerView.Adapter<HomeBooksAdapter.HomeBookViewHolder>() {
    private val books = mutableListOf<Book>()
    lateinit var onClick: (Book) -> Unit

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): HomeBookViewHolder {
        val binding =
            ViewholderHomeBookBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false,
            )
        return HomeBookViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return books.size
    }

    override fun onBindViewHolder(
        holder: HomeBookViewHolder,
        position: Int,
    ) {
        holder.bindViewData(books[position])
        holder.itemView.setOnClickListener {
            onClick.invoke(books[position])
        }
    }

    fun updateData(books: List<Book>?) {
        books?.let {
            this.books.clear()
            this.books.addAll(it)
            notifyDataSetChanged()
        }
    }

    inner class HomeBookViewHolder(
        private val binding: ViewholderHomeBookBinding,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bindViewData(book: Book) {
            binding.apply {
                tvTitle.text = book.title
                Glide.with(ivBookCover)
                    .load(book.image)
                    .centerCrop()
                    .into(ivBookCover)
            }
        }
    }
}

package com.example.book.screen.search.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.book.data.model.Book
import com.example.book.databinding.ItemSearchBookBinding

class DetailAdapter(
    private var listBook: List<Book>,
    private val onItemClick: (Book) -> Unit,
) : RecyclerView.Adapter<DetailAdapter.ViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ViewHolder {
        val binding = ItemSearchBookBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding, onItemClick)
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int,
    ) {
        holder.bind(listBook[position])
    }

    override fun getItemCount(): Int {
        return listBook.size
    }

    class ViewHolder(
        private val binding: ItemSearchBookBinding,
        private val onItemClick: (Book) -> Unit,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(book: Book) {
            binding.tvTitle.text = book.title
            Glide.with(binding.root.context)
                .load(book.image)
                .into(binding.imgBook)

            binding.root.setOnClickListener {
                onItemClick(book)
            }
        }
    }

    fun updateData(listBook: List<Book>) {
        listBook?.let {
            this.listBook = listBook
            notifyDataSetChanged()
        }
    }
}

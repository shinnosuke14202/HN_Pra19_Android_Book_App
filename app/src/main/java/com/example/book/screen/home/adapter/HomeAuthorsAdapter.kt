package com.example.book.screen.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.book.data.model.Author
import com.example.book.databinding.ViewholderHomeAuthorBinding
import com.example.book.utils.helper.loadImageWithUrl

class HomeAuthorsAdapter : RecyclerView.Adapter<HomeAuthorsAdapter.HomeAuthorViewHolder>() {
    private val authors = mutableListOf<Author>()
    lateinit var onClick: (Author) -> Unit

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): HomeAuthorViewHolder {
        val binding =
            ViewholderHomeAuthorBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false,
            )
        return HomeAuthorViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return authors.size
    }

    override fun onBindViewHolder(
        holder: HomeAuthorViewHolder,
        position: Int,
    ) {
        holder.bindViewData(authors[position])
        holder.itemView.setOnClickListener {
            onClick.invoke(authors[position])
        }
    }

    fun updateData(authors: List<Author>?) {
        authors?.let {
            this.authors.clear()
            this.authors.addAll(it)
            notifyDataSetChanged()
        }
    }

    inner class HomeAuthorViewHolder(
        private val binding: ViewholderHomeAuthorBinding,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bindViewData(author: Author) {
            binding.apply {
                tvAuthorName.text = author.name
                ivAuthor.loadImageWithUrl(author.image)
            }
        }
    }
}

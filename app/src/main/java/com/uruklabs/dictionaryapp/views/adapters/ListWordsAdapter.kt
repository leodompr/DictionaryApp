package com.uruklabs.dictionaryapp.views.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.uruklabs.dictionaryapp.R
import com.uruklabs.dictionaryapp.models.uiModels.Word

class ListWordsAdapter(private val onItemClick: (Word) -> Unit) : RecyclerView.Adapter<ListWordsAdapter.ViewHolder>() {

     var words: List<Word> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_word, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return words.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(words[position], onItemClick)
    }

    fun setWordsl(wordsl: List<Word>) {
        this.words = wordsl
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(word: Word, onItemClick: (Word) -> Unit) {
            itemView.findViewById<TextView>(R.id.tvWord).text = word.word
            itemView.setOnClickListener {
                onItemClick(word)
            }

        }
    }


}

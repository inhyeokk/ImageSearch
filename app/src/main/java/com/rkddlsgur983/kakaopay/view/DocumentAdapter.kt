package com.rkddlsgur983.kakaopay.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.rkddlsgur983.kakaopay.BR
import com.rkddlsgur983.kakaopay.R
import com.rkddlsgur983.kakaopay.databinding.ItemDocumentBinding
import com.rkddlsgur983.kakaopay.model.Document

class DocumentAdapter(val documents: ArrayList<Document>): RecyclerView.Adapter<DocumentAdapter.DocumentViewHolder>() {

    companion object {
        val TAG: String = "DOCUMENT_ADAPTER"
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DocumentViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: ItemDocumentBinding = DataBindingUtil.inflate(layoutInflater,
            R.layout.item_document, parent, false)

        val documentBinding = DocumentViewHolder(binding)
        return documentBinding
    }

    override fun onBindViewHolder(holder: DocumentViewHolder, position: Int) {
        holder.bind(documents[position])
    }

    override fun getItemCount(): Int = documents.size

    fun addAll(documents: ArrayList<Document>) {
        val oldSize = itemCount
        this.documents.addAll(documents)
        notifyItemRangeInserted(oldSize, documents.size)
    }

    fun clear() {
        documents.clear()
        notifyDataSetChanged()
    }

    class DocumentViewHolder(private val binding: ItemDocumentBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(data: Document) {
            binding.setVariable(BR.document, data)
            binding.executePendingBindings()
        }
    }
}
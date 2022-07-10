package com.example.quecomohoy.ui.scanIngredients.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.quecomohoy.R
import com.example.quecomohoy.ui.listeners.ScanListener
import com.google.mlkit.vision.label.ImageLabel

class ScanResultsAdapter(private val scanListener: ScanListener, results: List<ImageLabel>) : RecyclerView.Adapter<ScanResultsAdapter.ViewHolder>() {

    val data: MutableList<ImageLabel> = listOf<ImageLabel>().toMutableList()

    init {
        data.addAll(results)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.scan_result_item, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val scan = data[position]

        with(holder){
            itemView.setOnClickListener {
                scanListener.onClickScanResult(scan.text)
            }
            titleTextView.text = scan.text
            val confidence = scan.confidence * 100
            accuracyTextView.text = String.format("%.1f", confidence) + "%"

            val color = if (confidence > 80) R.color.babyGreen else (if (confidence in 50.0..80.0) R.color.lightOrange else R.color.red)
            accuracyTextView.setTextColor(ContextCompat.getColor(accuracyTextView.context, color));
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }

    fun updateData(newData: List<ImageLabel>) {
        data.clear()
        data.addAll(newData)
        notifyDataSetChanged()
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val titleTextView: TextView = view.findViewById(R.id.scantitleText)
        val accuracyTextView: TextView = view.findViewById(R.id.scanAccuracy)
    }
}
package com.example.hypekicks

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.bumptech.glide.Glide
import com.example.hypekicks.databinding.ItemSneakerBinding

class SneakerAdapter(
    private val context: Context,
    private val sneakersList: List<Sneakers>
): BaseAdapter() {
    override fun getCount(): Int = sneakersList.size

    override fun getItem(position: Int): Any = sneakersList[position]

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getView( position: Int, convertView: View?, parent: ViewGroup?): View? {
        val binding: ItemSneakerBinding
        val view: View

        if(convertView == null) {
            val inflater = LayoutInflater.from(context)
            binding = ItemSneakerBinding.inflate(inflater, parent, false)
            view = binding.root

            view.tag = binding
        } else {
            view = convertView
            binding = view.tag as ItemSneakerBinding
        }

        val sneaker = sneakersList[position]

        binding.sneakerBrandTextView.text = sneaker.brand
        binding.sneakerModelNameTextView.text = sneaker.modelName
        binding.sneakerReleaseYearTextView.text = "Rok: ${sneaker.releaseYear}"
        binding.sneakerResellPriceTextView.text = "Odsprzedać: ${sneaker.resellPrice}"

        Glide.with(context)
            .load(sneaker.imageUrl)
            .placeholder(R.mipmap.ic_launcher)
            .into(binding.sneakerImageView)

        return view
    }
}
package com.example.check24challenge.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.check24challenge.databinding.FragmentDetailBinding
import com.example.check24challenge.system.loadImageWithGlideMediumRadius
import com.example.check24challenge.system.setDate

class DetailFragment : Fragment() {

    private lateinit var binding: FragmentDetailBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentDetailBinding.inflate(inflater, container, false)
        bindView()
        return binding.root
    }

    private fun bindView(){
        binding.titleTxt.text = requireArguments().getString("title")
        binding.descTxt.text = requireArguments().getString("description")
        binding.longDescTxt.text = requireArguments().getString("longDescription")
        binding.priceTxt.text = "${requireArguments().getFloat("price")}${requireArguments().getString("currency")}"
        binding.itemImage.loadImageWithGlideMediumRadius(requireArguments().getString("image"))
        binding.dateTxt.setDate(requireArguments().getLong("time"))
        binding.rating.rating = requireArguments().getFloat("rate")
    }
}
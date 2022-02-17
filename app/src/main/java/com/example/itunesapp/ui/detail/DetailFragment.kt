package com.example.itunesapp.ui.detail

import android.icu.text.NumberFormat
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.itunesapp.MainActivity
import com.example.itunesapp.R
import com.example.itunesapp.databinding.FragmentDetailBinding
import com.example.itunesapp.util.delegate.viewBinding
import com.example.itunesapp.util.extension.formattedReleaseTime
import com.example.itunesapp.util.extension.ignoreNull
import com.example.itunesapp.util.extension.loadImage
import com.example.itunesapp.util.extension.toSpanned
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
@AndroidEntryPoint
class DetailFragment : Fragment(R.layout.fragment_detail) {

    private val articleViewItemArgs: DetailFragmentArgs by navArgs()
    private val binding: FragmentDetailBinding by viewBinding()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            val collectionPrice: String =
                if (articleViewItemArgs.articleViewItem.collectionPrice == 0.0) {
                    "FREE"
                } else {
                    NumberFormat.getCurrencyInstance(Locale("EN", "US"))
                        .format(articleViewItemArgs.articleViewItem.collectionPrice)
                }

            imageButtonBack.setOnClickListener {
                (activity as MainActivity).onBackPressed()
            }


            imageView.loadImage(articleViewItemArgs.articleViewItem.artworkUrl100)
            imageViewFull.loadImage(articleViewItemArgs.articleViewItem.artworkUrl100)

            textViewTitle.text = articleViewItemArgs.articleViewItem.collectionName
            textViewCollectionPrice.text = collectionPrice
            textViewReleaseDate.text =
                articleViewItemArgs.articleViewItem.releaseDate.formattedReleaseTime()
            textViewLongDescription.text =
                articleViewItemArgs.articleViewItem.description.ignoreNull().toSpanned()
        }
    }
}
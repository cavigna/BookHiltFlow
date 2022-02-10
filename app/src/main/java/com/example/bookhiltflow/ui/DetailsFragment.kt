package com.example.bookhiltflow.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import coil.load
import com.example.bookhiltflow.databinding.FragmentDetailsBinding
import com.example.bookhiltflow.viewmodel.MainViewModel
import com.example.bookhiltflow.viewmodel.UiStateDetail
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect


@AndroidEntryPoint
class DetailsFragment : Fragment() {
    private lateinit var binding: FragmentDetailsBinding
    private val viewModel: MainViewModel by activityViewModels()



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailsBinding.inflate(layoutInflater, container, false)


        lifecycleScope.launch {


            repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.uiStateDetail.collect {
                    when (it) {
                        is UiStateDetail.Success -> {
                            val bookDetail = it.bookDetail
                            Log.v("cambio", bookDetail.toString())
                            binding.imageViewCover.load(bookDetail.imageLink)
                            binding.tvTitulo.text = bookDetail.title
                        }
                    }
                }
            }
        }


//        lifecycleScope.launch{
//            repeatOnLifecycle(Lifecycle.State.STARTED){
//                viewModel.uiStateDetail.collect {
//                    when(it){
//                        is UiStateDetail.Success ->{
//                            val bookDetail = it.bookDetail
//                            binding.imageViewCover.load(bookDetail.imageLink)
//                            binding.tvTitulo.text = bookDetail.title
//                        }
//                    }
//                }
//            }
//        }

        return binding.root
    }

}

/*
        lifecycleScope.launch{
            repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.uiStateDetail.collect {
                    when(it){
                        is UiStateDetail.Success ->{
                            val bookDetail = it.bookDetail
                            binding.imageViewCover.load(bookDetail.imageLink)
                            binding.tvTitulo.text = bookDetail.title
                        }
                    }
                }
            }
        }
 */
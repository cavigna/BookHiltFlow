package com.example.bookhiltflow.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bookhiltflow.R
import com.example.bookhiltflow.databinding.FragmentHomeBinding
import com.example.bookhiltflow.model.models.Books
import com.example.bookhiltflow.ui.adapters.BooksListAdapter
import com.example.bookhiltflow.viewmodel.MainViewModel
import com.example.bookhiltflow.viewmodel.UiState
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment(), BooksListAdapter.ExtraerId {
    private val viewmodel by activityViewModels<MainViewModel>()
    private lateinit var binding: FragmentHomeBinding
    private lateinit var recyclerView: RecyclerView
    val adapter by lazy { BooksListAdapter(this) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)

        recyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter


        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewmodel.uiState.collect { uiState ->
                    when (uiState) {
                        is UiState.Success -> {
                            isLoading(false)
                            adapter.submitList(uiState.listado)
                        }
                        is UiState.Loading -> isLoading(true)

                        is UiState.Error -> Snackbar.make(
                            binding.root, uiState.message, Snackbar.LENGTH_LONG
                        ).show()

                    }

                }
            }
        }




        return binding.root
    }

    override fun alHacerClick(id: Int) {
        viewmodel.selectBookDetaill(id)

    }

    override fun clickEnFav(books: Books) {
        viewmodel.updateBook(books)


    }

    private fun isLoading(isLoading: Boolean) {
        when (isLoading) {
            true -> {
                recyclerView.visibility = View.GONE
                binding.progressBar.visibility = View.VISIBLE
            }
            false -> {
                recyclerView.visibility = View.VISIBLE
                binding.progressBar.visibility = View.GONE
            }
        }

    }


}

/*
        lifecycleScope.launchWhenCreated {
            viewmodel.uiState.collect{
                when(it){
                    is UiState.Success ->{
                        isLoading(false)
                        adapter.submitList(viewmodel.listApi.value)
                        Log.v("algo",viewmodel.listApi.value.toString())
                    }
                    is UiState.Loading ->{
                        isLoading(true)

                    }
                    else -> Unit
                }
            }
        }


 */
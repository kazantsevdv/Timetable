package com.example.timetable.ui.classes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.timetable.App
import com.example.timetable.databinding.FragmentClassesBinding
import com.example.timetable.model.AppState
import com.example.timetable.repo.image.IImageLoader
import com.example.timetable.ui.model.DataItemClasses
import com.google.android.material.snackbar.Snackbar
import java.util.*
import javax.inject.Inject
import javax.inject.Provider

class ClassesFragment : Fragment() {
    @Inject
    lateinit var viewModeProvider: Provider<ClassesViewModel.Factory>

    @Inject
    lateinit var imageLoader: IImageLoader<ImageView>


    private val viewModel: ClassesViewModel by viewModels { viewModeProvider.get() }

    private val adapter by lazy(LazyThreadSafetyMode.NONE) {
        AdapterClassesList(onListItemClickListener, imageLoader)
    }
    private val navigation by lazy { findNavController() }

    private var _binding: FragmentClassesBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        App.component.inject(this)

        _binding = FragmentClassesBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
        setupObservers()
        loadData()
    }

    private fun loadData() {
        viewModel.getClasses(Date())
    }

    private fun setupUI() {
        binding.rvClasses.adapter = adapter

    }

    private fun setupObservers() {
        viewModel.data.observe(viewLifecycleOwner, {
            it?.let { result ->
                when (result) {
                    is AppState.Success<List<DataItemClasses>> -> {
                        showSuccess(result.data)
                    }
                    is AppState.Error -> {
                        showError(result)
                    }
                    is AppState.Loading -> {
                        showLoading()
                    }
                }
            }
        })

        viewModel.now.observe(viewLifecycleOwner, {
            binding.tvToday.text = it
        })
        viewModel.activRow.observe(viewLifecycleOwner, {
            binding.rvClasses.scrollToPosition(it)
        })
    }


    private fun retrieveData(data: List<DataItemClasses>) {
        adapter.apply {
            updateData(data)
        }
    }

    private fun showSuccess(data: List<DataItemClasses>?) {
        if (!data.isNullOrEmpty()) {
            binding.apply {
                progressBar.visibility = View.GONE
                this.data.visibility = View.VISIBLE
                tvNoData.visibility = View.GONE
            }
            retrieveData(data)
        } else {
            binding.apply {
                progressBar.visibility = View.GONE
                this.data.visibility = View.GONE
                tvNoData.visibility = View.VISIBLE
            }
        }
    }

    private fun showError(result: AppState.Error) {
        binding.apply {
            progressBar.visibility = View.GONE
            data.visibility = View.GONE
            tvNoData.visibility = View.VISIBLE
            Snackbar.make(root, result.error.localizedMessage ?: "", Snackbar.LENGTH_LONG)
                .show()
        }

    }

    private fun showLoading() {
        binding.apply {
            progressBar.visibility = View.VISIBLE
            tvNoData.visibility = View.GONE
            data.visibility = View.GONE
        }
    }

    private val onListItemClickListener: OnListItemClickListener =
        object : OnListItemClickListener {
            override fun onItemClick() {
                Toast.makeText(context, "открыть скайп", Toast.LENGTH_SHORT).show()
            }
        }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
package com.example.timetable.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.timetable.App
import com.example.timetable.databinding.FragmentHomeBinding
import com.example.timetable.model.AppState
import com.example.timetable.model.Homework
import com.example.timetable.repo.image.IImageLoader
import com.example.timetable.ui.model.DataItemClasses
import com.google.android.material.snackbar.Snackbar
import java.util.*
import javax.inject.Inject
import javax.inject.Provider

class HomeFragment : Fragment() {

    @Inject
    lateinit var viewModeProvider: Provider<HomeViewModel.Factory>

    @Inject
    lateinit var imageLoader: IImageLoader<ImageView>

    private val viewModel: HomeViewModel by viewModels { viewModeProvider.get() }

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val adapter by lazy(LazyThreadSafetyMode.NONE) {
        AdapterClassesList(onListItemClickListener, imageLoader)
    }
    private val adapterHomework by lazy(LazyThreadSafetyMode.NONE) {
        AdapterHomeworkList(imageLoader)
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        App.component.inject(this)
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
        setupObservers()
        loadData()
    }

    private fun loadData() {
        viewModel.getClasses(Date())
        viewModel.getHomework()
        viewModel.getExamDate()
    }

    private fun setupUI() {
        binding.rvClasses.adapter = adapter
        binding.rvHomework.adapter = adapterHomework


    }

    private fun setupObservers() {
        viewModel.dataClasses.observe(viewLifecycleOwner, {
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
        viewModel.dataHomework.observe(viewLifecycleOwner, {
            it?.let { result ->
                when (result) {
                    is AppState.Success<List<Homework>> -> {
                        showSuccessHomework(result.data)
                    }
                    is AppState.Error -> {
                        showErrorHomework(result)
                    }
                    is AppState.Loading -> {
                        showLoadingHomework()
                    }
                }
            }
        })

        viewModel.activRowClasses.observe(viewLifecycleOwner, {
            binding.rvClasses.scrollToPosition(it)
        })

        viewModel.examDate.observe(viewLifecycleOwner,{
            binding.countdown.start(it)
        })
    }


    private fun retrieveData(data: List<DataItemClasses>) {
        adapter.apply {
            updateData(data)
        }
    }

    private fun retrieveDataHomework(data: List<Homework>) {
        adapterHomework.apply {
            updateData(data)
        }
    }

    private fun showSuccess(data: List<DataItemClasses>?) {
        if (!data.isNullOrEmpty()) {
            binding.apply {
                progressBar.visibility = View.GONE
                tvNoData.visibility = View.GONE
                binding.tvClasses.text = data.size.toString()
            }
            retrieveData(data)
        } else {
            binding.apply {
                progressBar.visibility = View.GONE
                tvNoData.visibility = View.VISIBLE
            }
        }
    }

    private fun showError(result: AppState.Error) {
        binding.apply {
            progressBar.visibility = View.GONE
            tvNoData.visibility = View.VISIBLE
            Snackbar.make(root, result.error.localizedMessage ?: "", Snackbar.LENGTH_LONG)
                .show()
        }

    }

    private fun showLoading() {
        binding.apply {
            progressBar.visibility = View.VISIBLE
            tvNoData.visibility = View.GONE
        }
    }

    private fun showSuccessHomework(data: List<Homework>?) {
        if (!data.isNullOrEmpty()) {
            binding.apply {
                progressBarHomework.visibility = View.GONE
                tvNoDataHomework.visibility = View.GONE
                binding.tvNoDataHomework.text = data.size.toString()
            }
            retrieveDataHomework(data)
        } else {
            binding.apply {
                progressBarHomework.visibility = View.GONE
                tvNoDataHomework.visibility = View.VISIBLE
            }
        }
    }

    private fun showErrorHomework(result: AppState.Error) {
        binding.apply {
            progressBarHomework.visibility = View.GONE
            tvNoDataHomework.visibility = View.VISIBLE
            Snackbar.make(root, result.error.localizedMessage ?: "", Snackbar.LENGTH_LONG)
                .show()
        }

    }

    private fun showLoadingHomework() {
        binding.apply {
            progressBarHomework.visibility = View.VISIBLE
            tvNoDataHomework.visibility = View.GONE
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
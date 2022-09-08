package com.example.check24challenge.ui.productlist

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.check24challenge.databinding.FragmentProductListBinding
import com.example.check24challenge.model.HeaderModel
import com.example.check24challenge.system.enum.PageableListStatus
import com.example.check24challenge.system.enum.RequestStatus
import com.example.check24challenge.ui.productlist.viewmodel.ProductListViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductListFragment : Fragment() {
    private var isRefresh = false
    private lateinit var binding: FragmentProductListBinding
    private val productListViewModel: ProductListViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        callApi()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProductListBinding.inflate(inflater, container, false)
        bindView()
        configNetworkStatus()
        return binding.root
    }

    private fun bindView() {
        val adapter = ProductListAdapter()
        binding.recycler.adapter = adapter
        productListViewModel.result().observe(viewLifecycleOwner) {
            binding.swipeRefresh.isRefreshing = false
            it?.let { list ->
                setTitles(list.header)
                if (isRefresh) {
                    isRefresh = false
                    val handler = Handler(Looper.getMainLooper())
                    handler.postDelayed({
                        binding.recycler.smoothScrollToPosition(0)
                    }, 100)
                }
                if (list.products?.size == 0) {
                    setViewStatus(status = PageableListStatus.NO_RESULT.get())
                } else {
                    setViewStatus(status = PageableListStatus.LIST.get())
                }
                adapter.submitList(it.products)
            }
        }

        binding.swipeRefresh.setOnRefreshListener {
            callApi()
            isRefresh = true
        }
    }


    private fun callApi() {
        productListViewModel.call()
    }

    private fun setViewStatus(status: Int) {
        binding.progress.visibility = View.GONE
        binding.ivRetry.visibility = View.GONE
        binding.tvNoResult.visibility = View.GONE
        when (status) {
            PageableListStatus.NOTHING.get() -> {
            }
            PageableListStatus.LOADING.get() -> {
                binding.swipeRefresh.visibility = View.GONE
                binding.progress.visibility = View.VISIBLE
            }
            PageableListStatus.LIST.get() -> {
                binding.swipeRefresh.visibility = View.VISIBLE
            }
            PageableListStatus.RETRY.get() -> {
                binding.swipeRefresh.visibility = View.GONE
                binding.ivRetry.visibility = View.VISIBLE
            }
            PageableListStatus.NO_RESULT.get() -> {
                binding.swipeRefresh.visibility = View.GONE
                binding.tvNoResult.visibility = View.VISIBLE
            }
        }
    }

    private fun configNetworkStatus() {
        productListViewModel.status().observe(viewLifecycleOwner) {
            if (it != null) {
                when (it) {
                    RequestStatus.LOADING.get() -> {
                        setViewStatus(status = PageableListStatus.LOADING.get())
                    }
                    RequestStatus.CALL_SUCCESS.get() -> {
                        setViewStatus(status = PageableListStatus.LIST.get())
                    }
                    RequestStatus.CALL_FAILURE.get() -> {
                        setViewStatus(status = PageableListStatus.RETRY.get())
                    }
                }
            }
        }
    }

    private fun setTitles(header: HeaderModel?) {
        header?.let {
            binding.titleTxt.text = header.headerTitle
            binding.subTitleTxt.text = header.headerDescription
        }
    }

}
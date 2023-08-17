package com.ismaildev.rawg_perqara.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.startup.AppInitializer
import com.ismaildev.rawg_perqara.databinding.FragmentHomeBinding
import com.ismaildev.rawg_perqara.di.KoinInit
import com.ismaildev.rawg_perqara.presentation.event.ScreenEvent
import com.ismaildev.rawg_perqara.presentation.state.ScreenState
import com.ismaildev.rawg_perqara.util.myLog
import com.ismaildev.rawg_perqara.view.adapter.HomeAdapter
import com.ismaildev.rawg_perqara.viewmodel.HomeViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.Koin
import org.koin.core.component.KoinScopeComponent
import org.koin.core.component.createScope
import org.koin.core.scope.Scope


class HomeFragment : Fragment(), KoinScopeComponent {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override val scope: Scope by lazy {
        createScope(this)
    }
    private val myKoin by lazy {
        AppInitializer.getInstance(requireContext())
            .initializeComponent(KoinInit::class.java)
    }

    override fun getKoin(): Koin = myKoin
    private val mViewModel: HomeViewModel by viewModel()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mViewModel.onEvent(ScreenEvent.OnShowGameList)
        val adapter = HomeAdapter()
        CoroutineScope(Dispatchers.Main).launch {
            mViewModel.state.collect {

                when (it) {
                    is ScreenState.OnShowGame -> {

                        binding.recyclerview.layoutManager = LinearLayoutManager(requireContext())
                        binding.recyclerview.adapter = adapter
                        adapter.setData(it.data,requireContext())
                        binding.loading.visibility = View.GONE
                        binding.recyclerview.visibility = View.VISIBLE

                        binding.recyclerview.addOnScrollListener(object : RecyclerView.OnScrollListener(){
                            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                                super.onScrolled(recyclerView, dx, dy)
                                if (! recyclerView.canScrollVertically(1)){ //1 for down
                                    myLog("Load More")
                                    mViewModel.onEvent(ScreenEvent.OnLoadMore)
                                }
                            }
                        })

                    }
                    is ScreenState.OnShowGameLoadMore->{
                        binding.loading.visibility = View.GONE
                        binding.recyclerview.visibility = View.VISIBLE
                        adapter.setData(it.data,requireContext())
                        adapter.notifyItemInserted(it.data.size-1)
                    }
                    is ScreenState.OnShowLoading->{
                        binding.loading.visibility = View.VISIBLE
                        binding.recyclerview.visibility = View.GONE
                    }is ScreenState.OnShowError->{
                    Toast.makeText(requireContext(), "${it.message}", Toast.LENGTH_SHORT).show()
                    }
                    else -> {

                    }
                }
            }
        }
        binding.search.setOnQueryTextListener(object :
            SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                mViewModel.onEvent(ScreenEvent.OnSearch(p0.toString()))
                return true
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                if (p0!=null){
                    if (p0.isEmpty()){
                        mViewModel.onEvent(ScreenEvent.OnShowGameList)
                    }

                }
                return true
            }
        })

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}
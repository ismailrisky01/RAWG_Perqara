package com.ismaildev.rawg_perqara.view.fragment

import android.opengl.Visibility
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.startup.AppInitializer
import com.ismaildev.rawg_perqara.databinding.FragmentFavoriteBinding
import com.ismaildev.rawg_perqara.di.KoinInit
import com.ismaildev.rawg_perqara.presentation.event.ScreenEvent
import com.ismaildev.rawg_perqara.presentation.state.ScreenState
import com.ismaildev.rawg_perqara.view.adapter.FavouriteAdapter
import com.ismaildev.rawg_perqara.view.adapter.HomeAdapter
import com.ismaildev.rawg_perqara.viewmodel.FavoriteViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.Koin
import org.koin.core.component.KoinScopeComponent
import org.koin.core.component.createScope
import org.koin.core.scope.Scope


class FavoriteFragment : Fragment(), KoinScopeComponent {


    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding!!

    override val scope: Scope by lazy {
        createScope(this)
    }
    private val myKoin by lazy {
        AppInitializer.getInstance(requireContext())
            .initializeComponent(KoinInit::class.java)
    }

    override fun getKoin(): Koin = myKoin
    private val mViewModel: FavoriteViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentFavoriteBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mViewModel.onEvent(ScreenEvent.OnShowFavorite)
        CoroutineScope(Dispatchers.Main).launch {
            mViewModel.state.collect {
                when (it) {
                    is ScreenState.OnShowFavorite -> {
                        if (it.data.isEmpty()){
                            binding.nullData.visibility=View.VISIBLE
                            return@collect
                        }
                        binding.nullData.visibility=View.GONE
                        val adapter = FavouriteAdapter()
                        binding.favoriteRecyclerview.layoutManager =
                            LinearLayoutManager(requireContext())
                        binding.favoriteRecyclerview.adapter = adapter
                        adapter.setData(it.data, requireContext())
                        binding.favoriteRecyclerview.visibility = View.VISIBLE
                    }
                    is ScreenState.OnShowLoading -> {
                        binding.favoriteRecyclerview.visibility = View.GONE
                    }
                    else -> {

                    }
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}
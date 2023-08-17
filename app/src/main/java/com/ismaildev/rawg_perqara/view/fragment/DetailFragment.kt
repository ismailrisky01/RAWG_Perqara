package com.ismaildev.rawg_perqara.view.fragment

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.startup.AppInitializer
import com.bumptech.glide.Glide
import com.ismaildev.rawg_perqara.R
import com.ismaildev.rawg_perqara.data.model.ModelGame
import com.ismaildev.rawg_perqara.databinding.FragmentDetailBinding
import com.ismaildev.rawg_perqara.presentation.state.ScreenState
import com.ismaildev.rawg_perqara.viewmodel.HomeViewModel
import com.ismaildev.rawg_perqara.di.KoinInit
import com.ismaildev.rawg_perqara.presentation.event.ScreenEvent
import com.ismaildev.rawg_perqara.util.myLog
import com.ismaildev.rawg_perqara.viewmodel.DetailViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.Koin
import org.koin.core.component.KoinScopeComponent
import org.koin.core.component.createScope
import org.koin.core.scope.Scope

class DetailFragment : Fragment(), KoinScopeComponent {
    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!

    override val scope: Scope by lazy {
        createScope(this)
    }
    private val myKoin by lazy {
        AppInitializer.getInstance(requireContext())
            .initializeComponent(KoinInit::class.java)
    }

    override fun getKoin(): Koin = myKoin
    private val mViewModel: DetailViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentDetailBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mViewModel.onEvent(ScreenEvent.OnShowGameDetail((arguments?.getInt("id")!!)))
        CoroutineScope(Dispatchers.Main).launch {
            mViewModel.state.collect { it ->

                when (it) {
                    is ScreenState.OnShowDetailGame -> {
                        binding.containerContent.visibility = View.VISIBLE
                        binding.banner.visibility = View.VISIBLE
                        binding.shimmer.visibility = View.GONE
                        binding.favoriteDetail.visibility = View.VISIBLE
                        binding.titleDetail.text = it.data.name
                        binding.releaseDetail.text = "Release date " + it.data.released
                        binding.rating.text = it.data.rating.toString()
                        binding.played.text = it.data.playtime.toString() + " played"


                        Glide.with(requireActivity()).load(it.data.backgroundImage)
                            .into(binding.banner)

                        val htmlData =
                            "<html> <head><meta name=\"viewport\" content=\"width=device-width, user-scalable=0,initial-scale=1.0\"></head><body style=\"padding:5;margin:0;color:#515F65;font-family: verdana;text-align:justify; overflow-wrap: break-word; " +
                                    "word-wrap: break-word; -ms-word-break: break-all; word-break: break-all; word-break: break-word; -ms-hyphens: auto; -moz-hyphens: auto; -webkit-hyphens: auto; hyphens: auto; \">" +
                                    it.data.description + "</body></html>"
                        binding.detail.loadDataWithBaseURL(
                            "http://nada", htmlData,
                            "text/html", "utf-8", ""
                        )
                        binding.detail.setBackgroundColor(Color.TRANSPARENT)

                        var genres=""
                        if (it.data.genres!!.isNotEmpty()){
                            it.data.genres.forEach {genre->
                                if (it.data.genres.last()?.id == genre?.id){
                                    genres += genre!!.name
                                }else{
                                    genres += genre!!.name+", "

                                }
                            }
                        }
                        binding.genre.text = genres

                    }
                    is ScreenState.OnShowLoading -> {
                        binding.containerContent.visibility = View.GONE
                        binding.banner.visibility = View.GONE
                        binding.shimmer.visibility = View.VISIBLE
                        binding.favoriteDetail.visibility = View.INVISIBLE

                    }
                    is ScreenState.OnChangeFavoriteIcon -> {
                        if (it.isChange){
                            binding.favoriteDetail.setImageResource(R.drawable.ic_baseline_favorite_red)

                        }else{
                            binding.favoriteDetail.setImageResource(R.drawable.ic_baseline_favorite_24)


                        }
                        myLog("Change Color")
                    }
                    else -> {

                    }
                }
            }
        }
        binding.back.setOnClickListener { findNavController().popBackStack() }
        binding.favoriteDetail.setOnClickListener {
            myLog("Favorite Click")
            CoroutineScope(Dispatchers.Main).launch {
                mViewModel.onEvent(ScreenEvent.OnAddFavourite)
            }
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}
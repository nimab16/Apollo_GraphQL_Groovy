package com.example.apollographql.ui.fragments.splash

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.airbnb.lottie.LottieDrawable
import com.example.apollographql.R
import com.example.apollographql.databinding.FragmentSplashBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashFragment : Fragment() {
    private var _binding: FragmentSplashBinding? = null
    private val binding get() = _binding!!
    private val viewModel: SplashViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSplashBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupAnimation()
        observeSplashState()
    }

    private fun setupAnimation() {
        binding.animationView.apply {
            setAnimation(R.raw.globe_animation)
            repeatCount = LottieDrawable.INFINITE
            playAnimation()
        }
    }

    private fun observeSplashState() {
        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            viewModel.isSplashFinished.collect { isFinished ->
                if (isFinished) {
                    binding.animationView.cancelAnimation()
                    findNavController().navigate(R.id.action_splashFragment_to_countriesListFragment)
                }
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.animationView.cancelAnimation()
        _binding = null
    }
} 
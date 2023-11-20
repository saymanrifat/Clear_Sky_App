package com.muratozturk.openai_dall_e_2.presentation.generate_image


import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.muratozturk.click_shrink_effect.applyClickShrink
import com.muratozturk.openai_dall_e_2.R
import com.muratozturk.openai_dall_e_2.common.*
import com.muratozturk.openai_dall_e_2.databinding.FragmentGenerateImageBinding
import com.zhuinden.fragmentviewbindingdelegatekt.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import www.sanju.motiontoast.MotionToast
import www.sanju.motiontoast.MotionToastStyle

@AndroidEntryPoint
class GenerateImageFragment : Fragment(R.layout.fragment_generate_image) {

    private val viewModel: GenerateImageViewModel by viewModels()
    private val binding by viewBinding(FragmentGenerateImageBinding::bind)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewCollect()
    }

    private fun initViewCollect() {
        with(viewModel) {
            with(binding) {
                generateButton.setOnClickListener {

                    if (promptEditText.text.toString().isEmpty().not()) {
                        val imageSize = if (size256.isChecked) {
                            Sizes.SIZE_256
                        } else if (size512.isChecked) {
                            Sizes.SIZE_256
                        } else {
                            Sizes.SIZE_256
                        }
                        generateImage(promptEditText.text.toString(), 4, imageSize)
                    } else {
                        promptInputLayout.error = getString(R.string.enter_prompt)
                    }


                }
                generatedImageCard.applyClickShrink()

                viewLifecycleOwner.lifecycleScope.launchWhenStarted {
                    state.collect { response ->
                        when (response) {
                            is Resource.Loading -> {
                                generateButton.startAnimation()
                                shimmerLayout.apply {
                                    startShimmer()
                                    visible()
                                }
                                generatedImagesGrid.gone()
                            }
                            is Resource.Success -> {
                                shimmerLayout.apply {
                                    stopShimmer()
                                    gone()
                                }
                                generatedImagesGrid.visible()

                                generateButton.revertAnimation {
                                    generateButton.setBackgroundResource(R.drawable.rounded_bg3)
                                }

                                generatedImageView.glideImage(response.data.data[0].url)


                                generatedImageCard.setOnClickListener {
                                    showImageFullPage(response.data.data[0].url)
                                }

                            }
                            is Resource.Error -> {
                                shimmerLayout.apply {
                                    stopShimmer()
                                    gone()
                                }
                                generatedImagesGrid.gone()

                                generateButton.revertAnimation {
                                    generateButton.setBackgroundResource(R.drawable.rounded_bg3)
                                }

                                MotionToast.createColorToast(
                                    requireActivity(),
                                    getString(R.string.error),
                                    response.throwable.localizedMessage ?: "Error",
                                    MotionToastStyle.ERROR,
                                    MotionToast.GRAVITY_TOP or MotionToast.GRAVITY_CENTER,
                                    MotionToast.LONG_DURATION,
                                    null
                                )

                                Log.e("Response", response.throwable.localizedMessage ?: "Error")
                            }
                            else -> {}
                        }
                    }
                }
            }

        }
    }

    private fun showImageFullPage(imageUrl: String) {
        findNavController().navigate(
            GenerateImageFragmentDirections.actionGenerateImageFragmentToImageDetailFragment(
                imageUrl
            )
        )
    }
}
package com.github.emmpann.first_question.ui.secondpage

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.github.emmpann.first_question.R
import com.github.emmpann.first_question.databinding.ActivitySecondBinding
import com.github.emmpann.first_question.ui.firstpage.MainActivity
import com.github.emmpann.first_question.ui.thirdpage.ThirdActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SecondActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySecondBinding
    private val viewModel: SecondViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySecondBinding.inflate(layoutInflater)
        setContentView(binding.root)

        WindowCompat.setDecorFitsSystemWindows(
            window,
            false
        )

        setupClickListener()
        setupView()
        setupObserver()
    }

    private fun setupObserver() {
        viewModel.currentName.observe(this) {
            binding.tvName.text = it
        }

        viewModel.selectedName.observe(this) {
            binding.tvSelectedUser.text = it
            binding.stepInformation.visibility = View.GONE
        }

        viewModel.selectedAvatar.observe(this) {
            Glide.with(this)
                .load(it)
                .transition(DrawableTransitionOptions.withCrossFade())
                .circleCrop()
                .into(binding.ivPhoto)
        }
    }

    private fun setupView() {
        val currentName = intent.getStringExtra(MainActivity.USERNAME)
        if (currentName != null) viewModel.setCurrentName(currentName)
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        data?.apply {
            getStringExtra(ThirdActivity.SELECTED_USERNAME)?.let { viewModel.setSelectedName(it) }
            getStringExtra(ThirdActivity.SELECTED_AVATAR)?.let { viewModel.setSelectedAvatar(it) }
        }

//        val selectedName = data?.getStringExtra(ThirdActivity.SELECTED_USERNAME)
//        if (selectedName != null) viewModel.setSelectedName(selectedName)
//
//        val selectedAvatar = data?.getStringExtra(ThirdActivity.SELECTED_AVATAR)
//        if (selectedAvatar != null) viewModel.setSelectedAvatar(selectedAvatar)
    }

    private fun setupClickListener() {
        binding.topAppBar.setNavigationOnClickListener {
            finish()
        }

        binding.btnChooseUser.setOnClickListener {
            val thirdPage = Intent(this, ThirdActivity::class.java)
            startActivityForResult(thirdPage, 10)
        }
    }
}
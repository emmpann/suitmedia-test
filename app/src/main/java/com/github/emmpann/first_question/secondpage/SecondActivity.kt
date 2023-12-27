package com.github.emmpann.first_question.secondpage

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.github.emmpann.first_question.databinding.ActivitySecondBinding
import com.github.emmpann.first_question.firstpage.MainActivity
import com.github.emmpann.first_question.thirdpage.ThirdActivity
import com.github.emmpann.first_question.thirdpage.ThirdViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SecondActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySecondBinding
    private val viewModel: SecondViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySecondBinding.inflate(layoutInflater)
        setContentView(binding.root)

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
        }
    }

    private fun setupView() {
        val currentName = intent.getStringExtra(MainActivity.USERNAME)
        if (currentName != null) viewModel.setCurrentName(currentName)
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val selectedName = data?.getStringExtra(ThirdActivity.SELECTED_USERNAME)
        if (selectedName != null) viewModel.setSelectedName(selectedName)
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
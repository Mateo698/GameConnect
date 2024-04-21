package com.gameconnect

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.gameconnect.databinding.ActivityRegisterBinding
import com.gameconnect.registerFragments.BioFragment
import com.gameconnect.registerFragments.GamesFragment
import com.gameconnect.registerFragments.GenreFragment
import com.gameconnect.registerFragments.PlatformsFragment
import com.gameconnect.registerFragments.TagsFragment
import com.gameconnect.registerFragments.TimeFragment
import com.gameconnect.registerFragments.UsernameFragment
import com.gameconnect.viewmodel.RegisterViewModel

class RegisterActivity : AppCompatActivity() {
    private val viewModel:RegisterViewModel by viewModels()

    private val binding by lazy {
        ActivityRegisterBinding.inflate(layoutInflater)
    }

    private var fragments = arrayOf(
        UsernameFragment.newInstance(),
        GenreFragment.newInstance(),
        GamesFragment.newInstance(),
        PlatformsFragment.newInstance(),
        BioFragment.newInstance(),
        TimeFragment.newInstance(),
        TagsFragment.newInstance()
    )


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        viewModel.page.observe(this) {
            showFragment(fragments[it])
            binding.progressBar.progress = (it + 1) * 100 / fragments.size

            if (it == 0) {
                binding.backBtn.isEnabled = false
            } else {
                binding.backBtn.isEnabled = true
            }

            if (it == fragments.size - 1) {
                binding.nextBtn.text = "Finish"
            } else {
                binding.nextBtn.text = "Next"
            }


        }

        binding.nextBtn.setOnClickListener {
            viewModel.nextPage()
        }

        binding.backBtn.setOnClickListener {
            viewModel.previousPage()
        }

    }

    fun showFragment(fragment: Fragment){
        supportFragmentManager.beginTransaction()
            .replace(binding.fragmentContainer.id, fragment)
            .commit()
    }
}
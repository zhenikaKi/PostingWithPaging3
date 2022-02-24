package ru.kirea.postingwithpaging3

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.paging.ExperimentalPagingApi
import ru.kirea.postingwithpaging3.databinding.MainActivityBinding
import ru.kirea.postingwithpaging3.windows.main.MainFragment

@ExperimentalPagingApi
class MainActivity : AppCompatActivity() {

    private val binding: MainActivityBinding by lazy { MainActivityBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        if (savedInstanceState == null) {
            val fragmentTransaction = supportFragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.container, MainFragment())
            fragmentTransaction.commit()
        }
    }
}
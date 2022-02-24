package ru.kirea.postingwithpaging3.windows.main

import androidx.lifecycle.lifecycleScope
import androidx.paging.ExperimentalPagingApi
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.launch
import org.koin.core.qualifier.named
import org.koin.java.KoinJavaComponent
import ru.kirea.postingwithpaging3.base.BaseFragment
import ru.kirea.postingwithpaging3.databinding.MainFragmentBinding
import ru.kirea.postingwithpaging3.di.Scopes
import ru.kirea.postingwithpaging3.windows.main.adapter.MainAdapter

@ExperimentalPagingApi
class MainFragment: BaseFragment<MainFragmentBinding>(MainFragmentBinding::inflate) {

    private val scope = KoinJavaComponent.getKoin().createScope<MainFragment>()
    private val viewModel: MainViewModel = scope.get(qualifier = named(Scopes.MAIN_VIEW_MODEL))
    private val adapter = MainAdapter()

    override fun afterOnCreateView() {
        viewModel.getPostsLiveData().observe(viewLifecycleOwner) {
            lifecycleScope.launch {
                adapter.submitData(it)
            }
        }

        initViews()
    }

    /** Инициализация view на экране */
    private fun initViews() {
        binding?.let {
            it.recyclerview.adapter = adapter
            it.recyclerview.layoutManager = LinearLayoutManager(requireContext())
            it.recyclerview.itemAnimator = DefaultItemAnimator()
            it.recyclerview.addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
        }
    }
}
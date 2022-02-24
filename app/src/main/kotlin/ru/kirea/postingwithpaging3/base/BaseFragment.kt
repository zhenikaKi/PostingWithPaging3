package ru.kirea.postingwithpaging3.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

abstract class BaseFragment<VB: ViewBinding> (private val inflate: InflateFragment<VB>): Fragment() {

    private var _binding: VB? = null
    protected val binding
        get() = _binding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = inflate.invoke(inflater, container, false)
        afterOnCreateView()
        return binding?.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    /** Метод, вызываемый при создании фрагмента */
    open fun afterOnCreateView() { }
}
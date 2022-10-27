package uz.xushnudbek.pixelswallpaper.ui.home

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import uz.xushnudbek.pixelswallpaper.R
import uz.xushnudbek.pixelswallpaper.databinding.FragmentHomeBinding
import uz.xushnudbek.pixelswallpaper.databinding.ItemTabBinding
import uz.xushnudbek.pixelswallpaper.ui.global.baseFragment.BaseFragment
import uz.xushnudbek.pixelswallpaper.ui.viewpager.ViewPagerAdapter

class HomeFragment : BaseFragment(R.layout.fragment_home) {

    private var _bn: FragmentHomeBinding? = null
    private val bn get() = _bn!!
    private lateinit var viewPagerAdapter: ViewPagerAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        _bn = FragmentHomeBinding.bind(view)
        setUpUI()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _bn = FragmentHomeBinding.inflate(inflater, container, false)
        setUpUI()
        return bn.root
    }

    override fun setUpUI() {
        val list:List<String> = listOf("All", "Nature", "Technology", "Animals")
        viewPagerAdapter = ViewPagerAdapter(lifecycle, childFragmentManager, list)
        bn.viewPager2.adapter = viewPagerAdapter
        TabLayoutMediator(bn.tabLayout, bn.viewPager2) { tab: TabLayout.Tab, position: Int ->
            val bindingItem: ItemTabBinding = ItemTabBinding.inflate(LayoutInflater.from(context), null, false)
            bindingItem.tabTitle.text = list[position]
            bindingItem.tabImage.setImageResource(R.drawable.indicator)
            if (position == 0) {
                bindingItem.tabTitle.setTextColor(Color.WHITE)
            } else {
                bindingItem.tabTitle.setTextColor(Color.parseColor("#8A8A8A"))
                bindingItem.tabImage.visibility = View.INVISIBLE
            }
            tab.customView = bindingItem.root
            bn.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                override fun onTabSelected(tab: TabLayout.Tab) {
                    val binding: ItemTabBinding = ItemTabBinding.bind(tab.customView!!)
                    binding.tabTitle.setTextColor(Color.WHITE)
                    binding.tabImage.visibility = View.VISIBLE
                }

                override fun onTabUnselected(tab: TabLayout.Tab) {
                    val binding: ItemTabBinding = tab.customView?.let { ItemTabBinding.bind(it) }!!
                    binding.tabTitle.setTextColor(Color.parseColor("#8A8A8A"))
                    binding.tabImage.visibility = View.INVISIBLE
                }

                override fun onTabReselected(tab: TabLayout.Tab) {
                    val binding: ItemTabBinding = ItemTabBinding.bind(tab.customView!!)
                    binding.tabTitle.setTextColor(Color.WHITE)
                    binding.tabImage.visibility = View.VISIBLE
                }
            })
        }.attach()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _bn = null
    }
}
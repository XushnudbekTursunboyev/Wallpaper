package uz.xushnudbek.pixelswallpaper.ui.viewpager

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

class ViewPagerAdapter(lifecycle: Lifecycle, fragment: FragmentManager, private var list:List<String>): FragmentStateAdapter(fragment,lifecycle) {
    override fun getItemCount(): Int = list.size

    override fun createFragment(position: Int): Fragment {
        return VPFragment.newInstance(list[position])
    }
}
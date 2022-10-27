package uz.xushnudbek.pixelswallpaper.ui.viewpager

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.google.gson.Gson
import uz.xushnudbek.pixelswallpaper.R
import uz.xushnudbek.pixelswallpaper.adapter.PhotoAdapter
import uz.xushnudbek.pixelswallpaper.databinding.DialogItemBinding
import uz.xushnudbek.pixelswallpaper.databinding.FragmentVPBinding
import uz.xushnudbek.pixelswallpaper.network.ApiClient
import uz.xushnudbek.pixelswallpaper.repository.PhotoRepository
import uz.xushnudbek.pixelswallpaper.utils.network.Status
import uz.xushnudbek.pixelswallpaper.viewmodel.PhotoViewModel
import uz.xushnudbek.pixelswallpaper.viewmodel.ViewModelFactory

private const val ARG_PARAM1 = "param1"

class VPFragment : Fragment() {
    private var param1: String? = null

    private lateinit var _bn:FragmentVPBinding
    private val bn get() = _bn
    private lateinit var mainViewModel: PhotoViewModel
    private val navController: NavController by lazy(LazyThreadSafetyMode.NONE) { NavHostFragment.findNavController(this) }
    private var photoAdapter = PhotoAdapter{
        navController.navigate(R.id.viewPhotoFragment, bundleOf("photo" to Gson().toJson(it)))
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)?:"All"
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _bn  = FragmentVPBinding.inflate(inflater, container, false)
        val fbRepository = PhotoRepository(ApiClient.apiService())

        mainViewModel = ViewModelProvider(requireActivity(), ViewModelFactory(fbRepository))[PhotoViewModel::class.java]
        Log.d("TAG", "Category: $param1")
        mainViewModel.getPhotos(param1.toString()).observe(requireActivity()){
            when(it.status){
                Status.SUCCESS -> {
                    bn.loadingView.visibility = View.INVISIBLE
                    photoAdapter.submitList(it.data?.photos)
                    bn.rv.adapter = photoAdapter
                    Log.d("TAG", "onCreateView: ${it.data?.photos}")
                }
                Status.LOADING -> {
                    bn.rv.adapter = null
                    bn.loadingView.visibility = View.VISIBLE
                }
                Status.ERROR -> {
                    Toast.makeText(requireContext(), "${it.message}", Toast.LENGTH_SHORT).show()
                    Log.e("TAG", "ErrorMessage: ${it.message}")
                }
            }
        }

        return bn.root
    }

    companion object {
       @JvmStatic
        fun newInstance(param1: String) =
            VPFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                }
            }
    }

//    override fun onDestroyView() {
//        super.onDestroyView()
//        bn.rv.adapter = null
//       // _bn = null
//    }
}
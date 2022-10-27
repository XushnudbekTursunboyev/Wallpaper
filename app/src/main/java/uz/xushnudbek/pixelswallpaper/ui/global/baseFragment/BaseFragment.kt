package uz.xushnudbek.pixelswallpaper.ui.global.baseFragment

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import uz.xushnudbek.pixelswallpaper.databinding.DialogItemBinding

abstract class BaseFragment(private val layoutId:Int) : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(layoutId, container, false).apply {

        }
    }

    protected fun showLoadingMessage(string: String, context: Context){
        var dialog = AlertDialog.Builder(context).create()
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val dialogView = DialogItemBinding.inflate(LayoutInflater.from(context))
        dialog.setView(dialogView.root)
        dialog.show()
    }

    protected abstract fun setUpUI()
}
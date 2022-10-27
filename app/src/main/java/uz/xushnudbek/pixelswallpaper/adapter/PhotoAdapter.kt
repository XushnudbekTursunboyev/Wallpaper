package uz.xushnudbek.pixelswallpaper.adapter

import android.graphics.drawable.BitmapDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import uz.xushnudbek.pixelswallpaper.databinding.ItemRvBinding
import uz.xushnudbek.pixelswallpaper.network.models.Photo
import java.lang.Exception


/**
 *Created by Xushnudbek Tursunboyev on 09/10/2022
 */

class PhotoAdapter(val onClick: (model: Photo) -> Unit) : ListAdapter<Photo, PhotoAdapter.ViewHolder>(ITEM_DIFF) {

    companion object {
        private val ITEM_DIFF = object : DiffUtil.ItemCallback<Photo>() {
            override fun areItemsTheSame(oldItem: Photo, newItem: Photo) = oldItem == newItem
            override fun areContentsTheSame(oldItem: Photo, newItem: Photo): Boolean =
                oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemRvBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(getItem(position))

    inner class ViewHolder(private val binding: ItemRvBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(d: Photo) {
            binding.apply {
                root.setOnClickListener {
                    onClick.invoke(d)

                }

                binding.apply {
                    Picasso.get().load(d.src.small).into(binding.imgRV, object : Callback {
                        override fun onSuccess() {
                            binding.progress.visibility = View.INVISIBLE
                            binding.imgRV.visibility = View.VISIBLE
                            val bitmap = (binding.imgRV.drawable as BitmapDrawable).bitmap

                        }

                        override fun onError(e: Exception?) {

                        }

                    })

                }
            }
        }
    }
}
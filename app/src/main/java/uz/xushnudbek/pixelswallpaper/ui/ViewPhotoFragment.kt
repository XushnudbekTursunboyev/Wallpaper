package uz.xushnudbek.pixelswallpaper.ui

import android.content.ContentValues
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import com.google.gson.Gson
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import uz.xushnudbek.pixelswallpaper.R
import uz.xushnudbek.pixelswallpaper.databinding.FragmentViewPhotoBinding
import uz.xushnudbek.pixelswallpaper.network.models.Photo
import uz.xushnudbek.pixelswallpaper.ui.global.baseFragment.BaseFragment
import java.lang.Exception
import java.util.*

class ViewPhotoFragment:BaseFragment(R.layout.fragment_view_photo){

    private lateinit var _bn:FragmentViewPhotoBinding
    private val bn get() = _bn
    private lateinit var photo: Photo

    override fun onCreate(savedInstanceState: Bundle?) {
        arguments?.apply {
            photo = Gson().fromJson(getString("photo"), Photo::class.java)
        }
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        _bn = FragmentViewPhotoBinding.bind(view)
        setUpUI()
    }

    override fun setUpUI() {
        Picasso.get().load(photo.src.original).into(bn.imPhoto, object : Callback {
            override fun onSuccess() {
                bn.progress.visibility = View.INVISIBLE
                bn.imPhoto.visibility = View.VISIBLE
            }

            override fun onError(e: Exception?) {

            }

        })

        bn.btnDownload.setOnClickListener {
            saveToStorage()
        }
    }
    private fun saveToStorage() {
        val contentResolver = requireActivity().contentResolver
        val images = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            MediaStore.Images.Media.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY)
        } else {
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        }
        val contentValues = ContentValues()
        contentValues.put(
            MediaStore.Images.Media.DISPLAY_NAME,
            "${photo.alt}.jpg"
        )
        contentValues.put(MediaStore.Images.Media.MIME_TYPE, "images/*")
        val uri = contentResolver.insert(images, contentValues)

        try {
            val bitmapDrawable = bn.imPhoto.drawable as BitmapDrawable
            val bitmap = bitmapDrawable.bitmap

            val outputStream = Objects.requireNonNull(uri)
                ?.let { contentResolver.openOutputStream(it) }
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
            Objects.requireNonNull(outputStream)
            Toast.makeText(requireContext(), "Saved successfully", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            Toast.makeText(requireContext(), "Failed to save image", Toast.LENGTH_SHORT).show()
        }
    }

}
package com.jeffersongondran.home_care_housekeeping

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jeffersongondran.home_care_housekeeping.databinding.ItemPhotoBinding
import com.bumptech.glide.Glide

data class Photo(
    val uri: Uri,
    val id: String = System.currentTimeMillis().toString()
)

class PhotoAdapter(
    private val onPhotoRemove: (Photo) -> Unit
) : RecyclerView.Adapter<PhotoAdapter.PhotoViewHolder>() {

    private val photos = mutableListOf<Photo>()

    fun updatePhotos(newPhotos: List<Photo>) {
        photos.clear()
        photos.addAll(newPhotos)
        notifyDataSetChanged()
    }

    fun addPhoto(photo: Photo) {
        photos.add(photo)
        notifyItemInserted(photos.size - 1)
    }

    fun removePhoto(photo: Photo) {
        val position = photos.indexOf(photo)
        if (position != -1) {
            photos.removeAt(position)
            notifyItemRemoved(position)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        val binding = ItemPhotoBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return PhotoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        holder.bind(photos[position])
    }

    override fun getItemCount(): Int = photos.size

    inner class PhotoViewHolder(
        private val binding: ItemPhotoBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(photo: Photo) {
            // Load image using Glide
            Glide.with(binding.ivPhoto.context)
                .load(photo.uri)
                .centerCrop()
                .into(binding.ivPhoto)

            // Show remove button on hover/touch
            binding.root.setOnLongClickListener {
                binding.fabRemovePhoto.visibility = View.VISIBLE
                true
            }

            binding.fabRemovePhoto.setOnClickListener {
                onPhotoRemove(photo)
            }

            // Hide remove button when not needed
            binding.root.setOnClickListener {
                binding.fabRemovePhoto.visibility = View.GONE
            }
        }
    }
}

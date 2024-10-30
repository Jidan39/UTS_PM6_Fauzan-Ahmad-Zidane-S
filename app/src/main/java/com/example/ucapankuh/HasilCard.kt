package com.example.ucapankuh

import android.Manifest
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import java.io.IOException

class HasilCard : AppCompatActivity() {

    companion object {
        const val REQUEST_STORAGE_PERMISSION = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hasil_card)

        // Ambil data dari Intent
        val kalimat2 = intent.getStringExtra("KALIMAT2")
        val assetId = intent.getIntExtra("ASSET_ID", -1)

        // Temukan komponen dalam layout
        val textViewUcapan: TextView = findViewById(R.id.textView3)
        val imageViewAsset: ImageView = findViewById(R.id.card_picture)
        val buttonDownload: Button = findViewById(R.id.button_download)

        // Set teks dan gambar berdasarkan data yang dikirim dari halaman pertama
        textViewUcapan.text = kalimat2
        if (assetId != -1) {
            imageViewAsset.setImageResource(assetId)
        }

        // Fungsi untuk kembali ke MainActivity
        val backIcon: ImageView = findViewById(R.id.back_icon)
        backIcon.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish() // Menutup aktivitas HasilCard agar tidak kembali ketika menekan tombol back
        }

        // Fungsi untuk menyimpan kartu sebagai gambar
        buttonDownload.setOnClickListener {
            // Memeriksa izin penyimpanan
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,
                    arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                    REQUEST_STORAGE_PERMISSION)
            } else {
                saveCardImage()
            }
        }
    }

    // Fungsi untuk menyimpan gambar ke galeri
    private fun saveCardImage() {
        val linearLayout = findViewById<LinearLayout>(R.id.kartu_ucapan_hasil) // Mengambil LinearLayout
        val bitmap = getBitmapFromView(linearLayout) // Mengambil bitmap dari LinearLayout
        if (bitmap != null) {
            saveBitmapToGallery(bitmap)
        } else {
            Toast.makeText(this, "Gagal mengambil gambar", Toast.LENGTH_SHORT).show()
        }
    }

    // Fungsi untuk membuat Bitmap dari tampilan
    private fun getBitmapFromView(view: View): Bitmap {
        val bitmap = Bitmap.createBitmap(view.width, view.height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        view.draw(canvas)
        return bitmap
    }

    // Fungsi untuk menyimpan gambar ke galeri
    private fun saveBitmapToGallery(bitmap: Bitmap) {
        val contentValues = ContentValues().apply {
            put(MediaStore.Images.Media.DISPLAY_NAME, "KartuUcapan_${System.currentTimeMillis()}.png")
            put(MediaStore.Images.Media.MIME_TYPE, "image/png")
            put(MediaStore.Images.Media.RELATIVE_PATH, Environment.DIRECTORY_PICTURES)
        }

        val uri = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
        if (uri != null) {
            try {
                contentResolver.openOutputStream(uri)?.use { outputStream ->
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
                    Toast.makeText(this, "Kartu ucapan berhasil disimpan", Toast.LENGTH_SHORT).show()
                }
            } catch (e: IOException) {
                Toast.makeText(this, "Gagal menyimpan kartu ucapan", Toast.LENGTH_SHORT).show()
                e.printStackTrace()
            }
        } else {
            Toast.makeText(this, "Gagal membuat URI untuk menyimpan gambar", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_STORAGE_PERMISSION) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                saveCardImage() // simpan gambar klo izin diberikan
            } else {
                Toast.makeText(this, "Izin penyimpanan ditolak", Toast.LENGTH_SHORT).show()
            }
        }
    }
}

package com.example.ucapankuh

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.ucapankuh.R

class SecondActivity : AppCompatActivity() {

    private lateinit var textViewKalimat: TextView
    private lateinit var imageViewAsset: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hasil_card)

        textViewKalimat = findViewById(R.id.textView3)
        imageViewAsset = findViewById(R.id.imageView)

        // Menerima data dari MainActivity
        val kalimat1 = intent.getStringExtra("KALIMAT1") ?: ""
        val kalimat2 = intent.getStringExtra("KALIMAT2") ?: ""
        val assetId = intent.getIntExtra("ASSET_ID", R.drawable.asset1) // Gambar default jika tidak ada yang dipilih

        // Menampilkan kalimat dan aset gambar
        textViewKalimat.text = "$kalimat1\n\n$kalimat2" // Menampilkan kedua kalimat
        imageViewAsset.setImageResource(assetId)

        // Tombol kembali
        val backButton: ImageView = findViewById(R.id.imageView)
        backButton.setOnClickListener {
            onBackPressed() // Mengembalikan ke MainActivity
        }

        // Tombol download
        val downloadButton: Button = findViewById(R.id.button)
        downloadButton.setOnClickListener {
            // Logika untuk mengunduh kartu (tidak ada implementasi detail, bisa menggunakan library atau metode lain)
            // Misalnya, Anda bisa menggunakan Intent untuk berbagi gambar
            shareCard()
        }
    }

    private fun shareCard() {
        // Logika untuk membagikan kartu
        // Bisa diimplementasikan menggunakan Intent untuk berbagi gambar atau teks
        // Contoh: Menggunakan FileProvider untuk membagikan gambar jika Anda menyimpannya ke penyimpanan lokal
    }
}

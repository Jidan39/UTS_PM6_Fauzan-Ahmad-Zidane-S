package com.example.ucapankuh

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var editTextKalimat2: EditText
    private var selectedAsset: Int? = null // Untuk menyimpan ID aset yang dipilih

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        editTextKalimat2 = findViewById(R.id.editTextTextMultiLine2)

        // Menyimpan ID setiap aset drawable
        val assets = listOf(
            R.drawable.asset1, R.drawable.asset2, R.drawable.asset3, R.drawable.asset4,
            R.drawable.asset5, R.drawable.asset6, R.drawable.asset7, R.drawable.asset8
        )

        // Set onClickListener untuk setiap aset
        assets.forEachIndexed { index, assetResId ->
            val assetImageViewId = resources.getIdentifier("asset${index + 1}", "id", packageName)
            findViewById<ImageView>(assetImageViewId).setOnClickListener {
                selectedAsset = assetResId // Menyimpan ID aset drawable yang dipilih
                Toast.makeText(this, "Aset gambar dipilih", Toast.LENGTH_SHORT).show()
            }
        }

        // Tombol untuk membuat kartu ucapan
        val buttonBuatKartu: Button = findViewById(R.id.button)
        buttonBuatKartu.setOnClickListener {
            val kalimat2 = editTextKalimat2.text.toString()

            if (selectedAsset != null) {
                // Mengirim data ke halaman kedua
                val intent = Intent(this, HasilCard::class.java).apply {
                    putExtra("KALIMAT2", kalimat2)
                    putExtra("ASSET_ID", selectedAsset)
                }
                startActivity(intent)
            } else {
                Toast.makeText(this, "Pilih gambar terlebih dahulu!", Toast.LENGTH_SHORT).show()
            }
        }
    }
}

package com.example.ucapankuh

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.ucapankuh.R
import com.example.ucapankuh.HasilCard

class MainActivity : AppCompatActivity() {

    private lateinit var editTextKalimat1: EditText
    private lateinit var editTextKalimat2: EditText
    private var selectedAsset: Int? = null // Untuk menyimpan ID aset yang dipilih

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        editTextKalimat1 = findViewById(R.id.editTextTextMultiLine)
        editTextKalimat2 = findViewById(R.id.editTextTextMultiLine2)

        // Menyimpan ID setiap aset ImageView
        val assets = listOf(
            R.id.asset1, R.id.asset2, R.id.asset3, R.id.asset4,
            R.id.asset5, R.id.asset6, R.id.asset7, R.id.asset8
        )

        // Set onClickListener untuk setiap aset
        assets.forEach { assetId ->
            findViewById<ImageView>(assetId).setOnClickListener {
                selectedAsset = assetId // Menyimpan ID aset yang dipilih
                Toast.makeText(this, "Aset gambar dipilih", Toast.LENGTH_SHORT).show()
            }
        }

        // Tombol untuk membuat kartu ucapan
        val buttonBuatKartu: Button = findViewById(R.id.button)
        buttonBuatKartu.setOnClickListener {
            val kalimat1 = editTextKalimat1.text.toString()
            val kalimat2 = editTextKalimat2.text.toString()

            if (selectedAsset != null) {
                // Mengirim data ke halaman kedua
                val intent = Intent(this, HasilCard::class.java).apply {
                    putExtra("KALIMAT1", kalimat1)
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

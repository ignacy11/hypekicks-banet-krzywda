package com.example.hypekicks

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.example.hypekicks.databinding.ActivitySneakerDetailsBinding

class SneakerDetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySneakerDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivitySneakerDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val daSneaker = if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
            intent.getSerializableExtra("SNEAKER_DATA", Sneaker::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getSerializableExtra("SNEAKER_DATA") as? Sneaker
        }
        if(daSneaker != null) {
            binding.detailModelNameTextView.text = daSneaker.modelName
            binding.detailBrandNameTextView.text = daSneaker.brand
            binding.detailReleaseYearTextView.text = "Rok wydania: ${daSneaker.releaseYear}"
            binding.detailReSellTextView.text = "Odsprzedaż: ${daSneaker.resellPrice} zł"

            Glide.with(this)
                .load(daSneaker.imageUrl)
                .placeholder(R.mipmap.ic_launcher)
                .into(binding.detailImageView)

            binding.backButton.setOnClickListener {
                finish()
            }
        } else {
            Toast.makeText(
                this,
                "Błąd władydowywania (albo coś takiego) buta. ZJEBAŁEŚ!!1!?!",
                Toast.LENGTH_LONG).show()
            finish()
        }
    }
}
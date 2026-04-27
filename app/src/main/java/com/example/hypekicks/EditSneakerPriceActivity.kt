package com.example.hypekicks

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.example.hypekicks.databinding.ActivityEditSneakerPriceBinding
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore

@Suppress("DEPRECATION")
class EditSneakerPriceActivity : AppCompatActivity() {
    lateinit var binding: ActivityEditSneakerPriceBinding

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityEditSneakerPriceBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val db = Firebase.firestore

        val sneaker = intent.getSerializableExtra("sneaker") as Sneaker
        val sneakerModel = sneaker.modelName
        val sneakerBrand = sneaker.brand
        val sneakerReleaseYear = sneaker.releaseYear
        val oldSneakerPrice = sneaker.resellPrice
        val sneakerImageUrl = sneaker.imageUrl

        binding.sneakerModelNameTextView.text = sneakerModel
        binding.sneakerBrandTextView.text = sneakerBrand
        binding.sneakerReleaseYearTextView.text = "Rok: $sneakerReleaseYear"
        binding.sneakerResellPriceTextView.text = "Cena: $oldSneakerPrice zł"
        Glide.with(this)
            .load(sneakerImageUrl)
            .placeholder(R.mipmap.ic_launcher)
            .into(binding.sneakerImageView)


        binding.confirmPriceChangeButton.setOnClickListener { _ ->
            val newPriceText = binding.newSneakerPriceEditText.text.toString().trim()
            if(newPriceText.isEmpty()) {
                binding.newSneakerPriceEditText.error = "Pole wymagane"
            } else {
                val newPrice = newPriceText.toInt()
                db.collection("sneakers")
                    .whereEqualTo("brand", sneaker.brand)
                    .whereEqualTo("modelName", sneaker.modelName)
                    .get()
                    .addOnSuccessListener { querySnapshot ->
                        if (!querySnapshot.isEmpty) {
                            val documentId = querySnapshot.documents[0].id

                            db.collection("sneakers").document(documentId)
                                .update("resellPrice", newPrice)
                                .addOnSuccessListener {
                                    Toast.makeText(this, "Cena buta została zmieniona", Toast.LENGTH_SHORT).show()
                                    finish()
                                }
                                .addOnFailureListener {
                                    Toast.makeText(this, "Błąd podczas zmiany ceny buta", Toast.LENGTH_SHORT).show()
                                }
                        } else {
                            Toast.makeText(this, "Nie znaleziono buta w bazie", Toast.LENGTH_SHORT).show()
                        }
                    }
                    .addOnFailureListener {
                        Toast.makeText(this, "Błąd połączenia z bazą", Toast.LENGTH_SHORT).show()
                    }
            }
        }
    }
}

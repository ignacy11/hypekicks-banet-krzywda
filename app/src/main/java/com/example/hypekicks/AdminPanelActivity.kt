package com.example.hypekicks

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.hypekicks.databinding.ActivityAdminPanelBinding
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import java.util.ArrayList

@Suppress("DEPRECATION")
class AdminPanelActivity : AppCompatActivity() {
    lateinit var binding: ActivityAdminPanelBinding
    lateinit var sneakersList: MutableList<Sneaker>
    lateinit var sneakerAdapter: SneakerAdapter
    val sneakerRepository = SneakerRepository()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityAdminPanelBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        sneakersList = (intent.getSerializableExtra("sneakers") as? ArrayList<Sneaker>) ?: arrayListOf()
        sneakerAdapter = SneakerAdapter(this, sneakersList)
        binding.sneakersAdminListView.adapter = sneakerAdapter

        binding.addToDatabaseButton.setOnClickListener { _ ->
            val sneakerBrand = binding.sneakerBrandEditText.text.toString().trim()
            val sneakerModel = binding.sneakerModelEditText.text.toString().trim()
            val sneakerReleaseYear = binding.sneakerReleaseYearEditText.text.toString().trim()
            val sneakerPrice = binding.sneakerPriceEditText.text.toString().trim()

            if(sneakerBrand.isEmpty()) {
                binding.sneakerBrandEditText.error = "Pole wymagane"
            } else if(sneakerModel.isEmpty()) {
                binding.sneakerModelEditText.error = "Pole wymagane"
            } else if(sneakerReleaseYear.isEmpty()) {
                binding.sneakerReleaseYearEditText.error = "Pole wymagane"
            } else if(sneakerPrice.isEmpty()) {
                binding.sneakerPriceEditText.error = "Pole wymagane"
            } else {
                val newSneaker = Sneaker(
                    sneakerBrand,
                    "https://i.postimg.cc/k4zCwc6R/sneaker-placeholder.jpg",
                    sneakerModel,
                    sneakerReleaseYear.toInt(),
                    sneakerPrice.toInt()
                )
                sneakerRepository.addSneaker(
                    newSneaker,
                    onSuccess = {
                        Toast.makeText(this, "Dodano pomyślnie buta do bazy", Toast.LENGTH_SHORT).show()
                        fetchDataFromCloud()
                    },
                    onError = {
                        Toast.makeText(this, "Błąd podczas dodawania buta", Toast.LENGTH_SHORT).show()
                    }
                )
            }
        }
        binding.sneakersAdminListView.setOnItemClickListener { parent, view, position, id ->
            val clickedSneaker = sneakersList[position]


            val intent = Intent(this, EditSneakerPriceActivity::class.java)
            intent.putExtra("sneaker", clickedSneaker)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        fetchDataFromCloud()
    }

    fun fetchDataFromCloud() {
        sneakerRepository.fetchSneakers(
            onSuccess = { sneakers ->
                sneakersList.clear()
                sneakersList.addAll(sneakers)
                sneakerAdapter.notifyDataSetChanged()
            },
            onError = { exception ->
                Log.e("FIREBASE_ERROR", "Błąd pobierania danych: ", exception)
                Toast.makeText(this, "Błąd pobierania danych z chmury!", Toast.LENGTH_LONG).show()
            }
        )
    }
}

package com.example.hypekicks

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.hypekicks.databinding.ActivityMainBinding
import com.google.firebase.Firebase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import kotlin.text.clear

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var sneakersList: MutableList<Sneakers>
    lateinit var adapter: SneakerAdapter
    private lateinit var allSneakersList: MutableList<Sneakers>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        sneakersList = mutableListOf()

        adapter = SneakerAdapter(this, sneakersList)
        val db = FirebaseFirestore.getInstance()

        fetchDataFromCloud()
        binding.sneakersGridView.setOnItemClickListener {_, _, position, _ ->
        val clickedSneaker = sneakersList[position]
        Toast.makeText(this,"this will do something soon", Toast.LENGTH_SHORT).show()
        }


        binding.sneakersSearchview.setOnQueryTextListener(object: androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                filterSneakers(newText?: "")
                return true
            }

        })
    }
    private fun fetchDataFromCloud() {
        val db = FirebaseFirestore.getInstance()
        db.collection("sneakers")
            .get()
            .addOnSuccessListener { documents ->
                sneakersList.clear()

                for (document in documents) {
                    val brand = document.getString("brand")?: "Nieznana marka"
                    val imageUrl = document.getString("imageUrl")?: ""
                    val modelName = document.getString("modelName")?: "Nieznany model"
                    val releaseYear = document.getLong("releaseYear")?.toInt() ?: 0
                    val resellPrice = document.getLong("resellPrice")?.toInt() ?: 0

                    val sneaker = Sneakers(brand, imageUrl, modelName, releaseYear, resellPrice)
                    allSneakersList.add(sneaker)
                }
                sneakersList.clear()
                sneakersList.addAll(allSneakersList)
                adapter.notifyDataSetChanged()
            }
            .addOnFailureListener { exception ->
                Log.e("FIREBASE_ERROR", "Błąd pobierania danych: ", exception)
                Toast.makeText(this, "Błąd pobierania danych z chmury!", Toast.LENGTH_LONG).show()
            }
    }

    private fun filterSneakers(query: String) {
        sneakersList.clear()

        if (query.isEmpty()) {
            sneakersList.addAll(allSneakersList)
        } else {
            val lowerCaseQuery = query.lowercase()

            for (sneaker in allSneakersList) {
                if (sneaker.modelName.lowercase().contains(lowerCaseQuery)) {
                    sneakersList.add(sneaker)
                }
            }
        }
        adapter.notifyDataSetChanged()
    }
}
private fun seeDataBase() {
    val sneakersList = listOf(
        Sneakers(
            brand = "Nike",
            imageUrl = "https://i.postimg.cc/cCqcwPTk/nike-air-max.jpg",
            modelName = "Air Max 90",
            releaseYear = 2020,
            resellPrice = 200
        ),

        Sneakers(
            brand = "Adidas",
            imageUrl = "https://i.postimg.cc/x8rPMBgs/adidas-yeezy-boost.jpg",
            modelName = "Yeezy Boost 350 V2",
            releaseYear = 2022,
            resellPrice = 350
        ),
        Sneakers(
            brand = "Jordan",
            imageUrl = "https://i.postimg.cc/5ycmLDnc/air-jordan-1-retro.jpg",
            modelName = "Air Jordan 1 Retro",
            releaseYear = 2021,
            resellPrice = 450
        ),
        Sneakers(
            brand = "New Balance",
            imageUrl = "https://i.postimg.cc/j2VhPBv9/new-balance-550.jpg",
            modelName = "550",
            releaseYear = 2023,
            resellPrice = 180
        ),
    )
    Sneakers(
        brand = "Puma",
        imageUrl = "https://i.postimg.cc/WzR7gxXP/puma-rsx.jpg",
        modelName = "RS-X",
        releaseYear = 2023,
        resellPrice = 140
    )

    Sneakers(
        brand = "Converse",
        imageUrl = "https://i.postimg.cc/Y0cxYT8n/converse-chuck-70.jpg",
        modelName = "Chuck 70",
        releaseYear = 2013,
        resellPrice = 100
    )
    Sneakers(
        brand = "Reebok",
        imageUrl = "https://i.postimg.cc/5ycmLDpr/reebok-club-c-85.jpg",
        modelName = "Club C 85",
        releaseYear = 2021,
        resellPrice = 95
    )
    Sneakers(
        brand = "Asics",
        imageUrl = "https://i.postimg.cc/HnGtXF3q/asics-gel-kayano-14.jpg",
        modelName = "Gel-Kayano 14",
        releaseYear = 2022,
        resellPrice = 215
    )

    val db = Firebase.firestore

    for (sneaker in sneakersList) {
        db.collection("sneakers")
            .add(sneaker)
            .addOnSuccessListener {
                Log.d("FIREBASE_TEST", "Sukces! Dodano auto: ${sneaker.modelName}")
            }
            .addOnFailureListener { e ->
                Log.e("FIREBASE_TEST", "Błąd podczas dodawania: ", e)
            }
    }
}


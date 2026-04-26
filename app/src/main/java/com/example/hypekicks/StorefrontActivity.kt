package com.example.hypekicks

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.hypekicks.databinding.ActivityMainBinding


class StorefrontActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var sneakersList: MutableList<Sneaker>
    lateinit var sneakerAdapter: SneakerAdapter
    private lateinit var allSneakersList: MutableList<Sneaker>
    private val sneakerRepository = SneakerRepository()

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
        allSneakersList = mutableListOf()
        
        sneakerAdapter = SneakerAdapter(this, sneakersList)
        binding.sneakersGridView.adapter = sneakerAdapter


        binding.sneakersSearchView.setOnQueryTextListener(object: androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                filterSneakers(newText?: "")
                return true
            }
        })
        binding.adminPanelButton.setOnClickListener { _ ->
            val intent = Intent(this, AdminPanelActivity::class.java)
            intent.putExtra("sneakers",ArrayList(allSneakersList))
            startActivity(intent)
        }


        binding.sneakersGridView.setOnItemClickListener {_, _, position, _ ->
            val clickedSneaker = sneakersList[position]
            Toast.makeText(this,"clicked a sneaker", Toast.LENGTH_SHORT).show()
            // TODO – add and show DetailsActivity when a sneaker is clicked
        }

        fetchDataFromCloud()
    }

    fun fetchDataFromCloud() {
        sneakerRepository.fetchSneakers(
            onSuccess = { sneakers ->
                allSneakersList.clear()
                allSneakersList.addAll(sneakers)

                sneakersList.clear()
                sneakersList.addAll(allSneakersList)
                Log.d("sneakers-output", "$sneakersList")
                try {
                    sneakerAdapter.notifyDataSetChanged()
                } catch (err: Exception) {
                    Log.e("set-data-err", err.toString())
                }
            },
            onError = { exception ->
                Log.e("FIREBASE_ERROR", "Błąd pobierania danych: ", exception)
                Toast.makeText(this, "Błąd pobierania danych z chmury!", Toast.LENGTH_LONG).show()
            }
        )
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
        sneakerAdapter.notifyDataSetChanged()
    }
}
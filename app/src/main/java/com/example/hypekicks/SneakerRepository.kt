package com.example.hypekicks

import android.util.Log
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore

class SneakerRepository {
    private val db = Firebase.firestore

    fun fetchSneakers(
        onSuccess: (List<Sneaker>) -> Unit,
        onError: (Exception) -> Unit
    ) {
        db.collection("sneakers")
            .get()
            .addOnSuccessListener { documents ->
                val list = mutableListOf<Sneaker>()

                for (document in documents) {
                    val sneaker = Sneaker(
                        modelName = document.getString("modelName") ?: "Unknown",
                        brand = document.getString("brand") ?: "Unknown",
                        releaseYear = document.getLong("releaseYear")?.toInt() ?: 0,
                        resellPrice = document.getLong("resellPrice")?.toInt() ?: 0,
                        imageUrl = document.getString("imageUrl") ?: "",
                    )
                    list.add(sneaker)
                }
                onSuccess(list)
            }
            .addOnFailureListener {
                onError(it)
            }
    }
    fun addSneaker(
        sneaker: Sneaker,
        onSuccess: () -> Unit = {},
        onError: (Exception) -> Unit = {}
    ) {
        val db = Firebase.firestore

        val sneakerData = hashMapOf(
            "brand" to sneaker.brand,
            "imageUrl" to sneaker.imageUrl,
            "modelName" to sneaker.modelName,
            "releaseYear" to sneaker.releaseYear,
            "resellPrice" to sneaker.resellPrice
        )

        db.collection("sneakers")
            .add(sneakerData)
            .addOnSuccessListener {
                Log.d("FIREBASE", "Sneaker added successfully!")
                onSuccess()
            }
            .addOnFailureListener { e ->
                Log.e("FIREBASE", "Error adding sneaker:", e)
                onError(e)
            }
    }
}
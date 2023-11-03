package com.delvin.uywalkywalker.providers

import com.delvin.uywalkywalker.models.Walker
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class WalkerProvider {
    val db = Firebase.firestore.collection("Walkers")

    fun create(walker: Walker): Task<Void>{
        return db.document(walker.id!!).set(walker)
    }


}
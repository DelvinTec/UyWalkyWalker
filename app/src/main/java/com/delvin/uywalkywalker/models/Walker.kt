package com.delvin.uywalkywalker.models

import com.beust.klaxon.*

val klaxon = Klaxon()

data class Walker (
    val id: String? = null,
    val name: String? = null,
    val lastname: String? = null,
    val email: String? = null,
    val phone: String? = null,
    val image: String? = null,
    val age: String? = null,
    val sex: String? = null,
    val dni: String? = null
) {
    public fun toJson() = klaxon.toJsonString(this)

    companion object {
        public fun fromJson(json: String) = klaxon.parse<Walker>(json)
    }
}
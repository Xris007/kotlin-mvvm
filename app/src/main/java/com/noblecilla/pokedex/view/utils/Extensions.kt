package com.noblecilla.pokedex.view.utils

import android.content.Context
import android.widget.ImageView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import java.util.*

fun Context.toast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun ImageView.pokemonLoading(order: String) {
    Glide.with(this)
        .load("https://www.serebii.net/pokemongo/pokemon/${order}.png")
        .transform(CenterCrop())
        .into(this)
}

fun ImageView.typeLoading(type: String) {
    Glide.with(this)
        .load("https://www.serebii.net/pokedex-rs/type/${type.toLowerCase(Locale.ROOT)}.gif")
        .transform(CenterCrop())
        .into(this)
}

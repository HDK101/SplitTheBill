package br.scl.ifsp.splitthebill.model

import android.os.Parcelable
import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity
@Parcelize
data class Person(
    @PrimaryKey(autoGenerate = true) var id: Int?,
    @NonNull var name: String,
    @NonNull val spent: Double,
    @NonNull var toPay: Double
) : Parcelable
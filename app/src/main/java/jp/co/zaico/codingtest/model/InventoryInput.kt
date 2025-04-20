package jp.co.zaico.codingtest.model

import android.annotation.SuppressLint
import kotlinx.serialization.Serializable

@SuppressLint("UnsafeOptInUsageError")
@Serializable
data class InventoryInput(
    val title: String,
    val quantity: String? = null,
    val unit: String? = null,
    val category: String? = null,
    val place: String? = null,
    val state: String? = null,
    val code: String? = null,
 )
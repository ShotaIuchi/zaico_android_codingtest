package jp.co.zaico.codingtest.model

import android.annotation.SuppressLint
import kotlinx.serialization.Serializable

@SuppressLint("UnsafeOptInUsageError")
@Serializable
data class Inventory(
    val id: Int = -1,
    val title: String = "",
    val quantity: String? = null,
    val unit: String? = null,
    val category: String? = null,
    val state: String? = null
)

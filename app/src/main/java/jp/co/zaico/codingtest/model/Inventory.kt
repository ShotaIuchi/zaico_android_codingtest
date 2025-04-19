package jp.co.zaico.codingtest.model

import android.annotation.SuppressLint
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@SuppressLint("UnsafeOptInUsageError")
@Serializable
data class Inventory(
    val id: Int = -1,
    val title: String = "",
    val quantity: String? = null,
    val unit: String? = null,
    val category: String? = null,
    val state: String? = null,

    @SerialName("item_image")
    val image: ItemImage? = null
)

@SuppressLint("UnsafeOptInUsageError")
@Serializable
data class ItemImage(
    val url: String?
)

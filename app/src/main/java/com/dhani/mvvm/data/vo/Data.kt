package com.dhani.mvvm.data.vo

import com.google.gson.annotations.SerializedName


//data class ResponseMarketplace(
//    @Expose @SerializedName("code")
//    val code: Int,
//    @Expose @SerializedName("data")
//    val data : Data,
//    @Expose @SerializedName("message")
//    val message: String,
//    @Expose @SerializedName("status")
//    val status: Boolean
//)
//
//data class Data(
//    @SerializedName("current_page")
//    val currentPage: Int,
//    @SerializedName("data")
//    val data : List<DataProduct>,
//    @SerializedName("first_page_url")
//    val firstPageUrl: String,
//    @SerializedName("from")
//    val from: Int,
//    @SerializedName("last_page")
//    val lastPage: Int,
//    @SerializedName("last_page_url")
//    val lastPageUrl: String,
//    @SerializedName("next_page_url")
//    val nextPageUrl: String,
//    @SerializedName("path")
//    val path: String,
//    @SerializedName("per_page")
//    val perPage: String,
//    @SerializedName("prev_page_url")
//    val prevPageUrl: Any,
//    @SerializedName("to")
//    val to: Int,
//    @SerializedName("total")
//    val total: Int
//)
//
//data class DataProduct(
//    @SerializedName("brand_id")
//    val brandId: Int,
//    @SerializedName("category_id")
//    val categoryId: Int,
//    @SerializedName("code")
//    val code: String,
//    @SerializedName("color")
//    val color: String,
//    @SerializedName("commission")
//    val commission: Int,
//    @SerializedName("created_at")
//    val createdAt: String,
//    @SerializedName("detail")
//    val detail: String,
//    @SerializedName("discount_value")
//    val discountValue: Int,
//    @SerializedName("id")
//    val id: Int,
//    @SerializedName("image")
//    val image: String,
//    @SerializedName("level_code")
//    val levelCode: String,
//    @SerializedName("maximum_discount")
//    val maximumDiscount: Int,
//    @SerializedName("name")
//    val name: String,
//    @SerializedName("poin")
//    val poin: Int,
//    @SerializedName("price")
//    val price: Int,
//    @SerializedName("status")
//    val status: String,
//    @SerializedName("stock")
//    val stock: Int,
//    @SerializedName("type_discount")
//    val typeDiscount: Int,
//    @SerializedName("updated_at")
//    val updatedAt: String,
//    @SerializedName("vendor_id")
//    val vendorId: Int,
//    @SerializedName("weight")
//    val weight: Double
//)

data class MovieDetails(
    val budget: Int,
    val id: Int,
    val overview: String,
    val popularity: Double,
    @SerializedName("poster_path")
    val posterPath: String,
    @SerializedName("release_date")
    val releaseDate: String,
    val revenue: Long,
    val runtime: Int,
    val status: String,
    val tagline: String,
    val title: String,
    val video: Boolean,
    @SerializedName("vote_average")
    val rating: Double
)

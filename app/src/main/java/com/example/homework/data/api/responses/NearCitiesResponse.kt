package com.example.homework.data.api.responses
import com.example.homework.data.models.CityData
import com.google.gson.annotations.SerializedName


data class NearCitiesResponse(
    @SerializedName("cod")
    var cod: String?,
    @SerializedName("count")
    var count: Int?,
    @SerializedName("list")
    var list: List<CityData>,
    @SerializedName("message")
    var message: String?
)

data class Clouds(
    @SerializedName("all")
    var all: Int
)

data class Coord(
    @SerializedName("lat")
    var lat: Double,
    @SerializedName("lon")
    var lon: Double
)

data class Main(
    @SerializedName("feels_like")
    var feelsLike: Double,
    @SerializedName("humidity")
    var humidity: Int,
    @SerializedName("pressure")
    var pressure: Int,
    @SerializedName("temp")
    var temp: Double,
    @SerializedName("temp_max")
    var tempMax: Double,
    @SerializedName("temp_min")
    var tempMin: Double
)

data class Snow(
    @SerializedName("1h")
    var h: Double
)

data class Sys(
    @SerializedName("id")
    var id: Int,
    @SerializedName("country")
    var country: String,
    @SerializedName("sunrise")
    var sunrise: Long,
    @SerializedName("sunset")
    var sunset: Long
)

data class Weather(
    @SerializedName("description")
    var description: String,
    @SerializedName("icon")
    var icon: String,
    @SerializedName("id")
    var id: Int,
    @SerializedName("main")
    var main: String
)

data class Wind(
    @SerializedName("deg")
    var deg: Int,
    @SerializedName("speed")
    var speed: Double
)
package com.example.pepega.tenor.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class ImplementSearchResponseDto(
    @JsonProperty("results") val results: MutableList<Results>
)

data class Results (
    @JsonProperty("id") val id: String,
    @JsonProperty("title") val title: String,
    @JsonProperty("media_formats") val mediaFormats: MediaFormats,
    @JsonProperty("content_description") val contentDescription: String,
    @JsonProperty("itemurl") val itemUrl: String,
)

data class MediaFormats(
    @JsonProperty("tinygif") val tinyGif: TinyGif
)

data class TinyGif(
    @JsonProperty("url") val url: String,
    @JsonProperty("duration") val duration: Int,
    @JsonProperty("preview") val preview: String,
    @JsonProperty("dims") val dims: List<Int>,
    @JsonProperty("size") val size: Int,
)

package com.example.pepega.common.model.response

class MutableListResult<T> (
    success: Boolean,
    code: Int,
    message:String,
    var results: MutableList<T>

): CommonResult(success, code, message)

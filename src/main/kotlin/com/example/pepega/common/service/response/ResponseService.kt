package com.example.pepega.common.service.response

import com.example.pepega.common.model.response.CommonResult
import org.springframework.stereotype.Service

@Service
class ResponseService {

    fun successResult(): CommonResult {
        return CommonResult()
    }

    fun failResult(code: Int, message: String): CommonResult {
        return CommonResult(false, code, message)
    }
}

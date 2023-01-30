package com.example.pepega.common.service.response

import com.example.pepega.common.model.response.CommonResult
import com.example.pepega.common.model.response.SingleResult
import org.springframework.stereotype.Service

@Service
class ResponseService {

    fun successResult(): CommonResult {
        return CommonResult()
    }

    fun failResult(code: Int, message: String): CommonResult {
        return CommonResult(false, code, message)
    }

    fun <T> singleResult(data: T): SingleResult<T> {
        val common = successResult()
        return SingleResult(
            success = common.success,
            code = common.code,
            message = common.message,
            data = data
        )
    }
}

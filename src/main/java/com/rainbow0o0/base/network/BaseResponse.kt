package com.rainbow0o0.base.network

/**
 * Author : pengyu
 * Date   : 2022/7/29
 * Desc   :
 */
abstract class BaseResponse<T> {

    //抽象方法，用户的基类继承该类时，需要重写该方法
    abstract fun isSuccess(): Boolean

    abstract fun getResponseData(): T

    abstract fun getResponseCode(): Int

    abstract fun getResponseMsg(): String
}
package com.example.quecomohoy.ui

data class Resource<out T>(val status: Status, val data: T?, val additionalData: Map<String, Any>?) {
    companion object {
        fun <T> success(data: T?, additionalData: Map<String, Any>?): Resource<T> {
            return Resource(Status.SUCCESS, data, additionalData)
        }

        fun <T> error(msg: String, data: T?, additionalData : Map<String, Any>?): Resource<T> {
            return Resource(Status.ERROR, data, additionalData)
        }

        fun <T> loading(additionalData: Map<String, Any>?): Resource<T> {
            return Resource(Status.LOADING, null, additionalData)
        }
    }
}
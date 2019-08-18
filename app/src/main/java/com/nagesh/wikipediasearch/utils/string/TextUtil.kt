package com.nagesh.wikipediasearch.utils.string

object TextUtil {
    fun isEmpty(str: CharSequence?): Boolean {
        return str == null || str.isEmpty()
    }
}
package com.android.app.persistence

interface LocalData {

   fun savePages(pages: Int)

    fun getPages() : Int

}
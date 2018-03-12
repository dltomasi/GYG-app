package com.android.app.persistence

import android.content.Context

class LocalDataImpl(context: Context) : LocalData {

    companion object {
        private const val PAGES = "PAGES"
    }

    private val sharedPref = context.getSharedPreferences("ReviewsInfo", Context.MODE_PRIVATE)
    private val editor = sharedPref.edit();

   override fun savePages(pages: Int) {
       editor.putInt(PAGES, pages);
       editor.apply();
   }

    override fun getPages() : Int {
        return sharedPref.getInt(PAGES, 0)
    }

}
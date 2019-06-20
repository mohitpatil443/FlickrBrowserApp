package com.example.flickrbrowserapp


import android.os.AsyncTask
import android.util.Log
import java.io.IOException
import java.lang.Exception
import java.net.MalformedURLException
import java.net.URL

enum class  DownloadStatus{
    OK,IDLE,NOT_INITIALIZED,FAILED,PERMISSION_ERROR,ERROR
}

//static class enum class


class rawData(private val listener:OnDownloadComplete):AsyncTask<String,Void,String>() {
    val TAG="rawData"
    var downloadStatus=DownloadStatus.IDLE
    override fun onPostExecute(result: String) {



        listener.onDownloadComplete(result,downloadStatus)

    }
    interface OnDownloadComplete
    {
        fun onDownloadComplete(result:String,downloadStatus: DownloadStatus)
    }

    override fun doInBackground(vararg p0: String?): String {
        if(p0[0]==null)
        {
            Log.d(TAG,"Invalid URL")
            return "Invalid URL"

        }
      try {
          downloadStatus=DownloadStatus.OK
          return URL(p0[0]).readText()
      }catch (e:Exception)
      {
         val  errorMessage:String=when(e)
         {
             is MalformedURLException ->{
                 downloadStatus=DownloadStatus.NOT_INITIALIZED
                 "Invalid URL ${e.message}"

             }
             is IOException ->{
                 downloadStatus=DownloadStatus.FAILED
                 "IOException occured ${e.message}"
             }
             is SecurityException->{
                 downloadStatus=DownloadStatus.PERMISSION_ERROR
                 "Permission Error ${e.message}"
             }
             else-> {
                 downloadStatus = DownloadStatus.ERROR
                 "Unknown Error"
             }

         }
          return errorMessage
      }

    }
}
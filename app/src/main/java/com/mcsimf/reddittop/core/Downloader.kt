package com.mcsimf.reddittop.core

import android.app.DownloadManager
import android.content.Context
import android.net.Uri
import android.os.Environment
import java.io.File


/**
 * @author Maksym Fedyay on 9/13/20 (mcsimf@gmail.com).
 */
object Downloader {

    fun download(context: Context, url: String) {
        val downloadUri: Uri = Uri.parse(url)
        val filename = downloadUri.lastPathSegment
        val ext = filename?.substringAfterLast(".")
        val request = DownloadManager.Request(downloadUri)
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI or DownloadManager.Request.NETWORK_MOBILE)
            .setAllowedOverRoaming(false)
            .setTitle(filename)
            .setMimeType("image/" + (ext ?: "jpeg"))
            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            .setDestinationInExternalPublicDir(Environment.DIRECTORY_PICTURES, File.separator + filename)
        val dm = context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        dm.enqueue(request)
    }

}
package com.example.vsuet.scheduleWidget

import android.content.Intent
import android.widget.RemoteViewsService

class WidgetService : RemoteViewsService() {

    override fun onGetViewFactory(p0: Intent?): RemoteViewsFactory {
        return ListViewRemoteFactory(applicationContext, p0!!)
    }

}
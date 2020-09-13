package com.mcsimf.reddittop.core

import android.content.Context

/**
 * @author Maksym Fedyay on 9/13/20 (mcsimf@gmail.com).
 */
class Core(context: Context) {

    companion object : SingletonHolder<Core, Context>(::Core)

}
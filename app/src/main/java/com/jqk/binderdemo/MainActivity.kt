package com.jqk.binderdemo

import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.graphics.Rect
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import android.os.RemoteException
import android.util.Log
import android.widget.Button
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    private var mIRemoteService: IRemoteService? = null
    private var mIntent: Intent? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mIntent = Intent()
        mIntent?.setClass(this, RemoteService::class.java)
        bindService(mIntent, mConnection, BIND_AUTO_CREATE)

        findViewById<Button>(R.id.bt_aidl).setOnClickListener {
            kotlin.runCatching {
                Toast.makeText(
                    this,
                    mIRemoteService?.getPid(Rect(2, 2, 2, 2)).toString(),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private val mConnection: ServiceConnection = object : ServiceConnection {
        override fun onServiceConnected(className: ComponentName, service: IBinder) {
            mIRemoteService = IRemoteService.Stub.asInterface(service)
            try {
                mIRemoteService?.registerCallback(mCallback)
                startService(mIntent)
            } catch (e: RemoteException) {
                e.printStackTrace()
            }
        }

        override fun onServiceDisconnected(className: ComponentName) {
            Log.e("jiqingke", "Service has unexpectedly disconnected")
            try {
                mIRemoteService?.unregisterCallback(mCallback)
            } catch (e: RemoteException) {
                e.printStackTrace()
            } finally {
                mIRemoteService = null
            }
        }
    }

    private val mCallback: IRemoteSerciceCallback = object : IRemoteSerciceCallback.Stub() {
        @Throws(RemoteException::class)
        override fun onSuccess(code: Int) {
            Log.d("jiqingke", "onSuccess = $code")
        }

        @Throws(RemoteException::class)
        override fun onFail(rect: Rect) {
            Log.d("jiqingke", "onFail = $rect")
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        stopService(intent)
    }
}
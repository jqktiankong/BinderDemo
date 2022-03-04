package com.jqk.binderdemo;

import android.app.Service;
import android.content.Intent;
import android.graphics.Rect;
import android.os.IBinder;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.util.Log;

/**
 * Created by Administrator on 2018/5/28 0028.
 */

public class RemoteService extends Service {

    final RemoteCallbackList<IRemoteSerciceCallback> mCallbacks = new RemoteCallbackList<IRemoteSerciceCallback>();

    @Override
    public void onCreate() {
        Log.d("jiqingke", "onCreate");
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("jiqingke", "onStartCommand");
        callback();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.d("jiqingke", "onBind");
        return mBinder;
    }


    void callback() {
        final int N = mCallbacks.beginBroadcast();
        Log.d("jiqingke", "N = " + N);
        for (int i = 0; i < N; i++) {
            try {
                mCallbacks.getBroadcastItem(i).onSuccess(i);
                mCallbacks.getBroadcastItem(i).onFail(new Rect(1, 1, 1, 1));
            } catch (RemoteException e) {
            }
        }
        mCallbacks.finishBroadcast();
    }


    private final IRemoteService.Stub mBinder = new IRemoteService.Stub() {
        @Override
        public int getPid(Rect rect) throws RemoteException {
            return rect.bottom;
        }

        @Override
        public void registerCallback(IRemoteSerciceCallback callback) throws RemoteException {
            Log.d("jiqingke", "registerCallback");
            if (callback != null) {
                mCallbacks.register(callback);
            }
        }

        @Override
        public void unregisterCallback(IRemoteSerciceCallback callback) throws RemoteException {
            Log.d("jiqingke", "unregisterCallback");
            if (callback != null) {
                mCallbacks.unregister(callback);
            }
        }

        @Override
        public IBinder getBinder() throws RemoteException {
            // 返回IBinder对象，实现一个服务绑定多个接口
            return null;
        }
    };
}

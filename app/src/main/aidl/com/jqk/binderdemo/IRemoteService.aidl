// IRemoteService.aidl
package com.jqk.binderdemo;
import com.jqk.binderdemo.IRemoteSerciceCallback;
// Declare any non-default types here with import statements

interface IRemoteService {
    /** Request the process ID of this service, to do evil things with it. */
    int getPid(in Rect rect);

    void registerCallback(IRemoteSerciceCallback callback);

    void unregisterCallback(IRemoteSerciceCallback callback);
}

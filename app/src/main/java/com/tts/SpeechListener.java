//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.tts;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;


public class SpeechListener extends Binder
{

    public SpeechListener() {

    }

    public IBinder asBinder()
    {
        return this;
    }

    public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
        return super.onTransact(code, data, reply, flags);
    }

}



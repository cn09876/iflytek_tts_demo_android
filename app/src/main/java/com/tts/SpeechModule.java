package com.tts;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.IBinder;
import android.os.Parcel;
import android.text.TextUtils;
import android.util.Log;


public class SpeechModule
{
    protected Object mSynLock = new Object();
    protected Context mContext = null;
    private ServiceConnection mConnection = null;
    private static final String mBindAction = "com.iflytek.component.speechsynthesizer";
    public IBinder mRemote=null;
    static final int TRANSACTION_synthesizeToUrl = 1;
    static final int TRANSACTION_startSpeaking = 2;
    static final int TRANSACTION_pauseSpeaking = 3;
    static final int TRANSACTION_resumeSpeaking = 4;
    static final int TRANSACTION_stopSpeaking = 5;
    static final int TRANSACTION_isSpeaking = 6;
    static final int TRANSACTION_getLocalSpeakerList = 7;

    void l(String s)
    {
        Log.e("ifly",s);
    }

    public SpeechModule(Context context) {
        this.mContext = context;
        this.bindService();
    }

    public static boolean isServiceInstalled(Context context) {
        if(null != context && !TextUtils.isEmpty(mBindAction)) {
            PackageManager pm = context.getPackageManager();
            Intent intent = new Intent(mBindAction);
            ResolveInfo info = pm.resolveService(intent, 0);
            return info != null;
        } else {
            return false;
        }
    }

    private void bindService() {
        if(!isServiceInstalled(this.mContext)) return;
        Intent intent = this.getIntent();
        intent.setAction(this.mBindAction);

        this.mConnection = new ServiceConnection()
        {
            public void onServiceConnected(ComponentName name, IBinder service)
            {
                synchronized(SpeechModule.this.mSynLock)
                {
                    try
                    {
                        mRemote=service;
                    }
                    catch (Exception e)
                    {

                    }
                }
            }

            public void onServiceDisconnected(ComponentName name)
            {
                SpeechModule.this.bindService();
            }
        };
        this.mContext.bindService(intent, this.mConnection, Context.BIND_AUTO_CREATE);
    }

    public Intent getIntent() {
        Intent intent = new Intent();
        return intent;
    }

    public boolean destory()
    {

        try {
            if(this.mConnection != null)
            {
                this.mContext.unbindService(this.mConnection);
                this.mConnection = null;
            }
            return true;
        }
        catch (Exception var2)
        {
            return false;
        }
    }

    public int startSpeaking(String text)
    {
        Parcel _data = Parcel.obtain();
        Parcel _reply = Parcel.obtain();

        int _result=0;
        try
        {
            Intent e = this.getIntent();
            e.putExtra("text", text);
            //e.putExtra("voice_name", "xiaomei");
            _data.writeInterfaceToken("com.iflytek.speech.aidl.ISpeechSynthesizer");
            _data.writeInt(1);
            e.writeToParcel(_data, 0);
            _data.writeStrongBinder((new SpeechListener()).asBinder());
            this.mRemote.transact(TRANSACTION_startSpeaking, _data, _reply, 0);
            _reply.readException();
            _result = _reply.readInt();
        }
        catch (Exception e)
        {
            l("tts.speak error "+e.getMessage());
        }
        finally
        {
            _reply.recycle();
            _data.recycle();
        }

        return _result;
    }

    public int pauseSpeaking(SpeechListener listener)
    {
        Parcel _data = Parcel.obtain();
        Parcel _reply = Parcel.obtain();

        int _result=0;
        try
        {
            _data.writeInterfaceToken("com.iflytek.speech.aidl.ISpeechSynthesizer");
            _data.writeStrongBinder(listener != null?listener.asBinder():null);
            this.mRemote.transact(3, _data, _reply, 0);
            _reply.readException();
            _result = _reply.readInt();
        }
        catch (Exception e)
        {}
        finally {
            _reply.recycle();
            _data.recycle();
        }

        return _result;
    }

    public int resumeSpeaking(SpeechListener listener)
    {
        Parcel _data = Parcel.obtain();
        Parcel _reply = Parcel.obtain();

        int _result=0;
        try {
            _data.writeInterfaceToken("com.iflytek.speech.aidl.ISpeechSynthesizer");
            _data.writeStrongBinder(listener != null?listener.asBinder():null);
            this.mRemote.transact(4, _data, _reply, 0);
            _reply.readException();
            _result = _reply.readInt();
        }
        catch (Exception e)
        {}
        finally {
            _reply.recycle();
            _data.recycle();
        }

        return _result;
    }

    public int stopSpeaking(SpeechListener listener)
    {
        Parcel _data = Parcel.obtain();
        Parcel _reply = Parcel.obtain();

        int _result=0;
        try {
            _data.writeInterfaceToken("com.iflytek.speech.aidl.ISpeechSynthesizer");
            _data.writeStrongBinder(listener != null?listener.asBinder():null);
            this.mRemote.transact(5, _data, _reply, 0);
            _reply.readException();
            _result = _reply.readInt();
        }
        catch (Exception e)
        {}
        finally {
            _reply.recycle();
            _data.recycle();
        }

        return _result;
    }

    public boolean isSpeaking()
    {
        Parcel _data = Parcel.obtain();
        Parcel _reply = Parcel.obtain();

        boolean _result=false;
        try {
            _data.writeInterfaceToken("com.iflytek.speech.aidl.ISpeechSynthesizer");
            this.mRemote.transact(6, _data, _reply, 0);
            _reply.readException();
            _result = 0 != _reply.readInt();
        }
        catch (Exception e)
        {}
        finally {
            _reply.recycle();
            _data.recycle();
        }

        return _result;
    }

    public String getLocalSpeakerList()
    {
        Parcel _data = Parcel.obtain();
        Parcel _reply = Parcel.obtain();

        String _result="";
        try {
            _data.writeInterfaceToken("com.iflytek.speech.aidl.ISpeechSynthesizer");
            this.mRemote.transact(7, _data, _reply, 0);
            _reply.readException();
            _result = _reply.readString();
        }
        catch (Exception e){}
        finally {
            _reply.recycle();
            _data.recycle();
        }

        return _result;
    }

}

/*
 * 中文语音合成
 * 孙威威 2014-08-21
 * 使用科大讯飞的语音库，需安装科大的APK
 * */

package ssp.tts_demo;

import android.content.Context;

import com.tts.SpeechModule;

public class clsTTS
{
    public SpeechModule mTts;

    public clsTTS(Context ctx)
    {
        mTts = new SpeechModule(ctx);
    }

    public void speak(String s)
    {
        mTts.startSpeaking(s);
    }




}
package ssp.tts_demo;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class FrmMain extends Activity {

    Button btn1;
    clsTTS t;

    void l(String s)
    {
        Log.e("tts.demo",s);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frm_main);

        l("onCreate ok,ready to create clstts object");
        t=new clsTTS(this);

        btn1=(Button)findViewById(R.id.btn1);
        btn1.setText("1234567");

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                t.speak("报读");
                btn1.setText(t.mTts.getLocalSpeakerList());
            }
        });
    }
}

package org.zqt.slidebutton;

import org.zqt.slidebutton.interf.OnToggleStateChangedListener;
import org.zqt.slidebutton.view.SlideButton;


import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends Activity implements OnToggleStateChangedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        SlideButton mSlideButton = (SlideButton) findViewById(R.id.slidebutton);
        
        // ����һ�¿��ص�״̬
        mSlideButton.setToggleState(true);		// ���ÿ��ص�״̬Ϊ��
        
        mSlideButton.setOnToggleStateChangedListener(this);
    }

	@Override
	public void onToggleStateChanged(boolean state) {
		if(state) {
			Toast.makeText(this, "���ش�", 0).show();
		} else {
			Toast.makeText(this, "���عر�", 0).show();
		}
	}

}

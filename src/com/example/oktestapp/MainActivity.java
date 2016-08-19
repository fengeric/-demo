package com.example.oktestapp;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	public void btnOpenThirdApp1(View v) {
		startActivity(new Intent(Intent.ACTION_VIEW,
				Uri.parse("life057://good/5784635550852c2f48a51f9e")));
	}
	
	public void btnOpenThirdApp2(View v) {
		startActivity(new Intent(Intent.ACTION_VIEW,
				Uri.parse("life057://bulltin/5768ba5650852c2474df3e89")));
	}
}

package com.example.oktestapp;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends Activity {
	private ImageView imageViewBg;
	private Bitmap bitmap;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		imageViewBg = (ImageView) findViewById(R.id.share_bg);

		bitmap = BitmapFactory.decodeResource(getResources(),
				R.drawable.blur_bg);
		bitmap = compressImage(bitmap);
	}

	/**
	 * ����ë����Ч��
	 */
	public void btnBlur(View v) {
		try {
			int scaleRatio = 30;// ͼƬ����ѹ������(ѹ������Խ���ٶ�Խ�죬ģ���̶�Խ��)
			int blurRadius = 8;// ģ���̶�(ģ���̶�Խ���ٶ�Խ����ģ���̶�Խ��)
			Bitmap scaledBitmap = Bitmap.createScaledBitmap(
					compressImage(bitmap), bitmap.getWidth() / scaleRatio,
					bitmap.getHeight() / scaleRatio, false);
			Bitmap newBitmap = Blur.fastblur(MainActivity.this, scaledBitmap,
					blurRadius);
			imageViewBg.setImageBitmap(newBitmap);
		} catch (Exception e) {
			Log.e("lala", "btnBlur");
		}

	}

	/**
	 * ��ͼƬ�Ľ�������ѹ��
	 */
	private static Bitmap compressImage(Bitmap image) {

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		image.compress(Bitmap.CompressFormat.JPEG, 30, baos);// ����ѹ��������30��ѹ���ʱ�ʾѹ��70%����ѹ��������ݴ�ŵ�baos��
		int options = 100;
		while (baos.toByteArray().length / 1024 > 200) { // ѭ���ж����ѹ����ͼƬ�Ƿ����200kb,���ڼ���ѹ��
			baos.reset();// ����baos�����baos
			options -= 10;// ÿ�ζ�����10
			image.compress(Bitmap.CompressFormat.JPEG, options, baos);// ����ѹ��options%����ѹ��������ݴ�ŵ�baos��

		}
		ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());// ��ѹ���������baos��ŵ�ByteArrayInputStream��
		Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);// ��ByteArrayInputStream��������ͼƬ
		return bitmap;
	}
}

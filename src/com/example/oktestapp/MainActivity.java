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
	 * 生成毛玻璃效果
	 */
	public void btnBlur(View v) {
		try {
			int scaleRatio = 30;// 图片质量压缩比例(压缩比例越大，速度越快，模糊程度越高)
			int blurRadius = 8;// 模糊程度(模糊程度越大，速度越慢，模糊程度越高)
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
	 * 将图片的进行质量压缩
	 */
	private static Bitmap compressImage(Bitmap image) {

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		image.compress(Bitmap.CompressFormat.JPEG, 30, baos);// 质量压缩方法，30是压缩率表示压缩70%，把压缩后的数据存放到baos中
		int options = 100;
		while (baos.toByteArray().length / 1024 > 200) { // 循环判断如果压缩后图片是否大于200kb,大于继续压缩
			baos.reset();// 重置baos即清空baos
			options -= 10;// 每次都减少10
			image.compress(Bitmap.CompressFormat.JPEG, options, baos);// 这里压缩options%，把压缩后的数据存放到baos中

		}
		ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());// 把压缩后的数据baos存放到ByteArrayInputStream中
		Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);// 把ByteArrayInputStream数据生成图片
		return bitmap;
	}
}

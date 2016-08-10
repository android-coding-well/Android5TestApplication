package com.gosuncn.core.utils.helpers;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Toast;

import com.gosuncn.core.event.CommonEvent;
import com.gosuncn.core.event.IEvent;

import org.greenrobot.eventbus.EventBus;

import java.io.File;


/**
 * 头像裁剪助手
 * 
 * @author HWJ
 * 
 */

@Deprecated
public class HeadPortraitHelper {

	/** 拍照 */
	public static final int REQUEST_TAKE_PHOTO = 111;
	/** 打开相册 */
	public static final int REQUEST_OPEN_ALBUM = 222;
	/** 裁剪图片 */
	public static final int REQUEST_CROP_PHOTO = 333;

	private String imageName; //拍照后图片的名称
	private Activity activity;
	private File direction; //存储图片的目录

	private int width = 150;
	private int height = 150;

	private Bitmap cropBitmap = null;// 裁剪后的图片

	/**
	 * 
	 * @param activity
	 * @param dir 存储目录
	 */
	public HeadPortraitHelper(Activity activity, String dir) {
		this.activity = activity;
		 direction = new File(dir);
		if (!direction.exists()) {
			direction.mkdirs();
		}
	}

	/**
	 * 设置裁剪图片的宽高，默认为150*150
	 * 
	 * @param width
	 * @param height
	 */
	public void setImageSize(int width, int height) {
		this.width = width;
		this.height = height;
	}

	/**
	 * 得到裁剪的图片
	 * @return
	 */
	public Bitmap getCropBitmap() {
		return cropBitmap;
	}

	/**
	 * 拍照裁剪
	 * 
	 * @param imgName
	 */
	public void takePhoto(String imgName) {
		this.imageName = imgName;
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		intent.putExtra(MediaStore.EXTRA_OUTPUT,
				Uri.fromFile(new File(direction, imgName)));//
		activity.startActivityForResult(intent, REQUEST_TAKE_PHOTO);// 采用ForResult打开
	}

	/**
	 * 相册裁剪
	 */
	public void openAlbum() {
		Intent intent = new Intent(Intent.ACTION_PICK, null);
		intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
				"image/*");
		activity.startActivityForResult(intent, REQUEST_OPEN_ALBUM);
	}
	

	/**
	 * 需要在相应的Activity中的onActivityResult中调用
	 * @param requestCode
	 * @param resultCode
	 * @param data
	 */
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode != activity.RESULT_OK)
			return;
		switch (requestCode) {
		case REQUEST_OPEN_ALBUM:
			if (data != null)
				cropPhoto(data.getData());
			break;
		case REQUEST_TAKE_PHOTO:
			File image = new File(direction.getAbsolutePath(), imageName);
			if (image.exists()) {
				cropPhoto(Uri.fromFile(image));// 裁剪图片
			} else {
				Toast.makeText(activity, "拍照失败", Toast.LENGTH_LONG).show();
			}
			break;
		case REQUEST_CROP_PHOTO:
			if (data != null) {
				Bundle extras = data.getExtras();
				cropBitmap = extras.getParcelable("data");
				EventBus.getDefault().post(new CommonEvent<Bitmap>(IEvent.CROP_SUCCESS,cropBitmap));
			}else{
				Toast.makeText(activity, "裁剪失败", Toast.LENGTH_LONG).show();
			}
		}
	}

	/**
	 * 裁剪照片
	 * */
	public void cropPhoto(Uri uri) {
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, "image/*");
		intent.putExtra("crop", "true");
		// aspectX aspectY 是宽高的比例
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);
		// outputX outputY 是裁剪图片宽高
		intent.putExtra("outputX", width);
		intent.putExtra("outputY", height);
		intent.putExtra("return-data", true);
		activity.startActivityForResult(intent, REQUEST_CROP_PHOTO);
	}

}

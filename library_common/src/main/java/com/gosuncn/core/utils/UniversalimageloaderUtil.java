package com.gosuncn.core.utils;

import android.content.Context;
import android.widget.ImageView;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
/**
 * 开源库Universalimageloader工具类
 * @author HWJ
 *
 */
@Deprecated
public class UniversalimageloaderUtil {

	/**
	 * 初始化
	 * 使用时可以放在Application中初始化一次就可以了
	 * 在发行时将config.writeDebugLogs()这一行注释掉
	 * @param context
	 */
	public static void initImageLoader(Context context) {
		// This configuration tuning is custom. You can tune every option, you may tune some of them,
		// or you can create default configuration by
		//  ImageLoaderConfiguration.createDefault(this);
		// method.
		ImageLoaderConfiguration.Builder config = new ImageLoaderConfiguration.Builder(context);
		config.threadPriority(Thread.NORM_PRIORITY - 2);
		config.denyCacheImageMultipleSizesInMemory();
		config.diskCacheFileNameGenerator(new Md5FileNameGenerator());
		config.diskCacheSize(50 * 1024 * 1024); // 50 MiB
		config.tasksProcessingOrder(QueueProcessingType.LIFO);
		//config.threadPoolSize(3);//线程池内加载的数量 
		//config.memoryCache(new UsingFreqLimitedMemoryCache(2 * 1024 * 1024)) ;//你可以通过自己的内存缓存实现 
		//config.memoryCacheSize(2 * 1024 * 1024);
		config.writeDebugLogs(); // Remove for release app
		//config.imageDownloader(new BaseImageDownloader(context, 5 * 1000, 20 * 1000)) ;// connectTimeout (5 s), readTimeout (30 s)超时时间  
		
		// Initialize ImageLoader with configuration.
		ImageLoader.getInstance().init(config.build());
	}
	
	public static  DisplayImageOptions  createImageOptions(int defResourceId){
		DisplayImageOptions displayImageOptions=new DisplayImageOptions.Builder()
		.showImageOnLoading(defResourceId) // 加载显示的图片
		.showImageForEmptyUri(defResourceId) // 空uri
		.showImageOnFail(defResourceId)// 加载失败
		.cacheInMemory(true)//设置下载的图片是否缓存在内存中  
		.cacheOnDisc(true)//设置下载的图片是否缓存在SD卡中  
		//.considerExifParams(true)  //是否考虑JPEG图像EXIF参数（旋转，翻转）
		//.imageScaleType(ImageScaleType.EXACTLY_STRETCHED)//设置图片以如何的编码方式显示  
		//.bitmapConfig(Bitmap.Config.RGB_565)//设置图片的解码类型//  
		//.decodingOptions(android.graphics.BitmapFactory.Options decodingOptions)//设置图片的解码配置  
		//.delayBeforeLoading(int delayInMillis)//int delayInMillis为你设置的下载前的延迟时间
		//.preProcessor(BitmapProcessor preProcessor) //设置图片加入缓存前，对bitmap进行设置  
		//.resetViewBeforeLoading(true)//设置图片在下载前是否重置，复位  
		.displayer(new RoundedBitmapDisplayer(5))//是否设置为圆角，弧度为多少  
		.displayer(new FadeInBitmapDisplayer(100))//是否图片加载好后渐入的动画时间 
		.build();
		return displayImageOptions;
	}

	/**
	 * 显示图片
	 * @param uri
	 * @param imageView
	 * @param defResourceId 默认图
	 */
	public static void displayImage(String uri,ImageView imageView,int defResourceId){
		ImageLoader.getInstance().displayImage(uri,imageView, new DisplayImageOptions.Builder()
				.showImageOnLoading(defResourceId) //加载显示的图片
				.showImageForEmptyUri(defResourceId) //空uri
				.showImageOnFail(defResourceId)//加载失败
				.cacheInMemory(true)
				.cacheOnDisk(true)
						//.considerExifParams(true)
						//.displayer(new RoundedBitmapDisplayer(20))//圆角
				.build());
	}
	/**
	 * 显示图片
	 * @param uri
	 * @param imageView
	 */
	public static void displayImage(String uri,ImageView imageView){
		ImageLoader.getInstance().displayImage(uri,imageView, new DisplayImageOptions.Builder()
				.cacheInMemory(true)
				.cacheOnDisk(true)
						//.considerExifParams(true)
						//.displayer(new RoundedBitmapDisplayer(20))//圆角
				.build());
	}
	
}

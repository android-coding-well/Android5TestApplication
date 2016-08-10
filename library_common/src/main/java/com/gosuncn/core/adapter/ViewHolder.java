package com.gosuncn.core.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

public class ViewHolder {

	
	private View convertView;
	private SparseArray<View> views;//存放控件
	private int position;
	private ViewHolder(Context context,int position, ViewGroup parent,int layoutId){
		this.position=position;
		this.views =new SparseArray<View>();
		this.convertView=LayoutInflater.from(context).inflate(layoutId, parent,false);
		convertView.setTag(this);
		
	}
	/**
	 * 获得viewHolder
	 * @param context
	 * @param position
	 * @param convertView
	 * @param parent
	 * @param layoutId
	 * @return
	 */
	public static ViewHolder getViewHolder(Context context,int position, View convertView, ViewGroup parent,int layoutId){
		
		if(convertView==null){
			return new ViewHolder(context,position,parent,layoutId);
		}else{
			ViewHolder viewHolder=(ViewHolder) convertView.getTag();
			//由于viewholder是复用的，因此需要修改position
			viewHolder.position =position;
			return viewHolder;
		}
	}
	
	
	/**
	 * 获得控件
	 * @param viewId
	 * @return
	 */
	public <T extends View> T getView(int viewId){
		View view=views.get(viewId);
		if(view==null){
			view=convertView.findViewById(viewId);
			views.put(viewId, view);
		}
		return (T) view;
	}
	
	
	/**
     * 获得convertView
     * @return
     */
    public View getConvertView(){
        return convertView;
    }
    /**
     * 获得position
     * @return
     */
    public int getPosition(){
        return position;
    }
    
    //-------------------------TextView-----------------------
    /**
     * 设置TextView的内容
     * @param viewId
     * @param text
     * @return
     */
    public ViewHolder setText(int viewId,CharSequence text){
        TextView tv=getView(viewId);
        tv.setText(text);
        return this;
    }

    /**
     * 设置TextView的内容
     * @param viewId
     * @param textId
     * @return
     * @throws Exception 
     */
    public ViewHolder setText(int viewId,int textId) {
    	//try{
        TextView tv=getView(viewId);
        tv.setText(textId);
    	//}catch(ClassCastException e){
    	//	throw new Exception("类型转换错误，请确保传入的是TextView的viewId");
    	//}
        return this;
    }
    
    /**
     *  设置TextView的颜色
     * @param viewId
     * @param color
     * @return
     */
    public ViewHolder setTextColor(int viewId,int color){
    	 TextView tv=getView(viewId);
         tv.setTextColor(color);
         return this;
    }
    
 
    /**
     * 设置textView的图片
     * @param viewId
     * @param left
     * @param top
     * @param right
     * @param bottom
     * @return
     */
    public ViewHolder setCompoundDrawablesWithIntrinsicBounds(int viewId,Drawable left, Drawable top, Drawable right, Drawable bottom){
      	 TextView tv=getView(viewId);
      	 tv.setCompoundDrawablesWithIntrinsicBounds(left, top, right, bottom);
        return this;
      }
    
    //----------------------公共-----------------------
    public ViewHolder setVisibility(int viewId,int visibility){
    	
   	 	getView(viewId).setVisibility(visibility);
        return this;
   }
    
    public ViewHolder setBackgroundResource(int viewId,int resid){
    	
   	 	getView(viewId).setBackgroundResource(resid);
        return this;
   }
    
 public ViewHolder setBackgroundDrawable(int viewId,Drawable background){
   	 	getView(viewId).setBackgroundDrawable(background);
        return this;
   }
    
    public ViewHolder setOnClickListener(int viewId,OnClickListener  listener){
        View iv=getView(viewId);
        iv.setOnClickListener(listener);
        return this;
    }
    
    
    public ViewHolder setCheckedOfToggleButton(int viewId,boolean checked){
        ToggleButton iv=getView(viewId);
        iv.setChecked(checked);
        return this;
    }
    public ViewHolder setOnCheckedChangeListenerOfCheckBox(int viewId,OnCheckedChangeListener listener){
        CheckBox iv=getView(viewId);
        iv.setOnCheckedChangeListener(listener);
        return this;
    }
    
    public ViewHolder setCheckedOfCheckBox(int viewId,boolean checked){
        CheckBox iv=getView(viewId);
        iv.setChecked(checked);
        return this;
    }
    
    
    

//----------------------ImageView-----------------------------
    public ViewHolder setImageBitmap(int viewId,Bitmap bitmap){
        ImageView iv=getView(viewId);
        iv.setImageBitmap(bitmap);
        return this;
    }
    public ViewHolder setImageDrawable(int viewId,Drawable drawable){
        ImageView iv=getView(viewId);
        iv.setImageDrawable(drawable);
        return this;
    }
    public ViewHolder setImageResource(int viewId,int resourceId){
        ImageView iv=getView(viewId);
        iv.setImageResource(resourceId);
        return this;
    }
    public ViewHolder setImageURI(int viewId,Uri uri){
        ImageView iv=getView(viewId);
        iv.setImageURI(uri);
        return this;
    }
    
   
    
    
    
    /**
     * 网络加载图片
     * @param viewId
     * @param uri 图片的网络地址
     * @param defResourceId 默认显示的图片
     * @return
     */
    public ViewHolder displayImage(int viewId,String uri,int defResourceId){
    	ImageLoader.getInstance().displayImage(uri,(ImageView)getView(viewId), new DisplayImageOptions.Builder() 
		.showImageOnLoading(defResourceId) //加载显示的图片
		.showImageForEmptyUri(defResourceId) //空uri
		.showImageOnFail(defResourceId)//加载失败
		.cacheInMemory(true) 
		.cacheOnDisk(true)
		//.considerExifParams(true)
		//.displayer(new RoundedBitmapDisplayer(20))//圆角
		.build());
    	return this;
    }
    
   
    /**
     * 网络加载图片
     * @param viewId
     * @param uri 图片的网络地址
     * @param options
     * @return
     */
    public ViewHolder displayImage(int viewId,String uri,DisplayImageOptions options){
    	ImageLoader.getInstance().displayImage(uri,(ImageView)getView(viewId), options);
    	return this;
    }
}

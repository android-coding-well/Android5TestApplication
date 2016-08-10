package com.gosuncn.core.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;

import com.gosuncn.core.R;

import java.util.List;
/**
 * 通用Adapter
 * 内部封装了ViewHolder，只需继承并实现抽象方法即可
 * @author HWJ
 *
 * @param <T>
 */
public abstract class CommonAdapter<T> extends BaseAdapter {
	
	protected List<T> list;
	protected Context context;
	protected int layoutId;
	protected  Animation pushLeftIn,pushRightIn; 
	protected boolean isLoadFirstAnim=false;//是否首次加载动画
	public CommonAdapter(Context context,List<T> list,int layoutId){
		this.context=context;
		this.list=list;
		this.layoutId=layoutId;
		pushLeftIn=AnimationUtils.loadAnimation(context, R.anim.push_left_in);  
		pushRightIn=AnimationUtils.loadAnimation(context, R.anim.push_right_in);
	}
	public CommonAdapter(Context context,List<T> list,int layoutId,boolean isLoadFirstAnim){
		this(context,list,layoutId);
		this.isLoadFirstAnim=isLoadFirstAnim;
	}
	
	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public T getItem(int position) {
		if(getCount()==0){
			return null;
		}
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder=ViewHolder.getViewHolder(context, position, convertView, parent, layoutId);
		convert(viewHolder, getItem(position));
		if(isLoadFirstAnim==true&&convertView==null){//只在第一次开启动画效果
			if (position % 2 == 0) {  
				pushLeftIn.setDuration(500);  
	            viewHolder.getConvertView().startAnimation(pushLeftIn);  
	        } else {  
	        	pushRightIn.setDuration(500);  
	            viewHolder.getConvertView().startAnimation(pushRightIn);  
	        }  
		}
		return viewHolder.getConvertView();
	}
	/**
     * 设置控件的显示内容
     * @param viewHolder
     * @param t
     */
	public abstract void convert(ViewHolder viewHolder,T t) ;
		

}

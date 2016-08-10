package com.gosuncn.test.recyclerview;

import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;
import android.widget.TextView;

/**
 * 与BaseRecyclerAdapter配合使用
 */
public class RecyclerViewHolder extends RecyclerView.ViewHolder {

    private SparseArray<View> views;//存放控件
    public RecyclerViewHolder(View itemView) {
        super(itemView);
        this.views =new SparseArray<View>();
    }

    /**
     * 获得控件
     * @param viewId
     * @return
     */
    public <T extends View> T getView(int viewId){
        View view=views.get(viewId);
        if(view==null){
            view=itemView.findViewById(viewId);
            views.put(viewId, view);
        }
        return (T) view;
    }


    //************************扩展功能函数，方便使用，可在此自定义更多控件的设置******************************
    /**
     * 设置TextView的内容
     * @param viewId
     * @param text
     * @return
     */
    public RecyclerViewHolder setText(int viewId,CharSequence text){
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
    public RecyclerViewHolder setText(int viewId,int textId) {
        //try{
        TextView tv=getView(viewId);
        tv.setText(textId);
        //}catch(ClassCastException e){
        //	throw new Exception("类型转换错误，请确保传入的是TextView的viewId");
        //}
        return this;
    }
}

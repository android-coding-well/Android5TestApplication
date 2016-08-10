package com.gosuncn.test.openfire.adapter;

import android.content.Context;
import android.view.View;

import com.gosuncn.core.adapter.CommonAdapter;
import com.gosuncn.core.adapter.ViewHolder;
import com.gosuncn.test.R;
import com.gosuncn.test.openfire.domain.ChatInfo;

import java.util.List;

/**
 * Created by hwj on 2016/6/2.
 */
public class ChatAdapter extends CommonAdapter<ChatInfo> {
    public ChatAdapter(Context context, List<ChatInfo> list, int layoutId) {
        super(context, list, layoutId);
    }

    /**
     * 设置控件的显示内容
     *
     * @param viewHolder
     * @param s
     */
    @Override
    public void convert(ViewHolder viewHolder, ChatInfo s) {

        if(s.isTo){
            viewHolder.setVisibility(R.id.ll_item_to, View.VISIBLE)
                    .setVisibility(R.id.ll_item_from,View.GONE)
                    .setText(R.id.tv_item_to_user,s.userJID)
                    .setText(R.id.tv_item_to_text,s.content);
        }else{
            viewHolder.setVisibility(R.id.ll_item_to, View.GONE)
                    .setVisibility(R.id.ll_item_from,View.VISIBLE)
                    .setText(R.id.tv_item_from_user,s.userJID)
                    .setText(R.id.tv_item_from_text,s.content);
        }
    }
}

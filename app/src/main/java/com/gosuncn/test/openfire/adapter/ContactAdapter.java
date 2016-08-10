package com.gosuncn.test.openfire.adapter;

import android.content.Context;

import com.gosuncn.core.adapter.CommonAdapter;
import com.gosuncn.core.adapter.ViewHolder;
import com.gosuncn.test.R;

import org.jivesoftware.smack.roster.RosterEntry;

import java.util.List;

/**
 * Created by hwj on 2016/6/2.
 */
public class ContactAdapter extends CommonAdapter<RosterEntry> {
    public ContactAdapter(Context context, List<RosterEntry> list, int layoutId) {
        super(context, list, layoutId);
    }

    /**
     * 设置控件的显示内容
     *
     * @param viewHolder
     * @param s
     */
    @Override
    public void convert(ViewHolder viewHolder, RosterEntry s) {

        viewHolder.setText(R.id.tv_item_user,s.getUser()+"("+s.getName()+")")
                .setText(R.id.tv_item_status,"status:"+s.getStatus()+",type:"+s.getType());
    }
}

package com.sora.projectn.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.sora.projectn.R;
import com.sora.projectn.model.vo.AllPlayerInfoVo;

import java.util.List;

/**
 * Created by Sora on 2016/2/20.
 */
public class PlayerListAdapter extends BaseAdapter {

    private List<AllPlayerInfoVo> list;
    LayoutInflater inflater;

    public PlayerListAdapter(Context context, List<AllPlayerInfoVo> list) {
        inflater = LayoutInflater.from(context);
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        View view = null;
        if (convertView == null || convertView.getTag() == null){
            view = inflater.inflate(R.layout.item_teamseason,null);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        }
        else{
            view = convertView;
            viewHolder = (ViewHolder) convertView.getTag();
        }

        AllPlayerInfoVo vo = (AllPlayerInfoVo) getItem(position);


        viewHolder.item_entry.setText(position + 1);
        viewHolder.item_data1.setText(vo.getName());
        viewHolder.item_data2.setText(vo.getTeam());

        return view;
    }

    class ViewHolder{
        TextView item_entry;
        TextView item_data1;
        TextView item_data2;

        public ViewHolder(View view) {
            this.item_entry = (TextView) view.findViewById(R.id.item_entry);
            this.item_data1 = (TextView) view.findViewById(R.id.item_data1);
            this.item_data2 = (TextView) view.findViewById(R.id.item_data2);
        }
    }
}

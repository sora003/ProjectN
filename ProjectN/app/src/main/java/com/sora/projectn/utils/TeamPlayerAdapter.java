package com.sora.projectn.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.sora.projectn.R;
import com.sora.projectn.gc.model.vo.TeamPlayerVo;

import java.util.List;

/**
 * Created by Sora on 2016/2/5.
 */
public class TeamPlayerAdapter extends BaseAdapter {
    private List<TeamPlayerVo> list;
    LayoutInflater inflater;

    public TeamPlayerAdapter(Context context, List<TeamPlayerVo> list) {
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

        TeamPlayerVo vo = (TeamPlayerVo) getItem(position);

        //Error:android.content.res.Resources$NotFoundException: String resource ID #0x0
        //把一个int型业务数据的 设置setText（）或者类似的方法中 ,这样Android系统就会主动去资源文件当中寻找, 但是它不是一个资源文件ID, 所以就会报出这个bug  需要将int型业务数据 转换成String类型
        viewHolder.item_entry.setText(String.valueOf(vo.getNo()));
        viewHolder.item_data1.setText(vo.getName());
        viewHolder.item_data2.setText(vo.getPos());

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

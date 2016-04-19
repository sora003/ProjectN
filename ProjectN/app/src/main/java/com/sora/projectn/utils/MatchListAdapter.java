package com.sora.projectn.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.sora.projectn.R;
import com.sora.projectn.utils.beans.MatchInfoVo;

import java.util.List;

/**
 * Created by Sora on 2016/2/7.
 */
public class MatchListAdapter extends BaseAdapter{
    private List<MatchInfoVo> list;
    LayoutInflater inflater;

    public MatchListAdapter(Context context, List<MatchInfoVo> list) {
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
            view = inflater.inflate(R.layout.item_matchlist,null);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        }
        else{
            view = convertView;
            viewHolder = (ViewHolder) convertView.getTag();
        }

        MatchInfoVo vo = (MatchInfoVo) getItem(position);

        String team = vo.getTeamAName() + "  VS  " + vo.getTeamBName();
        String score  = vo.getScoreA() + " : " + vo.getScoreB();


        viewHolder.tv_combatTeam.setText(team);
        viewHolder.tv_combatScore.setText(score);

        return view;
    }

    class ViewHolder{
        TextView tv_combatTeam;
        TextView tv_combatScore;

        public ViewHolder(View view) {
            this.tv_combatTeam = (TextView) view.findViewById(R.id.tv_combatTeam);
            this.tv_combatScore = (TextView) view.findViewById(R.id.tv_combatScore);
        }
    }
}

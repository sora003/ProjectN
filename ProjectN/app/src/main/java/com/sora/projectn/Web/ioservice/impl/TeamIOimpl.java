package com.sora.projectn.Web.ioservice.impl;

import android.os.Environment;
import android.widget.Toast;

import com.sora.projectn.Web.dataservice.TeamDS;
import com.sora.projectn.Web.dataservice.impl.TeamData;
import com.sora.projectn.Web.ioservice.TeamIO;
import com.sora.projectn.Web.parser.TeamParser;
import com.sora.projectn.Web.parser.impl.TeamParserImpl;
import com.sora.projectn.po.TeamPo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.List;

/**
 * Created by Sora on 2016/1/17.
 *
 */
public class TeamIOimpl implements TeamIO{



    @Override
    public void saveTeamList() {

        //调用TeamDS接口 获取爬取数据
        TeamDS teamDS = new TeamData();
        StringBuffer result = teamDS.getTeamList();


        //调用TeamParser接口 获取球队基本数据 List
        TeamParser teamParser = new TeamParserImpl();
        List<TeamPo> list = teamParser.parseTeamList(result);


        JSONObject jsonObject= new JSONObject();
        JSONArray jsonArray = new JSONArray();

        for (int i = 0; i < list.size(); i++) {
            JSONObject temp = new JSONObject();
            try {
                temp.put("abbr",list.get(i).getAbbr());
                temp.put("name",list.get(i).getName());
                temp.put("founded",list.get(i).getFounded());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            jsonArray.put(temp);
        }
        try {
            jsonObject.put("TeamList",jsonArray);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        //判断SD卡是否存在
        //TODO 可能后期会产生bug
        if (!Environment.getExternalStorageDirectory().equals(Environment.MEDIA_MOUNTED)){
            return;
        }
        //设置文件路径  SDCard/NBADATA/Team/TeamList
        File file = new File(Environment.getExternalStorageDirectory().toString() + File.separator + "NBADATA" + File.separator + "Team" + File.separator + "TeamList");
        //判断文件是否存在 若不存在 则创建文件
        if (!file.getParentFile().exists()){
            file.getParentFile().mkdir();
        }
        //打印流
        PrintStream out = null;
        //输出JSONObject到文件中
        try {
            out = new PrintStream(new FileOutputStream(file));
            out.print(jsonObject.toString());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            //如果打印流不为空 关闭打印流
            if (out != null){
                out.close();
            }
        }

    }
}

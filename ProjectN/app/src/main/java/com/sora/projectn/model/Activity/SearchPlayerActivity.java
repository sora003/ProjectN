package com.sora.projectn.model.Activity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;

import com.sora.projectn.R;
import com.sora.projectn.utils.Consts;
import com.sora.projectn.utils.MyLexical;
import com.sora.projectn.utils.MySyntax;
import com.sora.projectn.utils.beans.SearchPlayerInfo;

import android.app.AlertDialog;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

public class SearchPlayerActivity extends Activity {

    private SQLiteDatabase dbRead,dbWrite,dbRead2;
    private Intent intent;
    private ArrayList<SearchPlayerInfo> players = new ArrayList<SearchPlayerInfo>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_player);


        final EditText customParameterName = (EditText) findViewById(R.id.customParameterName);
        final EditText customFunction = (EditText) findViewById(R.id.customFunction);
        Button btnSubmitFunction = (Button) findViewById(R.id.submitFunction);

        btnSubmitFunction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (customParameterName.getText().toString().isEmpty()) {
                    new AlertDialog.Builder(SearchPlayerActivity.this).setTitle("提醒").setMessage("自定义参数名不能为空").setNegativeButton("确定", null).show();
                } else {

                    loadPlayerIdData();
                    String input=customFunction.getText().toString();
                    //最末尾加一个空作为结束
                    input=input+" ";
                    String token = (new MyLexical()).getToken(input);
                    if(token.contains("error")) {
                        new AlertDialog.Builder(SearchPlayerActivity.this).setTitle("提醒").setMessage("存在不合法字符"+token).setNegativeButton("确定", null).show();
                    }else{
                        String parseResult=(new MySyntax()).parseSentence(token);
                        if(parseResult.contains("ERROR")){
                            new AlertDialog.Builder(SearchPlayerActivity.this).setTitle("提醒").setMessage("语法错误"+parseResult).setNegativeButton("确定", null).show();
                        }
                    }
                    //loadAllPlayerInfo();
                }

            }
        });


    }


//    private String getTime(){
//        SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
//        Date curDate = new Date();
//        String str = format.format(curDate);
//        return str;
//
//    }

    private void loadPlayerIdData() {

        new AsyncTask<Void, Void, String>() {

            @Override
            protected String doInBackground(Void... voids) {

                try {
                    BufferedReader br = new BufferedReader(new InputStreamReader(new URL("http://172.17.142.40:8080/NBADataSystem/getTeamPlayerList.do?teamId=10").openStream(), "utf-8"));

                    String line = null;
                    StringBuffer content = new StringBuffer();

                    while ((line = br.readLine()) != null) {
                        content.append(line);
                    }
                    br.close();
                    return content.toString();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                return null;
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);

                if (s != null) {
                    try {
                        JSONArray jsonArray = new JSONArray(s);
                        String idList="";
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject JO = jsonArray.getJSONObject(i);
                            SearchPlayerInfo player = new SearchPlayerInfo();
                            player.setPlayerId(JO.getInt("id"));
                            idList=idList+ JO.getInt("id")+"\n";
                            players.add(player);
                        }

                        TextView list = (TextView) findViewById(R.id.searchPlayerList);
                        list.setText(idList);

                        loadAllPlayerInfo();





                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }

            }
        }.execute();
    }

    private void loadPlayerInfoByPlayerId(final int playerId){
        new AsyncTask<Void,Void,String>(){
            @Override
            protected String doInBackground(Void... voids) {

                try {

                    BufferedReader br = new BufferedReader(new InputStreamReader(new URL(Consts.getPlayerSeasonStatistics + playerId).openStream() ,"utf-8"));

                    String line=null;
                    StringBuffer content = new StringBuffer();

                    while((line=br.readLine())!=null){
                        content.append(line);
                    }
                    br.close();
                    return content.toString();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                return null;


            }


            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);

                if (s!=null){
                    try {
                        JSONObject obj = new JSONObject(s);
                        SearchPlayerInfo player = getPlayerByPlayerId(playerId);
                        player.setIsFirst(obj.getInt("isFirst"));
                        player.setTotalMatches(obj.getInt("totalMatches"));
                        player.setTime(obj.getDouble("time"));
                        player.setTwoHit(obj.getDouble("twoHit"));
                        player.setThreeHit(obj.getDouble("threeHit"));
                        player.setTwoShot(obj.getDouble("twoShot"));
                        player.setThreeShot(obj.getDouble("threeShot"));
                        player.setFreeThrowHit(obj.getDouble("freeThrowHit"));
                        player.setFreeThrowShot(obj.getDouble("freeThrowShot"));
                        player.setOffReb(obj.getDouble("offReb"));
                        player.setDefReb(obj.getDouble("defReb"));
                        player.setTotReb(obj.getDouble("totReb"));
                        player.setAss(obj.getDouble("ass"));
                        player.setSteal(obj.getDouble("steal"));
                        player.setBlockShot(obj.getDouble("blockShot"));
                        player.setTurnOver(obj.getDouble("turnOver"));
                        player.setFoul(obj.getDouble("foul"));
                        player.setScore(obj.getDouble("score"));

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }
        }.execute();

    }

    private void loadAllPlayerInfo(){

        for(int i=0; i<players.size(); i++){
            loadPlayerInfoByPlayerId(players.get(i).getPlayerId());
        }

        for(int i=0; i<players.size(); i++){
            System.out.println(players.get(i).getBlockShot()+"xxXX");
        }

    }

    private SearchPlayerInfo getPlayerByPlayerId(int playerId){
        int i=0;
        for (i=0; i<players.size(); i++){
            if(players.get(i).getPlayerId()==playerId){
                return players.get(i);
            }
        }
        return null;
    }


}

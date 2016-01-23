package com.sora.projectn.Web.webDS;

import android.graphics.Bitmap;

import java.util.List;
import java.util.Map;

/**
 * Created by Sora on 2016/1/16.
 */
public interface TeamWDS {

    /**
     *
     * @return 球队基本数据.html
     */
    public StringBuffer getTeamListFromWeb();


    /**
     * 爬取球队logo 返回Map<String, Bitmap>
     *
     * @param list
     * @return Map<String, Bitmap>
     *     key      -   abbr 球队缩写
     *     value    -   Bitmap 球队Logo
     */
    public Map<String, Bitmap> getTeamLogoFromWeb(List<String> list);

}

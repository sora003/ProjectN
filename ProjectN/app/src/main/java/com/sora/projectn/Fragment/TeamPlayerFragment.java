package com.sora.projectn.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.sora.projectn.R;
import com.sora.projectn.businesslogic.TeamBL;
import com.sora.projectn.businesslogicservice.TeamBLS;
import com.sora.projectn.utils.TeamAdapter;
import com.sora.projectn.vo.TeamSeasonInfoVo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sora on 2016/1/28.
 */
public class TeamPlayerFragment extends Fragment {



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_teamseason,container,false);




        return view;

    }
}
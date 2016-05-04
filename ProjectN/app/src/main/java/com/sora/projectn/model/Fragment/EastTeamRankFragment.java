package com.sora.projectn.model.Fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.sora.projectn.R;
import com.sora.projectn.model.Activity.TeamActivity;
import com.sora.projectn.utils.TeamRankAdapter;

import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link EastTeamRankFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link EastTeamRankFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EastTeamRankFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private ListView lv;
    private List<Map<String, String>> ranks;

    private OnFragmentInteractionListener mListener;


    public EastTeamRankFragment(){

    }

    public EastTeamRankFragment(List<Map<String, String>> ranks) {
        // Required empty public constructor
        this.ranks = ranks;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment TeamRankFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static EastTeamRankFragment newInstance(List<Map<String, String>> ranks) {
        EastTeamRankFragment fragment = new EastTeamRankFragment(ranks);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_east_teamrank, container, false);
        lv = (ListView)view.findViewById(R.id.east_team_rank_lv);
        TeamRankAdapter tradapter = new TeamRankAdapter(this.ranks,this.getContext());
        lv.setAdapter(tradapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getContext(), TeamActivity.class);
                intent.putExtra("id",Integer.parseInt(ranks.get(position).get("teamID")));
                startActivity(intent);
            }
        });
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}

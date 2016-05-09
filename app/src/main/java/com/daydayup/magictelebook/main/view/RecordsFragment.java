package com.daydayup.magictelebook.main.view;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.daydayup.magictelebook.R;
import com.daydayup.magictelebook.main.adpter.RecordAdapter;
import com.daydayup.magictelebook.main.bean.Record;
import com.daydayup.magictelebook.main.presenter.MainPresenter;
import com.daydayup.magictelebook.util.L;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class RecordsFragment extends Fragment {
    //view
    private RecyclerView recyclerView;
    private List<Record> mRecords = new ArrayList<>();
    private RecordAdapter mAapter;
    public static final int NUM = 15;

    public RecordsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_records, container, false);
        initRecords();
        mAapter = new RecordAdapter(mRecords);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAapter);
        //MainPresenter.getInstance(getActivity(), (IMainView) getActivity()).initRecords(NUM);
        return view;
    }

    /*
     * 初始化通话记录数据
     */
    public void initRecords() {
        L.d("initRecords");
        Record[] record = new Record[10];
        for(int i = 0;i < 9; i = i + 3){
            record[i] = new Record("1","尚骏远","18986311211","来电","湖北武汉","16/05/08", R.mipmap.touxiang);
            record[i+1] = new Record("2","闫航宇","18508259573","去电","四川遂宁","16/05/09",R.mipmap.touxiang2);
            record[i+2] = new Record("3","暮春生","13778732128","未接","北京海淀","16/05/09",R.mipmap.touxiang1);
            mRecords.add(record[i]);
            mRecords.add(record[i+1]);
            mRecords.add(record[i+2]);
        }
    }

    /*
     *初始化最近的一条记录
     */
    private void initFirstRecords() {

    }

}

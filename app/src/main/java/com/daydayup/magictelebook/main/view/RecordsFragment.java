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
    public static final int NUM = 12;

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
        recyclerView = (RecyclerView) view.findViewById(R.id.records_recyclerView);
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
        Record[] record = new Record[NUM];
        record[0] = new Record("1","尚骏远","18986311211","来电","湖北武汉","16/05/08", R.mipmap.touxiang);
        record[1] = new Record("2","闫航宇","18508259573","去电","四川遂宁","16/05/09",R.mipmap.touxiang2);
        record[2] = new Record("3","暮春生","13778732128","未接","北京海淀","16/05/09",R.mipmap.touxiang1);
        record[3] = new Record("4","春夏","13256122119","来电","湖南长沙","16/05/10",R.mipmap.touxiang3);
        record[4] = new Record("5","闻人羽","15821734532","去电","四川成都","16/05/10",R.mipmap.touxiang4);
        record[5] = new Record("6","乐无异","13343561221","未接","上海","16/05/10",R.mipmap.touxiang5);
        record[6] = new Record("7","夏夷则","18595652345","来电","重庆","16/05/10", R.mipmap.touxiang6);
        record[7] = new Record("8","文钦","13782596233","去电","江苏南京","16/05/10",R.mipmap.touxiang7);
        record[8] = new Record("9","云清风","13778732128","未接","浙江杭州","16/05/10",R.mipmap.touxiang8);
        record[9] = new Record("10","越琦","15945432678","来电","广东广州","16/05/10",R.mipmap.touxiang9);
        record[10] = new Record("11","刘清扬","15895484532","去电","云南丽江","16/05/10",R.mipmap.touxiang10);
        record[11] = new Record("12","周笙歌","13343561221","未接","山东济南","16/05/10",R.mipmap.touxiang11);
        mRecords.add(record[6]);
        mRecords.add(record[7]);
        mRecords.add(record[8]);
        mRecords.add(record[9]);
        mRecords.add(record[10]);
        mRecords.add(record[11]);
        mRecords.add(record[0]);
        mRecords.add(record[1]);
        mRecords.add(record[2]);
        mRecords.add(record[3]);
        mRecords.add(record[4]);
        mRecords.add(record[5]);

        //Record record1 = new Record("","","","","","",R.mipmap.ic_launcher);
        //mRecords.add(record1);
    }

}

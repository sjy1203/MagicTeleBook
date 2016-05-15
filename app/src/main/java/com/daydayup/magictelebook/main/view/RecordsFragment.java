package com.daydayup.magictelebook.main.view;


import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.daydayup.magictelebook.R;
import com.daydayup.magictelebook.main.adpter.RecordAdapter;
import com.daydayup.magictelebook.main.bean.Record;
import com.daydayup.magictelebook.util.L;

import java.util.ArrayList;
import java.util.List;

import info.hoang8f.android.segmented.SegmentedGroup;

/**
 * A simple {@link Fragment} subclass.
 */
public class RecordsFragment extends Fragment {
    //view
    private RecyclerView recyclerView;
    private List<Record> mRecords = new ArrayList<>();
    private RecordAdapter mAapter;
    private RelativeLayout search;
    public static final int NUM = 12;

    public RecordsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_records, container, false);
        initRecords();
        mAapter = new RecordAdapter(mRecords);
        recyclerView = (RecyclerView) view.findViewById(R.id.records_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAapter);
        //MainPresenter.getInstance(getActivity(), (IMainView) getActivity()).initRecords(NUM);
        search = (RelativeLayout) view.findViewById(R.id.search_layout);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSearchWindow(container,view);
            }
        });
        return view;
    }

    private void showSearchWindow(ViewGroup parent,View view) {
        View SearchWindowView = LayoutInflater.from(parent.getContext()).inflate(R.layout.record_search,parent,false);
        final PopupWindow SearchPopupWindow = new PopupWindow(SearchWindowView,
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, true);
        TextView canclesearch = (TextView) SearchWindowView.findViewById(R.id.cancle_search);
        SegmentedGroup segmentedSearchGroup = (SegmentedGroup) SearchWindowView.findViewById(R.id.segmented4);
        segmentedSearchGroup.setTintColor(getResources().getColor(R.color.lightblue));
        canclesearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchPopupWindow.dismiss();
            }
        });
        SearchPopupWindow.setTouchable(true);

        SearchPopupWindow.setTouchInterceptor(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;
            }
        });

        ColorDrawable dw = new ColorDrawable(0xb0000000);
        SearchPopupWindow.setBackgroundDrawable(dw);

        SearchPopupWindow.showAtLocation(view, Gravity.TOP,0,0);
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

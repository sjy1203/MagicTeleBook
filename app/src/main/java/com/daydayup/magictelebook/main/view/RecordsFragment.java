package com.daydayup.magictelebook.main.view;


import android.os.Bundle;
import android.support.v4.app.Fragment;
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
        recyclerView.setLayoutManager(new LinearLayoutManager(container.getContext()));
        recyclerView.setAdapter(mAapter);

        return view;
    }

    public void initRecords() {
        L.d("initRecords");
        Record[] record = new Record[10];
        for(int i = 0;i < 10; i++){
            record[i] = new Record("尚骏远","18986311211","来电","湖北武汉","16/05/08",
                    R.mipmap.contact_type, R.mipmap.contact_area, R.mipmap.contact_time);
            mRecords.add(record[i]);
        }
    }

}

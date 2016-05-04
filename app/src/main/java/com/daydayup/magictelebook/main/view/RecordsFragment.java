package com.daydayup.magictelebook.main.view;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.daydayup.magictelebook.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class RecordsFragment extends Fragment {
    //view
    private RecyclerView recyclerView;
    public RecordsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_records, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        //recyclerView.setAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(container.getContext()));
        return view;
    }

}

package com.daydayup.magictelebook.main.view;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.daydayup.magictelebook.R;
import com.daydayup.magictelebook.main.adpter.RecordSearchAdapter;
import com.daydayup.magictelebook.main.bean.BriefContact;
import com.daydayup.magictelebook.main.bean.Record;
import com.daydayup.magictelebook.main.callback.OnRecordsInitListener;
import com.daydayup.magictelebook.main.presenter.MainPresenter;
import com.daydayup.magictelebook.util.L;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jay on 16/5/24.
 */
public class ContactRecordFragment extends Fragment implements View.OnClickListener{
    //listview
    private ListView records_search_listView;
    private RecordSearchAdapter recordSearchAdapter;
    private List<Record> searchRecords = new ArrayList<>();

    //view
    private TextView person_telno,person_area,person_birth;
    private ImageView phone,smg;

    //const
    public static final String KEY_BRIEFCONTACT = "briefContact";

    //bean
    private BriefContact briefContact;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        briefContact = (BriefContact) getArguments().getSerializable(KEY_BRIEFCONTACT);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contactrecord,container,false);
        //listview
        records_search_listView = (ListView) view.findViewById(R.id.records_search_listView);
        recordSearchAdapter = new RecordSearchAdapter(getActivity(),searchRecords);
        records_search_listView.setAdapter(recordSearchAdapter);
        initRecords(briefContact.getNumber());
        //view
        //textview
        person_area = (TextView) view.findViewById(R.id.person_area);
        person_telno = (TextView) view.findViewById(R.id.person_telno);
        person_birth = (TextView) view.findViewById(R.id.person_birth);

        //imageview
        phone = (ImageView) view.findViewById(R.id.phone);
        smg = (ImageView) view.findViewById(R.id.smg);
        phone.setOnClickListener(this);
        smg.setOnClickListener(this);

        //initial text
        person_area.setText(briefContact.getArea());
        person_telno.setText(briefContact.getNumber());
        person_birth.setText(briefContact.getBirth());
        return view;
    }

    private void initRecords(String searchNumber) {
        MainPresenter.getInstance(getActivity(), (IMainView) getActivity()).searchRecordsByNumber(searchNumber, new OnRecordsInitListener() {
            @Override
            public void onLoadSuccess(List<Record> recordList) {
                searchRecords.clear();
                searchRecords.addAll(recordList);
                recordSearchAdapter.notifyDataSetChanged();
                L.d("recordList size:"+recordList.size());
            }

            @Override
            public void onLoadFailed(String msg) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.smg:
                Uri uri = Uri.parse("smsto:" + briefContact.getNumber());
                Intent sendIntent = new Intent(Intent.ACTION_VIEW, uri);
                if (briefContact.getWeatherInfo()!=null)
                    sendIntent.putExtra("sms_body", briefContact.getWeatherInfo().getGanmao());
                else sendIntent.putExtra("sms_body","");
                startActivity(sendIntent);
                break;
            case R.id.phone:
                Intent callIntent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + briefContact.getNumber()));
                startActivity(callIntent);
                break;
        }
    }
}

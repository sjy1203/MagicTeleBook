package com.daydayup.magictelebook.main.view;


import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.daydayup.magictelebook.R;
import com.daydayup.magictelebook.main.adpter.RecordAdapter;
import com.daydayup.magictelebook.main.adpter.RecordSearchAdapter;
import com.daydayup.magictelebook.main.bean.BriefContact;
import com.daydayup.magictelebook.main.bean.Record;
import com.daydayup.magictelebook.main.callback.OnRecordsInitListener;
import com.daydayup.magictelebook.main.presenter.MainPresenter;
import com.daydayup.magictelebook.util.L;
import com.daydayup.magictelebook.util.T;

import java.util.ArrayList;
import java.util.List;

import info.hoang8f.android.segmented.SegmentedGroup;

/**
 * A simple {@link Fragment} subclass.
 */
public class RecordsFragment extends Fragment implements View.OnClickListener{
    //view
    private RecyclerView recyclerView;
    private RecordAdapter mAapter;
    private RelativeLayout search;
    private EditText search_et_input;
    private ListView records_search_listView;
    private RecordSearchAdapter recordSearchAdapter;

    //const
    public static final int NUM = 12;

    //List
    private List<Record> records = new ArrayList<>();
    private List<Record> mRecords = new ArrayList<>();
    private List<Record> searchRecords = new ArrayList<>();

    //searchOptions
    enum SearchOp{NULL,NAME,NUMBER,AREA,ALL};
    SearchOp searchOp = SearchOp.NULL;

    //flag
    boolean isLoadingMore = false;
    public RecordsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_records, container, false);
        mAapter = new RecordAdapter(records,getActivity());
        recyclerView = (RecyclerView) view.findViewById(R.id.records_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAapter);
        recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int lastPos = ((LinearLayoutManager)recyclerView.getLayoutManager()).findLastVisibleItemPosition();
                int itemAllCount = recyclerView.getLayoutManager().getItemCount();
                if (lastPos >= itemAllCount - 5 && dy > 0) {
                    if(isLoadingMore){

                    } else{
                        loadPage(itemAllCount);//这里多线程也要手动控制isLoadingMore
                    }
                }
            }
        });
        search = (RelativeLayout) view.findViewById(R.id.search_layout);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSearchWindow(container,view);
            }
        });
        initRecords();
        return view;
    }

    private void loadPage(int offset) {
        isLoadingMore = true;
        MainPresenter.getInstance(getActivity(), (IMainView) getActivity()).incrementRecords(offset, NUM, new OnRecordsInitListener() {
            @Override
            public void onLoadSuccess(List<Record> recordList) {
                isLoadingMore = false;
                records.addAll(recordList);
                mAapter.notifyDataSetChanged();
            }

            @Override
            public void onLoadFailed(String msg) {

            }
        });
    }


    private void showSearchWindow(ViewGroup parent, View view) {
        View SearchWindowView = LayoutInflater.from(parent.getContext()).inflate(R.layout.record_search,parent,false);
        final PopupWindow SearchPopupWindow = new PopupWindow(SearchWindowView,
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, true);
        TextView canclesearch = (TextView) SearchWindowView.findViewById(R.id.cancle_search);
        SegmentedGroup segmentedSearchGroup = (SegmentedGroup) SearchWindowView.findViewById(R.id.segmented4);
        segmentedSearchGroup.setTintColor(getResources().getColor(R.color.lightblue));

        Button search_name,search_telno,search_area,search_all;ImageView search_iv_delete;
        search_iv_delete = (ImageView) SearchWindowView.findViewById(R.id.search_iv_delete);
        search_name = (Button) SearchWindowView.findViewById(R.id.search_name);
        search_telno = (Button) SearchWindowView.findViewById(R.id.search_telno);
        search_area = (Button) SearchWindowView.findViewById(R.id.search_area);
        search_all = (Button) SearchWindowView.findViewById(R.id.search_all);
        search_name.setOnClickListener(this);
        search_telno.setOnClickListener(this);
        search_area.setOnClickListener(this);
        search_all.setOnClickListener(this);
        search_iv_delete.setClickable(true); search_iv_delete.setOnClickListener(this);


        search_et_input = (EditText) SearchWindowView.findViewById(R.id.search_et_input);

        records_search_listView = (ListView) SearchWindowView.findViewById(R.id.records_search_listView);
        recordSearchAdapter = new RecordSearchAdapter(getActivity(),searchRecords);
        records_search_listView.setAdapter(recordSearchAdapter);
        records_search_listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), ContactInfoMixActivity.class);
                BriefContact briefContact = new BriefContact();
                Record record = records.get(position);
                long rawContactid = MainPresenter.getInstance(getActivity(), (IMainView) getActivity()).getRawContactId(record.get_ID());
                L.d("rawContactId" + rawContactid);
                briefContact.setRawContactId(rawContactid);
                briefContact.setBlack(false);
                briefContact.setBirth("");
                briefContact.setNumber(record.getTelno());
                briefContact.setName(record.getName());
                briefContact.setArea(record.getArea());
                intent.putExtra(ContactRecordFragment.KEY_BRIEFCONTACT,briefContact);
                intent.putExtra(ContactInfoMixActivity.INTENT_STATUS,ContactInfoMixActivity.STATUS_RECORD);
                getActivity().startActivity(intent);
            }
        });


        search_et_input.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                L.d(s.toString());
                searchBySomething(s.toString());
            }
        });

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

        searchOp = SearchOp.NULL;

        SearchPopupWindow.showAtLocation(view, Gravity.TOP,0,0);
    }

    private void searchBySomething(String s) {
        OnRecordsInitListener onRecordsInitListener = new OnRecordsInitListener() {
            @Override
            public void onLoadSuccess(List<Record> recordList) {
                searchRecords.clear();
                searchRecords.addAll(recordList);
                recordSearchAdapter.notifyDataSetChanged();
                L.d("success recordlist size:"+recordList.size());
            }

            @Override
            public void onLoadFailed(String msg) {
                T.showShort(getActivity(),msg);
            }
        };
        switch (searchOp){
            case NAME:
                MainPresenter.getInstance(getActivity(), (IMainView) getActivity()).searchRecordsByName(s,onRecordsInitListener);
                break;
            case NUMBER:
                MainPresenter.getInstance(getActivity(), (IMainView) getActivity()).searchRecordsByNumber(s,onRecordsInitListener);
                break;
            case ALL:
                MainPresenter.getInstance(getActivity(), (IMainView) getActivity()).searchRecordsByAll(s,onRecordsInitListener);
                break;
        }
    }

    /*
     * 初始化通话记录数据
     */
    public void initRecords() {
        L.d("initRecords");
        MainPresenter.getInstance(getActivity(), (IMainView) getActivity()).initRecords(NUM,new OnRecordsInitListener() {
            @Override
            public void onLoadSuccess(final List<Record> recordList) {
//                List<Record> records = mainView.getRecordAdapter().getList();
//                records.clear();
//                L.d("initRecords","addList size:"+recordList.size());
//                records.addAll(recordList);
//                L.d("initRecords","records size:"+records.size());
//                mainView.getRecordAdapter().notifyDataSetChanged();
                records.clear();
                records.addAll(recordList);
                mAapter.notifyDataSetChanged();
            }

            @Override
            public void onLoadFailed(String msg) {
                T.showShort(getActivity(),msg);
            }
        });
        Record[] record = new Record[NUM];
        record[0] = new Record("1","尚骏远","18986311211","来电","湖北武汉","01:54","16/05/08",R.mipmap.touxiang);
        record[1] = new Record("2","闫航宇","18508259573","去电","四川遂宁","01:54","16/05/09",R.mipmap.touxiang2);
        record[2] = new Record("3","暮春生","13778732128","未接","北京海淀","01:54","16/05/09",R.mipmap.touxiang1);
        record[3] = new Record("4","春夏","13256122119","来电","湖南长沙","01:54","16/05/10",R.mipmap.touxiang3);
        record[4] = new Record("5","闻人羽","15821734532","去电","四川成都","01:54","16/05/10",R.mipmap.touxiang4);
        record[5] = new Record("6","乐无异","13343561221","未接","上海","01:54","16/05/10",R.mipmap.touxiang5);
        record[6] = new Record("7","夏夷则","18595652345","来电","重庆","01:54","16/05/10", R.mipmap.touxiang6);
        record[7] = new Record("8","文钦","13782596233","去电","江苏南京","01:54","16/05/10",R.mipmap.touxiang7);
        record[8] = new Record("9","云清风","13778732128","未接","浙江杭州","01:54","16/05/10",R.mipmap.touxiang8);
        record[9] = new Record("10","越琦","15945432678","来电","广东广州","01:54","16/05/10",R.mipmap.touxiang9);
        record[10] = new Record("11","刘清扬","15895484532","去电","云南丽江","01:54","16/05/10",R.mipmap.touxiang10);
        record[11] = new Record("12","周笙歌","13343561221","未接","山东济南","01:54","16/05/10",R.mipmap.touxiang11);
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
    public RecordAdapter getAdpter(){
        return mAapter;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.search_name:
                if (searchOp==SearchOp.NAME)
                    break;
                searchOp = SearchOp.NAME;
                searchBySomething(search_et_input.getText().toString());
                break;
            case R.id.search_telno:
                if (searchOp==SearchOp.NUMBER)
                    break;
                searchOp = SearchOp.NUMBER;
                searchBySomething(search_et_input.getText().toString());
                break;
            case R.id.search_all:
                if (searchOp==SearchOp.ALL)
                    break;
                searchOp = SearchOp.ALL;
                searchBySomething(search_et_input.getText().toString());
                break;
            case R.id.search_area:
                if (searchOp==SearchOp.AREA)
                    break;
                searchOp = SearchOp.AREA;
                searchBySomething(search_et_input.getText().toString());
                break;
            case R.id.search_iv_delete:
                search_et_input.setText("");
                searchRecords.clear();
                recordSearchAdapter.notifyDataSetChanged();
                break;
        }

    }
}

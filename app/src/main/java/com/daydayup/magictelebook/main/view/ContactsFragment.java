package com.daydayup.magictelebook.main.view;


import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.daydayup.magictelebook.R;
import com.daydayup.magictelebook.main.adpter.ContactAdapter;
import com.daydayup.magictelebook.main.bean.Contact;
import com.daydayup.magictelebook.util.L;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ContactsFragment extends Fragment {
    private RecyclerView recyclerView;
    private List<Contact> mContacts = new ArrayList<>();
    private ContactAdapter mAdapter;
    private FloatingActionButton mAddBtn;
    public static final int NUM = 12;

    public ContactsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_contacts, container, false);
        initContacts();
        mAdapter = new ContactAdapter(mContacts,getScreenWidth(getActivity()));
        recyclerView = (RecyclerView) view.findViewById(R.id.contacts_recyclerView);
        recyclerView.setAdapter(mAdapter);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL));
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        mAddBtn = (FloatingActionButton) view.findViewById(R.id.add_btn);
        mAddBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                L.d("add is clicked");
            }
        });
        return view;
    }

    private void initContacts() {
        Contact[] contact = new Contact[NUM];
        contact[0] = new Contact("1","尚骏远","18986311211","湖北武汉","多云 27º", R.mipmap.touxiang);
        contact[1] = new Contact("2","闫航宇","18508259573","四川遂宁","阵雨 24º",R.mipmap.touxiang2);
        contact[2] = new Contact("3","暮春生","13778732128","北京海淀","晴 30º",R.mipmap.touxiang1);
        contact[3] = new Contact("4","春夏","13256122119","湖南长沙","阴 25º",R.mipmap.touxiang3);
        contact[4] = new Contact("5","闻人羽","15821734532","四川成都","多云 30º",R.mipmap.touxiang4);
        contact[5] = new Contact("6","乐无异","13343561221","上海","大雨 23º",R.mipmap.touxiang5);
        contact[6] = new Contact("7","夏夷则","18595652345","重庆","多云 27º", R.mipmap.touxiang6);
        contact[7] = new Contact("8","文钦","13782596233","江苏南京","小雨 24º",R.mipmap.touxiang7);
        contact[8] = new Contact("9","云清风","13778732128","浙江杭州","晴 30º",R.mipmap.touxiang8);
        contact[9] = new Contact("10","越琦","15945432678","广东广州","大雨 25º",R.mipmap.touxiang9);
        contact[10] = new Contact("11","刘清扬","15895484532","云南丽江","晴 24º",R.mipmap.touxiang10);
        contact[11] = new Contact("12","周笙歌","13343561221","山东济南","晴 29º",R.mipmap.touxiang11);
        mContacts.add(contact[0]);
        mContacts.add(contact[1]);
        mContacts.add(contact[2]);
        mContacts.add(contact[3]);
        mContacts.add(contact[4]);
        mContacts.add(contact[5]);
        mContacts.add(contact[6]);
        mContacts.add(contact[7]);
        mContacts.add(contact[8]);
        mContacts.add(contact[9]);
        mContacts.add(contact[10]);
        mContacts.add(contact[11]);

        Contact contacttest = new Contact("12","尚骏远","18986311211","湖北武汉","多云 27º", R.mipmap.touxiang);
        mContacts.add(contacttest);
    }

    public static int getScreenWidth(Context context) {
        WindowManager manager = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        Display display = manager.getDefaultDisplay();
        return display.getWidth();
    }
}

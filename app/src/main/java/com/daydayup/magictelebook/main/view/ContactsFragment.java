package com.daydayup.magictelebook.main.view;


import android.content.Context;
import android.content.Intent;
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
import android.widget.RadioGroup;

import com.daydayup.magictelebook.R;
import com.daydayup.magictelebook.main.adpter.ContactAdapter;
import com.daydayup.magictelebook.main.bean.BriefContact;
import com.daydayup.magictelebook.main.bean.Contact;
import com.daydayup.magictelebook.main.callback.OnBriefContactsInitListener;
import com.daydayup.magictelebook.main.presenter.MainPresenter;
import com.daydayup.magictelebook.util.L;
import com.daydayup.magictelebook.util.T;

import java.util.ArrayList;
import java.util.List;

import info.hoang8f.android.segmented.SegmentedGroup;

/**
 * A simple {@link Fragment} subclass.
 */
public class ContactsFragment extends Fragment {
    private RecyclerView ContactrecyclerView;
    private RecyclerView BlackrecyclerView;
    private SegmentedGroup segmented2;
    private List<BriefContact> mContacts = new ArrayList<>();
    private List<BriefContact> mBlacks = new ArrayList<>();
    private ContactAdapter mContactAdapter;
    private ContactAdapter mBlackAdapter;
    private FloatingActionButton mAddBtn;
    public static final int NUM = 12;

    public ContactsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_contacts, container, false);

        mContactAdapter = new ContactAdapter(mContacts,getScreenWidth(getActivity()),getActivity());
        mBlackAdapter = new ContactAdapter(mBlacks,getScreenWidth(getActivity()),getActivity());

        ContactrecyclerView = (RecyclerView) view.findViewById(R.id.contacts_recyclerView);
        ContactrecyclerView.setAdapter(mContactAdapter);
        ContactrecyclerView.setLayoutManager(new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL));
        ContactrecyclerView.setHasFixedSize(true);
        ContactrecyclerView.setItemAnimator(new DefaultItemAnimator());

        segmented2 = (SegmentedGroup) view.findViewById(R.id.segmented2);
        segmented2.setTintColor(getResources().getColor(R.color.lightblue));
        segmented2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (group.getCheckedRadioButtonId()){
                    case R.id.Contactlist:
                        ContactrecyclerView = (RecyclerView) view.findViewById(R.id.contacts_recyclerView);
                        ContactrecyclerView.setAdapter(mContactAdapter);
                        ContactrecyclerView.setLayoutManager(new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL));
                        ContactrecyclerView.setHasFixedSize(true);
                        ContactrecyclerView.setItemAnimator(new DefaultItemAnimator());
                        break;
                    case R.id.BlackList:
                        BlackrecyclerView = (RecyclerView) view.findViewById(R.id.contacts_recyclerView);
                        BlackrecyclerView.setAdapter(mBlackAdapter);
                        BlackrecyclerView.setLayoutManager(new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL));
                        BlackrecyclerView.setHasFixedSize(true);
                        BlackrecyclerView.setItemAnimator(new DefaultItemAnimator());
                        break;
                }
            }
        });


        mAddBtn = (FloatingActionButton) view.findViewById(R.id.add_btn);
        mAddBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),ContactInfoMixActivity.class);
                intent.putExtra(ContactInfoMixActivity.INTENT_STATUS,ContactInfoMixActivity.STATUS_EDIT);
                startActivity(intent);
            }
        });
        initContacts();
        return view;
    }


    private void initContacts() {
        MainPresenter.getInstance(getActivity(), (IMainView) getActivity()).initBriefContacts(NUM, new OnBriefContactsInitListener() {
            @Override
            public void onLoadSuccess(List<BriefContact> contactList) {
                mBlacks.clear();
                mContacts.clear();
                for (BriefContact briefContact:contactList){
                    L.d(briefContact.toString());
                    if (briefContact.isBlack()){
                        mBlacks.add(briefContact);
                    }else{
                        mContacts.add(briefContact);
                    }
                }
                mBlackAdapter.notifyDataSetChanged();
                mContactAdapter.notifyDataSetChanged();
            }

            @Override
            public void onLoadFailed(String msg) {
                T.showShort(getActivity(),msg);

            }
        });
//        Contact[] contact = new Contact[NUM];
//        contact[0] = new Contact("1","尚骏远","18986311211","湖北武汉","局部多云","27º",false, R.mipmap.touxiang);
//        contact[1] = new Contact("2","闫航宇","18508259573","四川遂宁","阵雨","24º",false,R.mipmap.touxiang2);
//        contact[2] = new Contact("3","暮春生","13778732128","北京海淀","晴转多云","30º",false,R.mipmap.touxiang1);
//        contact[3] = new Contact("4","春夏","13256122119","湖南长沙","阴转阵雨","25º",true,R.mipmap.touxiang3);
//        contact[4] = new Contact("5","闻人羽","15821734532","四川成都","多云","30º",false,R.mipmap.touxiang4);
//        contact[5] = new Contact("6","乐无异","13343561221","上海","大雨转晴","23º",false,R.mipmap.touxiang5);
//        contact[6] = new Contact("7","夏夷则","18595652345","重庆","多云","27º",true,R.mipmap.touxiang6);
//        contact[7] = new Contact("8","文钦","13782596233","江苏南京","小雨转多云","24º",false,R.mipmap.touxiang7);
//        contact[8] = new Contact("9","云清风","13778732128","浙江杭州","晴","30º",false,R.mipmap.touxiang8);
//        contact[9] = new Contact("10","越琦","15945432678","广东广州","大雨","25º",false,R.mipmap.touxiang9);
//        contact[10] = new Contact("11","刘清扬","15895484532","云南丽江","多云转晴","24º",true,R.mipmap.touxiang10);
//        contact[11] = new Contact("12","周笙歌","13343561221","山东济南","晴","29º",false,R.mipmap.touxiang11);
//        //initContacts
//        for(int i = 0;i < NUM; i++)
//        {
//            if(contact[i].getBlack())
//                mBlacks.add(contact[i]);
//            else
//                mContacts.add(contact[i]);
//        }
//        /*mContacts.add(contact[0]);
//        mContacts.add(contact[1]);
//        mContacts.add(contact[2]);
//        mContacts.add(contact[3]);
//        mContacts.add(contact[4]);
//        mContacts.add(contact[5]);
//        mContacts.add(contact[6]);
//        mContacts.add(contact[7]);
//        mContacts.add(contact[8]);
//        mContacts.add(contact[9]);
//        mContacts.add(contact[10]);
//        mContacts.add(contact[11]);*/
//        Contact contacttest = new Contact("12","尚骏远","18986311211","内蒙古鄂尔多斯","多云转晴","27º",false,R.mipmap.touxiang);
//        mContacts.add(contacttest);
//        /*initBlacks
//        mBlacks.add(contact[7]);
//        mBlacks.add(contact[8]);
//        mBlacks.add(contact[9]);
//        mBlacks.add(contact[10]);
//        mBlacks.add(contact[11]);*/11
    }

    public static int getScreenWidth(Context context) {
        WindowManager manager = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        Display display = manager.getDefaultDisplay();
        return display.getWidth();
    }
}

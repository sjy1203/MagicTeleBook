package com.daydayup.magictelebook.main.view;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.daydayup.magictelebook.R;
import com.daydayup.magictelebook.main.bean.BriefContact;
import com.daydayup.magictelebook.util.CustomDialog;

/**
 * Created by Jay on 16/5/24.
 */
public class ContactEditFragment extends Fragment {
    //view
    private EditText person_telno,person_area,person_birth,person_name;
    private TextView IsBlack,deleteContact;
    //bean
    private BriefContact briefContact = null;
    //const
    public static final String KEY_BRIEFCONTACT = "briefContact";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        briefContact = (BriefContact) getArguments().getSerializable(KEY_BRIEFCONTACT);
        if (briefContact==null){
            briefContact = new BriefContact();
            briefContact.setBlack(false);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contactedit,container,false);
        person_telno = (EditText) view.findViewById(R.id.person_telno);
        person_area = (EditText) view.findViewById(R.id.person_area);
        person_birth = (EditText) view.findViewById(R.id.person_birth);
        person_name = (EditText) view.findViewById(R.id.person_name);

        person_name.setText(briefContact.getName());
        person_telno.setText(briefContact.getNumber());
        person_birth.setText(briefContact.getBirth());
        person_area.setText(briefContact.getArea());

        IsBlack = (TextView) view.findViewById(R.id.addtoblack);
        deleteContact = (TextView) view.findViewById(R.id.deleteContact);

        if(briefContact.isBlack()){
            IsBlack.setText("解除黑名单");
            IsBlack.setTextColor(getResources().getColor(R.color.lightblue));
        }else{
            IsBlack.setText("加入黑名单");
            IsBlack.setTextColor(getResources().getColor(R.color.red));
        }

        IsBlack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(IsBlack.getText().equals("加入黑名单")){
                    final CustomDialog.Builder builder = new CustomDialog.Builder(getActivity());
                    builder.setTitle("提示");
                    builder.setMessage("确定加入黑名单吗？");
                    builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            briefContact.setBlack(true);
                            IsBlack.setText("解除黑名单");
                            IsBlack.setTextColor(getResources().getColor(R.color.lightblue));
                            dialog.dismiss();
                        }
                    });
                    builder.create().show();

                }else if(IsBlack.getText().equals("解除黑名单")){
                    CustomDialog.Builder builder = new CustomDialog.Builder(getActivity());
                    builder.setTitle("提示");
                    builder.setMessage("确定解除黑名单吗？");
                    builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            briefContact.setBlack(false);
                            IsBlack.setText("加入黑名单");
                            IsBlack.setTextColor(getResources().getColor(R.color.red));
                            dialog.dismiss();
                        }
                    });
                    builder.create().show();
                }

            }
        });

        return view;
    }

    public BriefContact commit(){

        briefContact.setName(person_name.getText().toString());
        briefContact.setNumber(person_telno.getText().toString());
        briefContact.setArea(person_area.getText().toString());
        briefContact.setBirth(person_birth.getText().toString());
        //TODO:
        return briefContact;
    }

}

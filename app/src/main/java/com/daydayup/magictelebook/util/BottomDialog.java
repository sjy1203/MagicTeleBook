package com.daydayup.magictelebook.util;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.daydayup.magictelebook.R;

/**
 * Created by Jallen on 2016/5/22.
 */
public class BottomDialog extends Dialog{
    public BottomDialog(Context context) {
        super(context);
    }

    public BottomDialog(Context context, int themeResId) {
        super(context, themeResId);
    }
    public static class Builder {
        private Context context;
        private String item1;
        private String item2;
        private String action;
        private View contentView;
        private DialogInterface.OnClickListener item1ClickListener;
        private DialogInterface.OnClickListener item2ClickListener;
        private DialogInterface.OnClickListener actionClickListener;

        public Builder(Context context) {
            this.context = context;
        }

        public Builder setContentView(View v) {
            this.contentView = v;
            return this;
        }

        public Builder setItem1Touch(String item1,
                                     DialogInterface.OnClickListener listener){
            this.item1 = item1;
            this.item1ClickListener = listener;
            return this;
        }

        public Builder setItem2Touch(String item2,
                                     DialogInterface.OnClickListener listener){
            this.item2 = item2;
            this.item2ClickListener = listener;
            return this;
        }
        public Builder setActionTouch(String action,
                                     DialogInterface.OnClickListener listener){
            this.action = action;
            this.actionClickListener = listener;
            return this;
        }


        public CustomDialog create() {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            // instantiate the dialog with the custom Theme
            final CustomDialog dialog = new CustomDialog(context, R.style.dialogstyle);
            View layout = inflater.inflate(R.layout.record_edit_1, null);
            dialog.addContentView(layout, new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            dialog.getWindow().setGravity(Gravity.BOTTOM);
            if (item1 != null) {
                ((TextView) layout.findViewById(R.id.item1)).setText(item1);
                if (item1ClickListener != null) {
                    (layout.findViewById(R.id.item1))
                            .setOnClickListener(new View.OnClickListener() {
                                public void onClick(View v) {
                                    item1ClickListener.onClick(dialog,
                                            DialogInterface.BUTTON_POSITIVE);
                                }
                            });
                }
            } else {
                // if no confirm button just set the visibility to GONE
                layout.findViewById(R.id.item1).setVisibility(
                        View.GONE);
            }
            if (item2 != null) {
                ((TextView) layout.findViewById(R.id.item2)).setText(item2);
                if (item2ClickListener != null) {
                    (layout.findViewById(R.id.item2))
                            .setOnClickListener(new View.OnClickListener() {
                                public void onClick(View v) {
                                    item2ClickListener.onClick(dialog,
                                            DialogInterface.BUTTON_POSITIVE);
                                }
                            });
                }
            } else {
                // if no confirm button just set the visibility to GONE
                layout.findViewById(R.id.item2).setVisibility(
                        View.GONE);
            }
            if (action != null) {
                ((TextView) layout.findViewById(R.id.action)).setText(action);
                if (actionClickListener != null) {
                    (layout.findViewById(R.id.action))
                            .setOnClickListener(new View.OnClickListener() {
                                public void onClick(View v) {
                                    actionClickListener.onClick(dialog,
                                            DialogInterface.BUTTON_NEGATIVE);
                                }
                            });
                }
            } else {
                // if no confirm button just set the visibility to GONE
                layout.findViewById(R.id.action).setVisibility(
                        View.GONE);
            }

            dialog.setContentView(layout);
            return dialog;
        }
    }

}

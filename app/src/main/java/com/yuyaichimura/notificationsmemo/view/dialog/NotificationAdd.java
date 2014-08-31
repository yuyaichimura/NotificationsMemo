package com.yuyaichimura.notificationsmemo.view.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.yuyaichimura.notificationsmemo.R;
import com.yuyaichimura.notificationsmemo.controller.Controller;
import com.yuyaichimura.notificationsmemo.view.NotificationList;

/**
 * Created by yuya on 7/23/14.
 */
public class NotificationAdd extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        final View view = inflater.inflate(R.layout.add_notification, null);

        builder.setView(view)
                .setTitle(R.string.dialog_title_add)
                .setPositiveButton(R.string.dialog_add, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setNegativeButton(R.string.dialog_cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

        return builder.create();
    }

    @Override
    public void onStart() {
        super.onStart();

        final AlertDialog aDialog = (AlertDialog)getDialog();
        if(aDialog != null){
            ((Button) aDialog.getButton(DialogInterface.BUTTON_POSITIVE)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    EditText tvTitle = (EditText) aDialog.findViewById(R.id.et_title);
                    EditText tvDesc = (EditText) aDialog.findViewById(R.id.et_desc);

                    String title = tvTitle.getText().toString();
                    String desc = tvDesc.getText().toString();

                    if (title.isEmpty() || title.length() == 0) {
                        Toast.makeText(getActivity(), R.string.warning_enter_title, Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(getActivity(), "Created Notification", Toast.LENGTH_SHORT).show();

                        addNewNotification(title, desc);

                        dismiss();
                    }
                }
            });
        }
    }

    void addNewNotification(String title, String description){

        Controller.addNotification(title, description);
        NotificationList.controller.refreshNotifications();
    }
}
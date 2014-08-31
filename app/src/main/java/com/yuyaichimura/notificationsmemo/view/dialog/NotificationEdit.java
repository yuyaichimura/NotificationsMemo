package com.yuyaichimura.notificationsmemo.view.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.yuyaichimura.notificationsmemo.R;
import com.yuyaichimura.notificationsmemo.controller.Controller;
import com.yuyaichimura.notificationsmemo.data.Notification;
import com.yuyaichimura.notificationsmemo.view.NotificationList;

/**
 * Created by yuya on 7/24/14.
 */
public class NotificationEdit extends DialogFragment {

    int pos = 0;

   /* public NotificationEdit(int pos){
        this.pos = pos;
    }*/

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        final View view = inflater.inflate(R.layout.add_notification, null);

        Notification n = Controller.getNotification(pos);
        ((EditText)view.findViewById(R.id.et_title)).setText(n.getTitle());
        ((EditText)view.findViewById(R.id.et_desc)).setText(n.getDescription());

        builder.setView(view)
                .setTitle(R.string.dialog_title_edit)
                .setPositiveButton(R.string.dialog_confirm, new DialogInterface.OnClickListener() {
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

        final AlertDialog aDialog;
        aDialog = (AlertDialog)getDialog();
        if(aDialog != null){
            (aDialog.getButton(DialogInterface.BUTTON_POSITIVE)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    EditText tvTitle = (EditText) aDialog.findViewById(R.id.et_title);
                    EditText tvDesc = (EditText) aDialog.findViewById(R.id.et_desc);

                    String title = tvTitle.getText().toString();
                    String desc = tvDesc.getText().toString();

                    if (title.isEmpty() || title.length() == 0) {
                        Toast.makeText(getActivity(), R.string.warning_empty_title, Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(getActivity(), "Created Notification", Toast.LENGTH_SHORT).show();

                        editNotification(title, desc);

                        dismiss();
                    }
                }
            });
        }
    }

    void editNotification(String title, String description){

        Controller.editNotification(title, description, pos);
        NotificationList.controller.refreshNotifications();
    }
}
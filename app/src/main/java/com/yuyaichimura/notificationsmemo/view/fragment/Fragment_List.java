package com.yuyaichimura.notificationsmemo.view.fragment;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.yuyaichimura.notificationsmemo.R;
import com.yuyaichimura.notificationsmemo.controller.Controller;
import com.yuyaichimura.notificationsmemo.data.Notification;
import com.yuyaichimura.notificationsmemo.view.NotificationList;

/**
 * Created by yuya on 7/23/14.
 */
public class Fragment_List  extends Fragment {

//    List<Notification> notificationList = new ArrayList<Notification>();
    ListView lvNotifications;

    public Fragment_List(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_notification_list, container, false);

        lvNotifications = (ListView)rootView.findViewById(R.id.lv_notifications);
        lvNotifications.setAdapter(Controller.getAdapter());
        lvNotifications.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent,
                                           View view, int position, long id) {

                showItemOptions(position);

                return true;
            }
        });

        NotificationList.controller.refreshNotifications();

        return rootView;
    }

    void showItemOptions(int position){

       final int pos = position;

       new AlertDialog.Builder(getActivity())

        .setTitle(R.string.dialog_title_option)
        .setItems(R.array.notification_option_choices, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
//                System.out.println(which);

                if (which == 1){
                    new AlertDialog.Builder(getActivity())

                            .setTitle(R.string.dialog_title_delete)
                            .setMessage(R.string.dialog_message_delete)
                            .setPositiveButton(R.string.dialog_confirm, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    Controller.deleteNotification(pos);
                                }
                            })
                            .setNegativeButton(R.string.dialog_cancel, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            })
                            .show();
                } else {
                    System.out.println("Editing");
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    LayoutInflater inflater = getActivity().getLayoutInflater();

                    final View view = inflater.inflate(R.layout.add_notification, null);

                    Notification n = Controller.getNotification(pos);
                    ((EditText)view.findViewById(R.id.et_title)).setText(n.getTitle());
                    ((EditText)view.findViewById(R.id.et_desc)).setText(n.getDescription());

                    final AlertDialog aDialog = builder.setView(view)
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
                            }).show();
                    aDialog.getButton(DialogInterface.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            EditText tvTitle = (EditText) view.findViewById(R.id.et_title);
                            EditText tvDesc = (EditText) view.findViewById(R.id.et_desc);

                            String title = tvTitle.getText().toString();
                            String desc = tvDesc.getText().toString();

                            if (title.isEmpty() || title.length() == 0) {
                                Toast.makeText(getActivity(), R.string.warning_enter_title, Toast.LENGTH_SHORT).show();

                            } else {
                                Toast.makeText(getActivity(), "edit Notification", Toast.LENGTH_SHORT).show();

                                editNotification(pos, title, desc);

//                                addNewNotification(title, desc);

                                aDialog.dismiss();
                            }
                        }
                    });
                }
            }
        }).show();
    }

    boolean confirmDialog(){

        final boolean[] retVal = new boolean[1];

        new AlertDialog.Builder(getActivity())

                .setTitle(R.string.dialog_title_delete)
                .setMessage(R.string.dialog_message_delete)
                .setPositiveButton(R.string.dialog_confirm, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        retVal[0] = true;
                        System.out.println("truu");
                    }
                })
                .setNegativeButton(R.string.dialog_cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        retVal[0] = false;
                        System.out.println("not truu");

                    }
                })
                .show();

        return retVal[0];
    }

    void editNotification(int pos, String title, String description){
        Controller.editNotification(title, description, pos);
    }
}
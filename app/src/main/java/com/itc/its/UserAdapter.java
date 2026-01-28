package com.itc.its;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class UserAdapter extends ArrayAdapter<UserInfo> {

    private Context context;
    private List<UserInfo> itemList;

    public UserAdapter(@NonNull Context context, int resource, @NonNull List<UserInfo> itemList) {
        super(context, resource, itemList);
        this.context = context;
        this.itemList = itemList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;

        // Inflate the custom list item layout if the view is null
        if (listItemView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            listItemView = inflater.inflate(R.layout.user_item_layout, parent, false);
        }

        // Get the current item
        UserInfo currentItem = itemList.get(position);

        // Find the TextViews in the custom layout
        TextView userFullName = listItemView.findViewById(R.id.user_fullname);
        TextView userId = listItemView.findViewById(R.id.user_id);
        TextView userName = listItemView.findViewById(R.id.user_name);
        // Set the text for each TextView
        userFullName.setText(currentItem.getUser_fullname());
        userId.setText(currentItem.getUser_id());
        userName.setText(currentItem.getUser_name());
        return listItemView;
    }

}

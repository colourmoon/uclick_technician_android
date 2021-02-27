package com.uclick.technician.Notification;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.uclick.technician.R;

import java.util.List;

public class Notification_Adapter extends RecyclerView.Adapter<Notification_Adapter.ViewHolder> {

    Context context;
    List<Notification_Model> list;

    Notification_Adapter(Context context,List<Notification_Model> list){
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.notification_recyclerview,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Notification_Model notificationModel = list.get(position);

        holder.notify_Text.setText(notificationModel.getNotify_Content());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView notify_Text;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            notify_Text = itemView.findViewById(R.id.notification_Content);

        }
    }
}

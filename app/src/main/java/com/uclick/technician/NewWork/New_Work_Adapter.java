package com.uclick.technician.NewWork;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.uclick.technician.R;

import java.util.List;

public class New_Work_Adapter extends RecyclerView.Adapter<New_Work_Adapter.ViewHolder> {
    /* Context context;
     List<New_Work_Model> list;


     New_Work_Adapter(Context context, List<New_Work_Model> list) {
         this.context = context;
         this.list = list;
     }
 */
    Context context;
    List<New_Work_Model> list;

    private onNewworkClick newworkClick;

    public interface onNewworkClick {
        void onItemClick(int position);
    }

    public void setNewworkClick(onNewworkClick onNewworkClick) {
        this.newworkClick = onNewworkClick;
    }

    New_Work_Adapter(Context context, List<New_Work_Model> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.newwork_recyclerview, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        New_Work_Model new_work_model = list.get(position);
        //viewHolder.service_order_id.setText(new_work_model.getService_order_id());
       // viewHolder.order_id.setText(new_work_model.getOrder_id());
       // viewHolder.technician_id.setText(new_work_model.getTechnician_id());
        viewHolder.name.setText(new_work_model.getUser_name());
        viewHolder.address.setText(new_work_model.getAddress());
        viewHolder.date.setText(new_work_model.getAppointment_date());
        viewHolder.time.setText(new_work_model.getTimings());
        viewHolder.work_status.setText(new_work_model.getWork_status());
        viewHolder.mobileNumber_view.setText(new_work_model.getMobile_number());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView service_order_id, order_id,technician_id,work_status,date,name,time,address,mobileNumber_view;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

          // service_order_id = itemView.findViewById(R.id.view_service_order_id);
           // order_id = itemView.findViewById(R.id.view_order_id);
           // technician_id = itemView.findViewById(R.id.view_technician_id);
            name = itemView.findViewById(R.id.view_name);
            address = itemView.findViewById(R.id.view_address);
            date = itemView.findViewById(R.id.view_date);
            time = itemView.findViewById(R.id.view_time);
            work_status = itemView.findViewById(R.id.view_workstatus);
            mobileNumber_view = itemView.findViewById(R.id.view_mobilenumber);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (newworkClick != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            newworkClick.onItemClick(position);
                        }
                    }
                }
            });
        }
    }
}

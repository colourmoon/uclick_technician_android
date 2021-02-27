package com.uclick.technician.CompletedWork;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.uclick.technician.R;

import java.util.List;

public class Completed_Work_Adapter extends RecyclerView.Adapter<Completed_Work_Adapter.ViewHolder> {

   /* Context context;
    List<Completed_Work_Model> list;

    Completed_Work_Adapter(Context context, List<Completed_Work_Model> list) {
        this.context = context;
        this.list = list;
    }
*/
    Context context;
    List<Completed_Work_Model> list;

    private onCompletedworkClick completedworkClick;

    public interface onCompletedworkClick {
        void onItemClick(int position);
    }

    public void setCompletedworkClick(onCompletedworkClick onCompletedworkClick) {
        this.completedworkClick = onCompletedworkClick;
    }
    Completed_Work_Adapter(Context context, List<Completed_Work_Model> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.completedwork_recyclerview, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        Completed_Work_Model completed_work_model = list.get(position);
        //viewHolder.service_order_id.setText(new_work_model.getService_order_id());
        //viewHolder.order_id.setText(new_work_model.getOrder_id());
        //viewHolder.technician_id.setText(new_work_model.getTechnician_id());
        viewHolder.name.setText(completed_work_model.getUser_name());
        viewHolder.address.setText(completed_work_model.getAddress());
        viewHolder.date.setText(completed_work_model.getAppointment_date());
        viewHolder.time.setText(completed_work_model.getTimings());
        viewHolder.work_status.setText(completed_work_model.getWork_status());
        viewHolder.mobile_numberView.setText(completed_work_model.getMobile_number());
        viewHolder.payment_status.setText(completed_work_model.getPayment_status());
    }

    @Override
    public int getItemCount()
    {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView service_order_id, order_id,technician_id,work_status,date,name,time,address,mobile_numberView,payment_status;

        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);
            //service_order_id = itemView.findViewById(R.id.view_service_order_id);
            // order_id = itemView.findViewById(R.id.view_order_id);
            // technician_id = itemView.findViewById(R.id.view_technician_id);
            name = itemView.findViewById(R.id.name_view_cw);
            address = itemView.findViewById(R.id.address_view_cw);
            date = itemView.findViewById(R.id.date_view_cw);
            time = itemView.findViewById(R.id.time_view_cw);
            work_status = itemView.findViewById(R.id.workstatus_view_cw);
            mobile_numberView = itemView.findViewById(R.id.mobilenumber_view_cw);
            payment_status = itemView.findViewById(R.id.paymentstatus_view_cw);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (completedworkClick != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            completedworkClick.onItemClick(position);
                        }
                    }
                }
            });
        }
    }
}

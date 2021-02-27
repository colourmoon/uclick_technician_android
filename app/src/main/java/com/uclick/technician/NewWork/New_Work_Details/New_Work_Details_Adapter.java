package com.uclick.technician.NewWork.New_Work_Details;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.uclick.technician.R;

import java.util.List;

public class New_Work_Details_Adapter extends RecyclerView.Adapter<New_Work_Details_Adapter.ViewHolder> {

    Context context;
    List<Service_Order_List_Model> list;

    private onNewworkDetailsClick newworkDetailsClick;

    public interface onNewworkDetailsClick {
        void onItemClick(int position);
    }

    public void setNewworkDetailsClick(New_Work_Details_Adapter.onNewworkDetailsClick onNewworkDetailsClick) {
        this.newworkDetailsClick = onNewworkDetailsClick;
    }

    New_Work_Details_Adapter(Context context, List<Service_Order_List_Model> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.order_details_recyclerview, parent, false);
        New_Work_Details_Adapter.ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;

    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position)
    {

        Service_Order_List_Model model = list.get(position);
        /*viewHolder.order_id.setText(new_work_details_model.getOrder_id());
        viewHolder.appointment_date.setText(new_work_details_model.getAppointment_date());
        viewHolder.timings.setText(new_work_details_model.getTimings());
        viewHolder.price.setText(new_work_details_model.getPrice());
        viewHolder.payment_status.setText(new_work_details_model.getPayment_status());
        viewHolder.work_status.setText(new_work_details_model.getWork_status());
        viewHolder.address.setText(new_work_details_model.getAddress());*/
        viewHolder.serviceName.setText(model.getService_name());
        viewHolder.serviceAmount.setText(model.getAmount());

    }

    @Override
    public int getItemCount()
    {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        //TextView order_id,appointment_date,timings,price,payment_status,work_status,address,service_order_id,payment_mode,technician_id,user_name;
        TextView serviceName,serviceAmount;
        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);
            serviceName=itemView.findViewById(R.id.service_name);
            serviceAmount=itemView.findViewById(R.id.service_amount);
            /* order_id = itemView.findViewById(R.id.orderId_view);
            appointment_date = itemView.findViewById(R.id.appointmentDate_view);
            timings = itemView.findViewById(R.id.scheduleTime_view);
            price = itemView.findViewById(R.id.price_view);
            payment_status = itemView.findViewById(R.id.paymentStatus_view);
            work_status = itemView.findViewById(R.id.workStatus_view);
            address = itemView.findViewById(R.id.technicianDetails_view);
            serviceName=itemView.findViewById(R.id.service_name);
            serviceCost=itemView.findViewById(R.id.service_cost);
*/
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (newworkDetailsClick != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            newworkDetailsClick.onItemClick(position);
                        }
                    }
                }
            });
        }
    }
}

package com.uclick.technician.CompletedWork.Completed_Work_Details;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.uclick.technician.R;

import java.util.List;

public class Completed_Work_Details_Adapter extends RecyclerView.Adapter<Completed_Work_Details_Adapter.ViewHolder> {

    Context context;
    List<Completed_Work_Details_Model> list;

    Completed_Work_Details_Adapter(Context context, List<Completed_Work_Details_Model> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.order_details_recyclerview, parent, false);
        Completed_Work_Details_Adapter.ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        Completed_Work_Details_Model model = list.get(position);

        viewHolder.serviceName.setText(model.getServiceName());
        viewHolder.serviceAmount.setText(model.getServiceAmount());
    }

    @Override
    public int getItemCount()
    {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView serviceName,serviceAmount;
        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);
            serviceName=itemView.findViewById(R.id.service_name);
            serviceAmount=itemView.findViewById(R.id.service_amount);
        }
    }
}

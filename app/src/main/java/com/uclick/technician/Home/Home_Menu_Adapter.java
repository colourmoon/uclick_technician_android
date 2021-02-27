package com.uclick.technician.Home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.uclick.technician.R;

import java.util.List;

public class Home_Menu_Adapter extends RecyclerView.Adapter<Home_Menu_Adapter.ViewHolder> {

    Context context;
    List<Home_Menu_Model> list;


    private onMenuClickListener onMenuClickListener;

    public interface onMenuClickListener {
        void onItemClick(int position);
    }

    public void setOnMenuClickListener(onMenuClickListener onMenuClickListener) {
        this.onMenuClickListener = onMenuClickListener;
    }

    Home_Menu_Adapter(Context context, List<Home_Menu_Model> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.home_menu_recyclerview, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Home_Menu_Model menu_model = list.get(position);

        ImageView cardImage = holder.image;
        TextView cardTitle = holder.title;

        cardImage.setImageResource(menu_model.getImage());
        cardTitle.setText(menu_model.getTitle());

    }

    @Override
    public int getItemCount()
    {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView image;
        TextView title;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.home_CardView_Image);
            title = itemView.findViewById(R.id.home_CardView_Title);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onMenuClickListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            onMenuClickListener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }
}

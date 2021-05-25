package in.org.myweatherapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import in.org.myweatherapp.models.DateModel;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyviewHolder> {
    private List<DateModel> dateModels;
    private Context context;

    public RecyclerViewAdapter(List<DateModel> dateModels, Context context) {
        this.dateModels = dateModels;
        this.context=context;
    }

    @Override
    public MyviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.one_item, parent, false);
        return new MyviewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.MyviewHolder holder, int position) {
        holder.date_textView.setText(dateModels.get(position).getDate());
        Glide.with(context).load("https://openweathermap.org/img/w/" +dateModels.get(position).getIcon()+ ".png").into(holder.icon_imageView);
    }

    @Override
    public int getItemCount() {
        return dateModels.size();
    }

    public class MyviewHolder extends RecyclerView.ViewHolder {
        ImageView icon_imageView;
        TextView date_textView;

        public MyviewHolder(@NonNull View itemView) {
            super(itemView);
            icon_imageView = itemView.findViewById(R.id.date_icon);
            date_textView = itemView.findViewById(R.id.date);
        }
    }
}

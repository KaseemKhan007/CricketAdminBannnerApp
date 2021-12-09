package com.example.cricketapps.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.cricketapps.BannerModel;
import com.example.cricketapps.R;

import java.util.ArrayList;

/**
 * Created by Kaseem Khan on 03/12/2021.
 */

public class BannerAdapter extends RecyclerView.Adapter<BannerAdapter.ViewHolder> {

    protected Context context;
    ArrayList<BannerModel> bannerModelArrayList;

    public BannerAdapter( Context context, ArrayList<BannerModel> bannerModelArrayList) {

        this.context = context;
        this.bannerModelArrayList = bannerModelArrayList;

        //getAwsKeys();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.item_banners, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {


        holder.tvAppName.setText(""+bannerModelArrayList.get(position).getAppName());
        holder.tvBannerName.setText(""+bannerModelArrayList.get(position).getBannerName());
        holder.tvPhoneNumber.setText(""+bannerModelArrayList.get(position).getPhoneNumber());
        holder.tvUpdatedDateTime.setText(""+bannerModelArrayList.get(position).getUpdatedDateTime());

        Glide.with(context)
                .load(bannerModelArrayList.get(position).getSmallBanner())
                .placeholder(R.drawable.ic_launcher_background)
                .diskCacheStrategy(DiskCacheStrategy.DATA)
                .error(R.drawable.ic_launcher_background)
                .into(holder.ivBanner);
    }

    @Override
    public int getItemCount() {
        return bannerModelArrayList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        ImageView ivBanner;
        TextView tvAppName,tvBannerName, tvPhoneNumber, tvUpdatedDateTime;

        public ViewHolder(View itemView) {
            super(itemView);
            ivBanner = itemView.findViewById(R.id.ivBanner);
            tvAppName = itemView.findViewById(R.id.tvAppName);
            tvBannerName = itemView.findViewById(R.id.tvBannerName);
            tvPhoneNumber = itemView.findViewById(R.id.tvPhoneNumber);
            tvUpdatedDateTime = itemView.findViewById(R.id.tvUpdatedDateTime);
        }
    }
}

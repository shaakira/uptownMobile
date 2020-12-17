package com.example.uptown.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.uptown.CallBacks.ResponseCallBack;
import com.example.uptown.Model.Wishlist;
import com.example.uptown.R;
import com.example.uptown.RetrofitClient.RetrofitClient;
import com.example.uptown.Services.WishListService;
import com.example.uptown.SingleProperty;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Response;

public class WishListAdapter extends RecyclerView.Adapter<WishListAdapter.ViewHolder> implements ResponseCallBack {
    private List<Wishlist> wishlists;
    private Context context;
    private WishListService wishListService;
    Wishlist wishlist1;
    int Position;

    public WishListAdapter(List<Wishlist> wishlists, Context context) {
        this.wishlists = wishlists;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.wish_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Wishlist wishlist = wishlists.get(position);
        holder.heading.setText(wishlist.getProperty().getHeading());
        String url = RetrofitClient.Url() + "resources/Image/" + wishlist.getProperty().getImage1();
        Picasso.get().load(url).into(holder.image);
        holder.city.setText(wishlist.getProperty().getCity());
        holder.price.setText(String.valueOf(wishlist.getProperty().getRate()));
        holder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), SingleProperty.class);
                intent.putExtra("propId", wishlist.getProperty().getId());
                view.getContext().startActivity(intent);
            }
        });
        wishListService = new WishListService();
        getWishList(wishlist.getId());
        holder.remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                removeWishList(wishlist1.getId());
                Position=position;
            }
        });

    }

    public void removeWishList(int id) {
        wishListService.remove(id, this);
    }
    public void getWishList(int id) {
        wishListService.getWishList(id, getWishListResponse());
    }

    @Override
    public int getItemCount() {
        return wishlists.size();
    }


    @Override
    public void onSuccess(Response response) throws IOException {
        ResponseBody responseBody = (ResponseBody) response.body();
        wishlists.remove(Position);
        notifyDataSetChanged();
        if (wishlists.isEmpty()) {

        }
    }

    public ResponseCallBack getWishListResponse() {
        ResponseCallBack profileResponseCallBack = new ResponseCallBack() {
            @Override
            public void onSuccess(Response response) throws IOException {
                Wishlist wishlist = (Wishlist) response.body();
                if (wishlist != null) {
                    wishlist1 = wishlist;
                }

            }

            @Override
            public void onError(String errorMessage) {

            }
        };
        return profileResponseCallBack;
    }

    @Override
    public void onError(String errorMessage) {

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView heading;
        private ImageView image;
        private TextView city;
        private TextView price;
        private TextView remove;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            heading = itemView.findViewById(R.id.description);
            image = itemView.findViewById(R.id.image);
            city = itemView.findViewById(R.id.propCity);
            price = itemView.findViewById(R.id.rate);
            remove = itemView.findViewById(R.id.remove);
        }
    }
}

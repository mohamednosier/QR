package eu.kudan.qrcode.Adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import eu.kudan.qrcode.R;

public class MyListAdapter extends RecyclerView.Adapter<MyListAdapter.ViewHolder>{
    private MyListData[] listdata;

    // RecyclerView recyclerView;
    public MyListAdapter(MyListData[] listdata) {
        this.listdata = listdata;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.list_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final MyListData myListData = listdata[position];
        holder.product.setText(listdata[position].getProduct());
        holder.date.setText(listdata[position].getDate());
        holder.code.setText(listdata[position].getCode());
        holder.product.setTextColor(Color.BLACK);
        holder.date.setTextColor(Color.BLACK);
        holder.code.setTextColor(Color.BLACK);
//        holder.imageView.setImageResource(listdata[position].getImgId());
        if (position==1){
            holder.relativeLayout.setClickable(false);
        }else {
            holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    Toast.makeText(view.getContext(), "click on item: " + myListData.getProduct() + "   " + position, Toast.LENGTH_LONG).show();

                }
            });
        }
    }


    @Override
    public int getItemCount() {
        return listdata.length;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public TextView product;
        public TextView date;
        public TextView code;
        public RelativeLayout relativeLayout;
        public ViewHolder(View itemView) {
            super(itemView);
//            this.imageView = (ImageView) itemView.findViewById(R.id.imageView);
            this.product = (TextView) itemView.findViewById(R.id.product);
            this.date = (TextView) itemView.findViewById(R.id.date);
            this.code = (TextView) itemView.findViewById(R.id.code);
            relativeLayout = (RelativeLayout)itemView.findViewById(R.id.relativeLayout);
        }
    }
}

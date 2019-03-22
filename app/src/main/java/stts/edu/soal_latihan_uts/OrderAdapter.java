package stts.edu.soal_latihan_uts;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class OrderAdapter  extends RecyclerView.Adapter<OrderAdapter.ViewHolder> {
    private static RVClickListener mylistener;
    private ArrayList<Order> arrOrder;

    public OrderAdapter(ArrayList<Order> arrOrder,RVClickListener mylistener) {
        this.arrOrder = arrOrder;
        this.mylistener = mylistener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView textView_qty_type,textView_subtotal,textView_toppings;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView_qty_type = itemView.findViewById(R.id.textView_qty_type);
            textView_subtotal = itemView.findViewById(R.id.textView_subtotal);
            textView_toppings = itemView.findViewById(R.id.textView_toppings);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mylistener.recyclerViewListClicked(v,ViewHolder.this.getLayoutPosition());
                }
            });
        }
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View v = inflater.inflate(R.layout.row_item_order,viewGroup,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.textView_qty_type.setText(arrOrder.get(i).getQty()+" "+arrOrder.get(i).getType());
        viewHolder.textView_subtotal.setText(arrOrder.get(i).getSubtotal()+"");
        viewHolder.textView_toppings.setText("with Toppings:");
        for (String s: arrOrder.get(i).getToppings()) {

            viewHolder.textView_toppings.setText(viewHolder.textView_toppings.getText()+s+",");
        }
    }

    @Override
    public int getItemCount() {
        return (arrOrder!=null)?arrOrder.size():0;
    }

}

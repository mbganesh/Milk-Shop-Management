package mb.ganesh.komalacreationdemo;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CustomerDetailsAdapter extends RecyclerView.Adapter<CustomerDetailsAdapter.CustomerDetailsHolder> {

    Context context;
    ArrayList<CustomerDetailsModel> list;

    public CustomerDetailsAdapter(Context context, ArrayList<CustomerDetailsModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public CustomerDetailsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.customer_details_row , parent, false);

        return new CustomerDetailsHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomerDetailsHolder holder, int position) {

        if(list.size() != 0){
            holder.customerNameIdRow.setText(list.get(position).fastName + " " + list.get(position).lastName);
            holder.customerIdRow.setText(list.get(position).regId);
            holder.registerDateIdRow.setText(list.get(position).regDate);
            holder.customerIdRow.setText(list.get(position).regId);
            holder.customerNoIdRow.setText(list.get(position).primaryNo);
            holder.customerAddressRow.setText(list.get(position).address);
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class CustomerDetailsHolder extends RecyclerView.ViewHolder{

        TextView customerIdRow , registerDateIdRow , customerNameIdRow , customerNoIdRow, customerAddressRow;

        public CustomerDetailsHolder(@NonNull View itemView) {
            super(itemView);

            customerIdRow = itemView.findViewById(R.id.customerIdRow);
            registerDateIdRow = itemView.findViewById(R.id.registerDateIdRow);
            customerNameIdRow = itemView.findViewById(R.id.customerNameIdRow);
            customerNoIdRow = itemView.findViewById(R.id.customerNoIdRow);
            customerAddressRow = itemView.findViewById(R.id.customerAddressRow);

        }
    }
}

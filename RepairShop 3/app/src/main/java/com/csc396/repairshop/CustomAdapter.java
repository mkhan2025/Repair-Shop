package com.csc396.repairshop;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.csc396.repairshop.database.RepairVehicles;

import java.util.ArrayList;

public class CustomAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<RepairVehicles> repairVehicles;

    public CustomAdapter (Context contextArg, ArrayList<RepairVehicles> repairVehiclesArg) {
        context = contextArg;
        repairVehicles = repairVehiclesArg;
    }


    @Override
    public int getCount() {
        return repairVehicles.size();
    }

    @Override
    public Object getItem(int i) {
        return repairVehicles.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.listview_results_row, viewGroup, false);
        }

        RepairVehicles repairVehicle = repairVehicles.get(i);

        TextView makeModel = view.findViewById(R.id.text_year_make_model);
        TextView repairDate = view.findViewById(R.id.text_repair_date);
        TextView repairCost = view.findViewById(R.id.text_repair_cost);
        TextView repairDescription = view.findViewById(R.id.text_repair_description);

        makeModel.setText(repairVehicle.getVehicle().toString());
        repairDate.setText(repairVehicle.getRepair().getDate());
        repairCost.setText("$" + String.valueOf(repairVehicle.getRepair().getCost()));
        repairDescription.setText(repairVehicle.getRepair().getDescription());
        return view;
    }
}

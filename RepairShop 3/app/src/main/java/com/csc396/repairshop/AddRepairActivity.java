package com.csc396.repairshop;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.csc396.repairshop.database.DBHelper;
import com.csc396.repairshop.database.Repair;
import com.csc396.repairshop.database.Vehicle;
import com.csc396.repairshop.databinding.ActivityAddRepairBinding;

import java.util.ArrayList;
import java.util.Calendar;

public class AddRepairActivity extends AppCompatActivity {

    private ActivityAddRepairBinding binding;
    private AlertDialog.Builder builder;


    private View.OnClickListener addRepair_OnClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View view) {
            if (!isEditTextLengthEqual(binding.edittextRepairCost, 0)) {
                if (!isEditTextLengthEqual(binding.edittextRepairDate, 0)) {
                    if( !isEditTextLengthEqual(binding.edittextRepairDescription, 0)){
                        Vehicle vehicle = (Vehicle) binding.spinnerVehicles.getSelectedItem();
                        String vehicleModel = vehicle.getModel();
                        int vehicleID = vehicle.getVid();

                        String date = returnEditTextString(binding.edittextRepairDate);
                        float cost = Float.valueOf(returnEditTextString(binding.edittextRepairCost));
                        String description = returnEditTextString(binding.edittextRepairDescription);

                        Repair repair = new Repair(vehicleModel, date, cost, description, vehicleID);
                        DBHelper dbHelper = DBHelper.getDBHelper(AddRepairActivity.this);
                        long result = dbHelper.insertRepair(repair);
                        if (result > 0) {
                            Toast.makeText(AddRepairActivity.this, "Repair Successfully added", Toast.LENGTH_SHORT).show();
                        }
                        finish();
                    }
                    else {
                        showAlertBuilder("Repair Description incomplete", "Please enter a description for the repair");
                    }
                }
                else {
                    showAlertBuilder("Repair Date incomplete", "Please specify a date for the repair");
                }
            }
            else {
                showAlertBuilder("Repair Cost incomplete", "Please specify a $cost for the repair");
            }
        }
    };


    private DatePickerDialog.OnDateSetListener datepicker_OnDateSetListener = new DatePickerDialog.OnDateSetListener(){

        @Override
        public void onDateSet(DatePicker datePicker, int year, int month, int date) {
            binding.edittextRepairDate.setText(year+"-"+String.format("%02d", month+1)+"-"+String.format("%02d", date));
        }
    };

    private View.OnClickListener datepicker_OnClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View view) {
            Calendar calendar = Calendar.getInstance();
            DatePickerDialog datePicker = new DatePickerDialog(AddRepairActivity.this, datepicker_OnDateSetListener, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE));
            datePicker.show();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddRepairBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        DBHelper dbHelper = DBHelper.getDBHelper(this);
        ArrayList<Vehicle> vehicles = dbHelper.returnVehicles();
        ArrayAdapter<Vehicle> adapter = new ArrayAdapter<Vehicle>(this, android.R.layout.simple_spinner_item, vehicles);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spinnerVehicles.setAdapter(adapter);

        binding.edittextRepairDate.setOnClickListener(datepicker_OnClickListener);
        binding.buttonAddRepair.setOnClickListener(addRepair_OnClickListener);
    }

    private String returnEditTextString(EditText editText){
        return editText.getText().toString();
    }

    private boolean isEditTextLengthEqual(EditText editText, int length) {
        return returnEditTextString(editText).length() == length;
    }

    private void showAlertBuilder(String title, String content) {
        builder = new AlertDialog.Builder(AddRepairActivity.this);
        builder.setTitle(title).setMessage(content);
        builder.create().show();
    }


}
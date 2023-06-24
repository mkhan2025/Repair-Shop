package com.csc396.repairshop;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.csc396.repairshop.database.DBHelper;
import com.csc396.repairshop.database.Vehicle;
import com.csc396.repairshop.databinding.ActivityAddVehicleBinding;


public class AddVehicleActivity extends AppCompatActivity {

    private ActivityAddVehicleBinding binding;
    private AlertDialog.Builder builder;

    private String returnEditTextString(EditText editText){
        return editText.getText().toString();
    }

    private boolean isEditTextLengthEqual(EditText editText, int length) {
        return returnEditTextString(editText).length() == length;
    }

    private View.OnClickListener addVehicle_onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (!isEditTextLengthEqual(binding.edittextMakeModel, 0)) {
                if (!isEditTextLengthEqual(binding.edittextPrice, 0)) {
                    if( isEditTextLengthEqual(binding.edittextYear, 4)){
                        String year = returnEditTextString(binding.edittextYear);
                        String model = returnEditTextString(binding.edittextMakeModel);
                        float price = Float.valueOf(returnEditTextString(binding.edittextPrice));
                        boolean newBool = binding.checkboxIsNew.isChecked();

                        Vehicle vehicle = new Vehicle(year, model, price, newBool);
                        DBHelper helper = DBHelper.getDBHelper(AddVehicleActivity.this);
                        long result = helper.insertVehicle(vehicle);
                        if (result > 0) {
                            Toast.makeText(AddVehicleActivity.this, "Vehicle Successfully added", Toast.LENGTH_SHORT).show();
                        }
                        finish();
                    }
                    else {
                        showAlertBuilder("Incorrect year", "Fill the year in format YYYY");
                    }
                }
                else {
                    showAlertBuilder("Price field incomplete", "Please specify the price of vehicle");
                }
            }
            else {
                showAlertBuilder("Model field incomplete", "Please specify the Model of the vehicle");
            }
        }
    };

    private void showAlertBuilder(String title, String content) {
        builder = new AlertDialog.Builder(AddVehicleActivity.this);
        builder.setTitle(title).setMessage(content);
        builder.create().show();
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddVehicleBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.buttonAddVehicle.setOnClickListener(addVehicle_onClickListener);

    }
}
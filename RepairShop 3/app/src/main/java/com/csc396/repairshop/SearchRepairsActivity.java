package com.csc396.repairshop;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Toast;

import com.csc396.repairshop.database.DBHelper;
import com.csc396.repairshop.database.RepairVehicles;
import com.csc396.repairshop.databinding.ActivitySearchRepairsBinding;


public class SearchRepairsActivity extends AppCompatActivity {

    private ActivitySearchRepairsBinding binding;

    private AdapterView.OnItemLongClickListener delete_OnLongClickListener = new AdapterView.OnItemLongClickListener() {

        @Override
        public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
            RepairVehicles repairVehicle = (RepairVehicles) adapterView.getItemAtPosition(i);
            DBHelper dbHelper = DBHelper.getDBHelper(SearchRepairsActivity.this);
            int result = dbHelper.removeRepair(repairVehicle.getRepair().getRid());
            if (result > 0) {
                Toast.makeText(SearchRepairsActivity.this, "Repair Successfully deleted", Toast.LENGTH_SHORT).show();
                binding.listviewResults.invalidateViews();
                binding.listviewResults.setAdapter(returnCustomAdapter(returnEditTextString(binding.edittextSearchPhrase)));
            }
            return false;
        }
    };

    private View.OnClickListener search_OnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            String query =  returnEditTextString(binding.edittextSearchPhrase);

            CustomAdapter customAdapter = returnCustomAdapter(query);
            binding.listviewResults.setAdapter(customAdapter);
            binding.listviewResults.setOnItemLongClickListener(delete_OnLongClickListener);
        }
    };

    private CustomAdapter returnCustomAdapter(String query) {
        DBHelper dbHelper = DBHelper.getDBHelper(SearchRepairsActivity.this);
        return new CustomAdapter(SearchRepairsActivity.this, dbHelper.returnRepairVehicles(query));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySearchRepairsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.buttonFindRepairs.setOnClickListener(search_OnClickListener);
    }

    private String returnEditTextString(EditText editText){
        return editText.getText().toString();
    }

}
package dk.au.mad22spring.assignment1.au651459;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class ListActivity extends AppCompatActivity implements DrinkAdapter.IDrinkItemClickedListener{

    private RecyclerView rcView;
    private DrinkAdapter adapter;
    private DrinkViewModel vm;
    private ActivityResultLauncher<Intent> launcher;
    private Button exitButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        vm= new ViewModelProvider(this).get(DrinkViewModel.class);

        exitButton =findViewById(R.id.buttonExit);
        adapter=new DrinkAdapter(this,getApplicationContext());
        rcView=findViewById(R.id.recycler);
        rcView.setLayoutManager(new LinearLayoutManager(this));
        rcView.setAdapter(adapter);


        if(vm.resources==null) vm.resources=getResources();

        adapter.updateDrinkList(vm.getList());

        //Launcher - Based on code from MAD lecture 2
        launcher=registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if(result.getResultCode()== RESULT_OK){
                    //Toast.makeText(ListActivity.this, result.getData().getStringExtra("colors"), Toast.LENGTH_SHORT).show();
                    Log.d("listactivity-log-tag", "launcher recieved ok: "+ result.getData().getIntExtra("DrinkIndex",0));
                    int index=result.getData().getIntExtra("DrinkIndex",0);
                    adapter.notifyItemChanged(index);
                }
                else{
                    //Toast.makeText(MainActivity.this, "Canceled", Toast.LENGTH_SHORT).show();
                }
            }
        });

        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    public void onDrinkClicked(int index) {
        Log.d("listactivity-log-tag","drink Number "+ index + " clicked");
        Intent intent = new Intent(this, DetailsActivity.class);
        intent.putExtra("DrinkIndex", index);
        launcher.launch(intent);

    }

}
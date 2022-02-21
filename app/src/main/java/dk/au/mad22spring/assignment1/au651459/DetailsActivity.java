package dk.au.mad22spring.assignment1.au651459;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.Locale;

public class DetailsActivity extends AppCompatActivity {

    Intent data;
    int arrayIndex;

    ImageView img;
    SeekBar scoreBar;
    Button backButton, saveButton;
    TextView drinkName,categoryName,score, ingredientList,instructionList;
    DrinkAdapter.IDrinkItemClickedListener listener;
    DrinkViewModel vm;
    Drink drink;
    @RequiresApi(api = Build.VERSION_CODES.O) //string.join
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        data=getIntent();
        arrayIndex=data.getIntExtra("DrinkIndex",0);

        //Assign design elements:
        img=findViewById(R.id.imageDetails);
        drinkName=findViewById(R.id.textTitle);
        categoryName=findViewById(R.id.textDrinkCategory);
        score=findViewById(R.id.textRating);
        ingredientList=findViewById(R.id.textIngredients);
        instructionList=findViewById(R.id.textInstructions);
        backButton=findViewById(R.id.buttonBack);
        saveButton=findViewById(R.id.buttonSave);
        scoreBar=findViewById(R.id.seekBar);

        vm= new ViewModelProvider(this).get(DrinkViewModel.class);
        if(vm.resources==null) vm.resources=getResources();
        drink=vm.getList().get(arrayIndex);

        //Populate View
        drinkName.setText(drink.name);
        categoryName.setText(drink.category);
        drink.score=FloatSingleton.getInstance().scores.get(arrayIndex);
        score.setText(Float.toString( drink.score));

        String[] superIngredients =new String[drink.measures.size()];
        for(int i=0,j=0; i< drink.measures.size(); i++){
            superIngredients[i]=drink.measures.get(i)+ " "+drink.ingredients.get(i);
        }
        ingredientList.setText( String.join("\n",superIngredients));
        instructionList.setText( drink.instructions);
        scoreBar.setMax(100);
        scoreBar.setProgress((int) (drink.score*10),true);
        Log.d("seekbar", String.valueOf( drink.score*10));


        //Load image:
        String imageName="drawable/" + drink.name;
        imageName=imageName.toLowerCase(Locale.ROOT).replaceAll(" ","_").replaceAll("\\p{C}", "?");//Based on https://stackoverflow.com/a/32685095
        int image= getResources().getIdentifier(imageName, null, getPackageName());//Based on https://stackoverflow.com/questions/6783327/setimageresource-from-a-string
        if(image<1) image=getResources().getIdentifier("drawable/gin_tonic", null, getPackageName());//Invisible letter in CSV :(
        img.setImageResource(image);



        //Assign listeners:
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();}
        });
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vm.setScore(drink.score,arrayIndex);
                Intent intent=new Intent();
                intent.putExtra("DrinkIndex", arrayIndex);
                setResult(RESULT_OK,intent);
                finish();}
        });
        scoreBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                drink.score=(float) i/10;
                score.setText(Float.toString( drink.score));

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }
}
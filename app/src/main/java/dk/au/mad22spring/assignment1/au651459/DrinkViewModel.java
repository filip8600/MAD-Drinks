package dk.au.mad22spring.assignment1.au651459;

import android.content.res.Resources;
import android.util.Log;

import androidx.lifecycle.ViewModel;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;

public class DrinkViewModel extends ViewModel {

    public Resources resources;
    private ArrayList<Drink> drinkList;
    public ArrayList<Drink> getList(){
        if(drinkList==null) readData();
        return drinkList;
    }
    public void setScore(float score, int index){
        if(score<0||score>10) return;
        drinkList.get(index).score=score;
        FloatSingleton.getInstance().scores.set(index,score);
    }
    private void readData() {//Derived from https://stackoverflow.com/a/46016818
        InputStream is = resources.openRawResource(R.raw.drinksdata);
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(is, Charset.forName("UTF-8")));
        String line = "";
        drinkList=new ArrayList<Drink>();
        try {
            while ((line = reader.readLine()) != null) {
                // Split the line into different tokens (using the comma as a separator).
                String[] tokens = line.split(";");
                // Read the data and store it in the WellData POJO.
                Drink drink = new Drink();
                drink.name= (tokens[0]);
                drink.category=(tokens[1]);
                for (int i =2;i<6;i++){
                    if(tokens[i].equalsIgnoreCase("null")) break;
                    drink.measures.add(tokens[i]);
                }
                for (int i =6;i<10;i++){
                    if(tokens[i].equalsIgnoreCase("null")) break;
                    drink.ingredients.add(tokens[i]);
                }
                drink.instructions=(tokens[10]);
                drinkList.add(drink);
                FloatSingleton.getInstance().scores.add(0.0F);

                Log.d("csv-reader" ,"Just Created " + drink.name);
            }
        } catch (IOException e1) {
            Log.e("MainActivity", "readError" + line, e1);
            e1.printStackTrace();
        }
        finally {
            try {
                is.close();
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

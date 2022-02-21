package dk.au.mad22spring.assignment1.au651459;

import android.content.Context;
import android.net.CaptivePortal;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Locale;

//Entire file Derived from AU MAD lecture 3 "PersonAdaptor"

public class DrinkAdapter extends RecyclerView.Adapter<DrinkAdapter.DrinkViewHolder> {
    public interface IDrinkItemClickedListener{
        void onDrinkClicked(int index);
    }
    private IDrinkItemClickedListener listener;
    private ArrayList<Drink> drinkList;
    private Context context;//Used to get images

    public DrinkAdapter(IDrinkItemClickedListener listener,Context appContext){
        this.listener=listener;
        context= appContext;
    }

    public void updateDrinkList(ArrayList<Drink> list){
        drinkList=list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public DrinkAdapter.DrinkViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;
        v= LayoutInflater.from(parent.getContext()).inflate(R.layout.drinkitemlayout,parent,false);
        DrinkViewHolder vh= new DrinkViewHolder(v,listener);
        return vh;
    }
    @NonNull
    @Override
    public void onBindViewHolder(@NonNull DrinkViewHolder holder, int position){
        Drink drink=drinkList.get(position);
        holder.drinkName.setText(drink.name);
        holder.categoryName.setText(drink.category);
        drink.score=FloatSingleton.getInstance().scores.get(position);
        holder.score.setText(Float.toString( drink.score));
        //Load image:
        String imageName="drawable/" + drink.name;
        imageName=imageName.toLowerCase(Locale.ROOT).replaceAll(" ","_").replaceAll("\\p{C}", "?");//Based on https://stackoverflow.com/a/32685095
        int image= context.getResources().getIdentifier(imageName, null, context.getPackageName());//Based on https://stackoverflow.com/questions/6783327/setimageresource-from-a-string
        if(image<1) image=context.getResources().getIdentifier("drawable/gin_tonic", null, context.getPackageName());//Invisible letter in CSV :(
        holder.img.setImageResource(image);
    }

    @Override
    public int getItemCount() {
        return drinkList.size();
    }

    public class DrinkViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView img;
        TextView drinkName;
        TextView categoryName;
        TextView score;
        IDrinkItemClickedListener listener;

        public DrinkViewHolder(@NonNull View itemView, DrinkAdapter.IDrinkItemClickedListener listener){
            super(itemView);

            img=itemView.findViewById(R.id.imageDrink);
            drinkName=itemView.findViewById(R.id.txtDrinkName);
            categoryName=itemView.findViewById(R.id.txtDrinkCat);
            score=itemView.findViewById(R.id.txtDrinkScore);
            this.listener=listener;

            itemView.setOnClickListener(this);

            }
            @Override
            public void onClick(View view){
                Log.d("listactivity-log-tag","drink   clicked");

                listener.onDrinkClicked(getAdapterPosition());
            }
    }

}


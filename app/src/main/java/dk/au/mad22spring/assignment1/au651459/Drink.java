package dk.au.mad22spring.assignment1.au651459;

import java.util.ArrayList;
import java.util.List;

public class Drink {
    public String name;
    public String category;
    public ArrayList<String> measures;
    public ArrayList<String> ingredients;
    public String instructions;
    public float score=0;

    public Drink(){
        measures=new ArrayList<String>();
        ingredients=new ArrayList<String>();

    }

}
//Derived from https://www.geeksforgeeks.org/singleton-class-java/
class FloatSingleton {
    // Static variable reference of single_instance
    // of type Singleton
    private static FloatSingleton single_instance = null;

    // Declaring a variable of type ArrayList<Float>
    public ArrayList<Float> scores;

    // Constructor
    // Here we will be creating private constructor
    // restricted to this class itself
    private FloatSingleton()
    {
       scores = new ArrayList<Float>();
    }

    // Static method
    // Static method to create instance of Singleton class
    public static FloatSingleton getInstance()
    {
        if (single_instance == null)
            single_instance = new FloatSingleton();

        return single_instance;
    }
}
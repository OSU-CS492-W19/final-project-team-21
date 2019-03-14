package com.example.sadfacts;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceViewHolder;
import android.util.AttributeSet;
import android.view.View;

public class PreferenceAnimals extends Preference {

    private String pAnimal;
    private View button1;
    private View button2;
    private View button3;
    private View button4;

    private final String DEFAULT_VALUE = "animal_1";

    public PreferenceAnimals(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PreferenceAnimals(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setWidgetLayoutResource(R.layout.animal_pref);
        pAnimal = DEFAULT_VALUE;
    }

    @Override
    public void onBindViewHolder(PreferenceViewHolder holder) {
        super.onBindViewHolder(holder);
        holder.itemView.setClickable(false); // disable parent click

        button1 = holder.findViewById(R.id.animal_1);
        button1.setClickable(true); // enable custom view click

        button2 = holder.findViewById(R.id.animal_2);
        button2.setClickable(true); // enable custom view click

        button3 = holder.findViewById(R.id.animal_3);
        button3.setClickable(true); // enable custom view click

        button4 = holder.findViewById(R.id.animal_4);
        button4.setClickable(true); // enable custom view click

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setAnimal("animal_1");
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setAnimal("animal_2");
            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setAnimal("animal_3");
            }
        });

        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setAnimal("animal_4");
            }
        });

        formatButtons();

    }

    public void setAnimal(String animal){
        pAnimal = animal;
        persistString(pAnimal);
        notifyChanged();
        formatButtons();
    }

    private void formatButtons(){
        if(pAnimal.equals("animal_1")){
            button1.setBackgroundColor(Color.RED);
            button2.setBackgroundColor(Color.BLUE);
            button3.setBackgroundColor(Color.BLUE);
            button4.setBackgroundColor(Color.BLUE);
        }else if(pAnimal.equals("animal_2")){
            button1.setBackgroundColor(Color.BLUE);
            button2.setBackgroundColor(Color.RED);
            button3.setBackgroundColor(Color.BLUE);
            button4.setBackgroundColor(Color.BLUE);
        }else if(pAnimal.equals("animal_3")){
            button1.setBackgroundColor(Color.BLUE);
            button2.setBackgroundColor(Color.BLUE);
            button3.setBackgroundColor(Color.RED);
            button4.setBackgroundColor(Color.BLUE);
        }else if(pAnimal.equals("animal_4")){
            button1.setBackgroundColor(Color.BLUE);
            button2.setBackgroundColor(Color.BLUE);
            button3.setBackgroundColor(Color.BLUE);
            button4.setBackgroundColor(Color.RED);
        }
    }

    public String getAnimal(){
        return getPersistedString(DEFAULT_VALUE);
    }

    @Override
    protected void onSetInitialValue(boolean restorePersistedValue, Object defaultValue) {
        if (restorePersistedValue) {
            pAnimal = getPersistedString(DEFAULT_VALUE);
        } else {
            pAnimal = DEFAULT_VALUE;
            persistString(pAnimal);
        }
    }

    public static int GetDrawableIDForAnimalPreference(String animal) {
        switch (animal) {
            case "animal_2":
                return R.drawable.corgie;
            case "animal_3":
                return R.drawable.huskie;
            case "animal_4":
                return R.drawable.duck;
            case "animal_1":
            default:
                return R.drawable.totaro_hula;
        }
    }

}

package com.gluonapplication.views;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.ArrayList;

public class RecipeButtons {

    private final StringProperty buttonId = new SimpleStringProperty();
    private ArrayList<Ingredients> ingredients = new ArrayList<>();
    private String name;
    private String imageURL;
    private boolean added =false;

    final void setText(String value) { buttonId.set(value); }
    final String getText() { return buttonId.get(); }
    public final String getName() { return name; }
    final String getImage() { return imageURL; }
    final void setName(String value) { name = value; }
    final void setImage(String value) { imageURL = value; }
    final ArrayList<Ingredients> getIngredients() {return ingredients;}
    final void setIngredients(ArrayList<Ingredients> i) {ingredients = i;}
    final void setTrue() {added = true;}
    final void setFalse() {added = false;}
    boolean isTrue() {return added;}

}

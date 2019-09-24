package com.gluonapplication.views;

import javafx.scene.control.Label;
import javafx.scene.layout.HBox;


class PrintIngredients {

    String colour;
    private int height;
    private int width;
    private Ingredients food;

    PrintIngredients(String colour, int height, int width, Ingredients food) {
        this.colour = colour;
        this.height = height;
        this.width = width;
        this.food = food;
    }

    HBox getDisplayList() {

            Label amounts = new Label(food.getFood());
            Label ingredientList = new Label(food.getAmount() + " " + food.getMeasurement());
            ingredientList.setId("ingredientList");
            if (!colour.equals("none")) {
                ingredientList.setStyle("-fx-background-color: " + colour + ";");
                amounts.setStyle("-fx-background-color: " + colour + ";");
            }
            ingredientList.setMinHeight(height);
            ingredientList.setMinWidth(width);
            ingredientList.setMaxWidth(width);
            amounts.setMinHeight(height);
            amounts.setMinWidth(width);
            amounts.setMaxWidth(width);
            amounts.setWrapText(true);
            amounts.setId("amounts");
            ingredientList.setWrapText(true);
            return  new HBox(amounts, ingredientList);

    }
}





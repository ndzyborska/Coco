package com.gluonapplication.views;

import com.gluonapplication.Coco;
import com.gluonhq.charm.glisten.afterburner.GluonPresenter;
import com.gluonhq.charm.glisten.animation.BounceInRightTransition;
import com.gluonhq.charm.glisten.control.AppBar;
import com.gluonhq.charm.glisten.control.FloatingActionButton;
import com.gluonhq.charm.glisten.mvc.View;
import com.gluonhq.charm.glisten.visual.MaterialDesignIcon;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.ArrayList;

public class ShoppingPresenter extends GluonPresenter<Coco> {

    @FXML
    private View shopping;


    @FXML
    VBox shoppingList;

    private ArrayList<Ingredients> list;

    private
    ArrayList<RecipeButtons> myRecipes;


    public void initialize() {
        shopping.setShowTransitionFactory(BounceInRightTransition::new);
        
        FloatingActionButton fab = new FloatingActionButton(MaterialDesignIcon.INFO.text,
                e -> System.out.println("Info"));
        fab.showOn(shopping);
        
        shopping.showingProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue) {
                AppBar appBar = getApp().getAppBar();
                appBar.setNavIcon(MaterialDesignIcon.MENU.button(e -> 
                        getApp().getDrawer().open()));
                appBar.setTitleText("Your List!");
                appBar.getActionItems().add(MaterialDesignIcon.DELETE.button(e ->
                        restart() ));
            }
        });

    }
    private void restart() {

        myRecipes.clear();
        shoppingList.getChildren().clear();
        AppViewManager.RECIPES_VIEW.switchView();


    }

    void initShoppingList(RecipeButtons b, ArrayList<RecipeButtons> alb) {

        this.myRecipes = alb;
        boolean added = false;
            System.out.println("sjopping!: " + b.getImage());

            list = new ArrayList<>();

            for (RecipeButtons recipe : myRecipes) {
                for (Ingredients allRecipes : recipe.getIngredients()) {
                    for (Ingredients cumulative : list) {
                        if (cumulative.getFood().equals(allRecipes.getFood())) {
                            cumulative.setAmount(addStrings(cumulative.getAmount(), allRecipes.getAmount()));
                            added = true;
                        }
                    }
                    if (!added) {
                        list.add(allRecipes);
                    }
                    added = false;
                }
            }
            printShoppingList();
        }




    private void printShoppingList() {
        int count = 1;

        HBox h;
        PrintIngredients displayList;


        for (Ingredients food : list) {
            CheckBox c = new CheckBox();
            if (count % 2 == 0) { displayList = new PrintIngredients("FFE298", 100, 140, food);
                c.setStyle("-fx-background-color: FFE298");}
            else { displayList = new PrintIngredients(" ffab00", 100, 140, food); }
            h = displayList.getDisplayList();

            c.setPrefWidth(70);
            c.setPrefHeight(100);
            h.getChildren().add(c);
            shoppingList.getChildren().add(h);

            count++;
        }
    }

    private String addStrings(String a, String b) {
        double d = Double.parseDouble(a) + Double.parseDouble(b);

        return Double.toString(d);

    }
}

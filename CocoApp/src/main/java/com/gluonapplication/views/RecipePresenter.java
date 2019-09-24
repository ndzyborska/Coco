package com.gluonapplication.views;


import com.gluonapplication.Coco;
import com.gluonhq.charm.glisten.afterburner.GluonPresenter;
import com.gluonhq.charm.glisten.animation.BounceInRightTransition;
import com.gluonhq.charm.glisten.control.AppBar;
import com.gluonhq.charm.glisten.mvc.View;
import com.gluonhq.charm.glisten.visual.MaterialDesignIcon;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class RecipePresenter extends GluonPresenter<Coco> {


    @FXML
    private View recipe;


    @FXML
    private ImageView image;

    @FXML
    private VBox ingredients;


    @FXML
    protected AnchorPane myAnchor;




    private ArrayList<RecipeButtons> myRecipes;

    private RecipeButtons buttons;



    private ExcelReader reader;
    private ArrayList<Ingredients> i;

    public void initialize() {
        recipe.setShowTransitionFactory(BounceInRightTransition::new);


        recipe.showingProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue) {
                AppBar appBar = getApp().getAppBar();
                appBar.setNavIcon(MaterialDesignIcon.MENU.button(e ->
                        getApp().getDrawer().open()));
                appBar.setTitleText("Recipe");
                appBar.getActionItems().add(MaterialDesignIcon.ARROW_BACK.button(e ->
                        AppViewManager.RECIPES_VIEW.switchView()));
                appBar.getActionItems().add(MaterialDesignIcon.ADD.button(e ->
                        add()));

            }
        });
    }
    private void add(){
        buttons.setIngredients(i);
        myRecipes.add(buttons);

        buttons.setTrue();
        AppViewManager.RECIPES_VIEW.switchView();
    }

    @FXML
    void initRecipe(RecipeButtons b, ArrayList<RecipeButtons> alb)throws IOException, InvalidFormatException {

        this.myRecipes = alb;
        this.buttons = b;


        File file = new File("src/main/resources/RecipeList/ingredients.xlsx");
        String path = file.getAbsolutePath();
        reader = new ExcelReader(path);
        ingredients();

        reader.close();

    }


    @FXML
    private void ingredients() {

        int count = 1;

        i = reader.getFood(buttons.getText());
        HBox h;
        PrintIngredients displayList;

        image.setImage(new Image(buttons.getImage()));
        for (Ingredients food : i) {

            if (count % 2 == 0) { displayList = new PrintIngredients("FFE298", 100, 175, food); }
            else { displayList = new PrintIngredients(" ffab00", 100, 175, food); }
            h = displayList.getDisplayList();

            ingredients.getChildren().add(h);

            count++;
        }




    }





}






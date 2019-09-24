package com.gluonapplication.views;

import com.gluonapplication.Coco;
import com.gluonhq.charm.glisten.afterburner.GluonPresenter;
import com.gluonhq.charm.glisten.control.AppBar;
import com.gluonhq.charm.glisten.mvc.View;
import com.gluonhq.charm.glisten.visual.MaterialDesignIcon;
import javafx.animation.Interpolator;
import javafx.animation.PathTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Polyline;
import javafx.util.Duration;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class RecipesPresenter extends GluonPresenter<Coco> {

    @FXML
    private View recipes;


    @FXML
    Button go;

    @FXML
    private VBox myView;

    @FXML
    private HBox cheap, showstopper, quick, breakfast, deserts, salads;

    @FXML
    private HBox recipeArray;

    @FXML
    Button imageDrop;

    @FXML
    ScrollPane recipeScroll;

    private ArrayList<RecipeButtons> myRecipes = new ArrayList<>();

    private RecipeButtons buttons = new RecipeButtons();

    private ArrayList<HBox> categories = new ArrayList<>();

    public void initialize() {
        recipes.showingProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue) {
                AppBar appBar = getApp().getAppBar();
                appBar.setStyle("-fx-background-color: black;");
                appBar.setNavIcon(MaterialDesignIcon.MENU.button(e -> 
                        getApp().getDrawer().open()));
                appBar.setTitleText("Recipes");
                appBar.getActionItems().add(MaterialDesignIcon.SEARCH.button(e -> 
                        System.out.println("Search")));
                    if (buttons.isTrue()) {
                        addButton();
                        buttons.setFalse();
                    }


                if (myRecipes.isEmpty()) {
                    clearBar();
                }

            }
        });

        try {
            initRecipes();
        } catch (IOException | org.apache.poi.openxml4j.exceptions.InvalidFormatException e) {
            e.printStackTrace();
        }




    }

    @FXML
    private void clearBar() {
        recipeScroll.setVisible(false);
        recipeArray.getChildren().clear();

    }



    @FXML
    private void addButton() {
        imageDrop.setVisible(true);

        Polyline polyline = new Polyline();
        polyline.getPoints().addAll(0.0, 0.0,
                -100.0, 10.0,
                -220.1, 30.0,
                -260.0, 400.0);


        imageDrop.setStyle("-fx-background-image: url('" + buttons.getImage() + "')");
        imageDrop.setPrefWidth(65);
        imageDrop.setPrefHeight(65);

        PathTransition pt = new PathTransition();
        pt.setOnFinished(e -> putButton());
        pt.setNode(imageDrop);
        pt.setInterpolator(Interpolator.EASE_IN);
        pt.setDuration(Duration.seconds(0.8));
        pt.setPath(polyline);
        pt.setCycleCount(1);

        pt.play();

        recipeScroll.setVisible(true);


    }


    private void putButton() {
        imageDrop.setVisible(false);
        imageDrop.setTranslateX(260);
        imageDrop.setTranslateX(-400);
        Button v = new Button();
        v.setStyle("-fx-background-image: url('" +buttons.getImage() + "')");
        v.setPrefWidth(65);
        v.setPrefHeight(65);


        recipeArray.getChildren().add(v);

    }


    @FXML
    private void initRecipes() throws IOException, InvalidFormatException {

        File file = new File("src/main/resources/RecipeList/Recipes.xlsx");
        String path = file.getAbsolutePath();
        ExcelReader list = new ExcelReader(path);

        int i = 10;
        int sheetNo = 0;

        categories.add(cheap);
        categories.add(showstopper);
        categories.add(quick);
        categories.add(breakfast);
        categories.add(deserts);
        categories.add(salads);

        for (HBox h : categories) {
            for (int k = 0; k < list.getAmount(sheetNo); k++) {  // Can be presented better....
                String buttonId = Integer.toString(i) + (k + 1);
                Button button = new Button(list.getName(buttonId, sheetNo));
                button.setId(buttonId);
                button.setStyle("-fx-background-image: url('" + list.getImage(buttonId, sheetNo)+ "')");
                int buttonHeight = 96;
                button.setPrefHeight(buttonHeight);
                int buttonWidth = 106;
                button.setPrefWidth(buttonWidth);

                button.setOnAction(event -> {

                    buttons.setImage( list.getImage(buttonId, (Character.getNumericValue(buttonId.charAt(0)))-1));
                    buttons.setName(list.getName(buttonId, (Character.getNumericValue(buttonId.charAt(0)))-1));
                    buttons.setText(((Button)event.getSource()).getId());
                    buttons.setText(((Button)event.getSource()).getId());
                    System.out.println("GET TEXT" +buttons.getText());

                    AppViewManager.RECIPE_VIEW.switchView()
                            .ifPresent(presenter ->
                            {
                                try {
                                    ((RecipePresenter) presenter).initRecipe(buttons, myRecipes);
                                } catch (IOException | InvalidFormatException ex) {
                                    ex.printStackTrace();
                                }
                                });

                });

                h.getChildren().add(button);
                h.getParent().prefWidth(h.getPrefWidth());

            }

            sheetNo++;
            i += 10;

        }

        list.close();
    }

    @FXML
    void myRecipes() {

        AppViewManager.SHOPPING_VIEW.switchView()
                .ifPresent(presenter ->
                        ((ShoppingPresenter) presenter).initShoppingList(buttons, myRecipes));}

}
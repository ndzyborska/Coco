package com.gluonapplication.views;

import com.gluonhq.charm.glisten.afterburner.AppView;
import com.gluonhq.charm.glisten.afterburner.AppViewRegistry;
import com.gluonhq.charm.glisten.afterburner.GluonPresenter;
import com.gluonhq.charm.glisten.afterburner.Utils;
import com.gluonhq.charm.glisten.application.MobileApplication;
import com.gluonhq.charm.glisten.control.Avatar;
import com.gluonhq.charm.glisten.control.NavigationDrawer;
import com.gluonhq.charm.glisten.visual.MaterialDesignIcon;
import com.gluonapplication.Coco;
import javafx.scene.image.Image;
import java.util.Locale;

import static com.gluonhq.charm.glisten.afterburner.AppView.Flag.HOME_VIEW;
import static com.gluonhq.charm.glisten.afterburner.AppView.Flag.SHOW_IN_DRAWER;
import static com.gluonhq.charm.glisten.afterburner.AppView.Flag.SKIP_VIEW_STACK;

public class AppViewManager {

    private static final AppViewRegistry REGISTRY = new AppViewRegistry();

    static final AppView RECIPES_VIEW = view("Recipes", RecipesPresenter.class, MaterialDesignIcon.HOME, SHOW_IN_DRAWER, HOME_VIEW, SKIP_VIEW_STACK);
    static final AppView SHOPPING_VIEW = view("Shopping", ShoppingPresenter.class, MaterialDesignIcon.DASHBOARD, SHOW_IN_DRAWER);
    static final AppView RECIPE_VIEW = view("Recipe", RecipePresenter.class, MaterialDesignIcon.DASHBOARD, SHOW_IN_DRAWER);

    private static AppView view(String title, Class<? extends GluonPresenter<?>> presenterClass, MaterialDesignIcon menuIcon, AppView.Flag... flags ) {
        return REGISTRY.createView(name(presenterClass), title, presenterClass, menuIcon, flags);
    }

    private static String name(Class<? extends GluonPresenter<?>> presenterClass) {
        return presenterClass.getSimpleName().toUpperCase(Locale.ROOT).replace("PRESENTER", "");
    }
    
    public static void registerViewsAndDrawer(MobileApplication app) {
        for (AppView view : REGISTRY.getViews()) {
            view.registerView(app);
        }

        NavigationDrawer.Header header = new NavigationDrawer.Header("Coco",
                "Your Vegan Kickstart!",
                new Avatar(21, new Image(Coco.class.getResourceAsStream("/icon.png"))));

        Utils.buildDrawer(app.getDrawer(), header, REGISTRY.getViews()); 
    }
}

package eu.kudan.qrcode.Storage;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import eu.kudan.qrcode.Utils.Loyalty;

public class SharedPreference {
    public static final String PREFS_NAME = "PRODUCT_APP";
    public static final String FAVORITES = "Product_Favorite";

    public SharedPreference() {
        super();
    }

    // This four methods are used for maintaining favorites.
    public void saveFavorites(Context context, List<Loyalty> favorites) {
        SharedPreferences settings;
        SharedPreferences.Editor editor;

        settings = context.getSharedPreferences(PREFS_NAME,
                Context.MODE_PRIVATE);
        editor = settings.edit();

        Gson gson = new Gson();
        String jsonFavorites = gson.toJson(favorites);

        editor.putString(FAVORITES, jsonFavorites);

        editor.commit();
    }

    public void addFavorite(Context context, Loyalty product) {
        List<Loyalty> favorites = getFavorites(context);
        if (favorites == null)
            favorites = new ArrayList<Loyalty>();
        favorites.add(product);
        saveFavorites(context, favorites);
    }

    public void removeFavorite(Context context, Loyalty product) {
        ArrayList<Loyalty> favorites = getFavorites(context);
        if (favorites != null) {
            favorites.remove(product);
            saveFavorites(context, favorites);
        }
    }

    public ArrayList<Loyalty> getFavorites(Context context) {
        SharedPreferences settings;
        List<Loyalty> favorites;

        settings = context.getSharedPreferences(PREFS_NAME,
                Context.MODE_PRIVATE);

        if (settings.contains(FAVORITES)) {
            String jsonFavorites = settings.getString(FAVORITES, null);
            Gson gson = new Gson();
            Loyalty[] favoriteItems = gson.fromJson(jsonFavorites,
                    Loyalty[].class);

            favorites = Arrays.asList(favoriteItems);
            favorites = new ArrayList<Loyalty>(favorites);
        } else
            return null;

        return (ArrayList<Loyalty>) favorites;
    }
}

package net.frazionz.android.utils;

import net.frazionz.android.activity.ui.shop.ShopTypes;
import net.frazionz.android.auth.FzAuthProfile;
import net.frazionz.android.auth.FzProfileItem;

public class VarFz {

    public static String URL_API_BASE = "https://api.frazionz.net/";

    public static String PARAM_GET_POSTS_BY_PAGE = URL_API_BASE+"news?page={CURRENT_PAGE}";
    public static String PARAM_GET_POST_BY_ID = URL_API_BASE+"news/{ID}";
    public static String PARAM_GET_PROFILE = URL_API_BASE+"faction/profile/{ID}";
    public static String PARAM_GET_SHOP_TYPES = URL_API_BASE+"faction/shop/types";
    public static String PARAM_GET_SHOP_ITEMS = URL_API_BASE+"faction/shop/items/{ID}";
    public static String getParamGetPostsByPage(String currentPage) {
        return PARAM_GET_POSTS_BY_PAGE.replace("{CURRENT_PAGE}", currentPage);
    }
    public static String getParamGetProfile(FzProfileItem authProfile) {
        return PARAM_GET_PROFILE.replace("{ID}", authProfile.getUuid());
    }
    public static String getParamGetProfile(FzAuthProfile authProfile) {
        return PARAM_GET_PROFILE.replace("{ID}", authProfile.getUuid());
    }
    public static String getParamGetShopTypes() {
        return PARAM_GET_SHOP_TYPES;
    }
    public static String getParamGetShopItems(ShopTypes shopTypes) {
        return PARAM_GET_SHOP_ITEMS.replace("{ID}", String.valueOf(shopTypes.getId()));
    }
    public static String getParamGetPostById(int id) {
        return PARAM_GET_POST_BY_ID.replace("{ID}", String.valueOf(id));
    }

    //NOTIFS CHANNEL
    public enum ChokaNotifsChannel {

        POSTS("new-post", "Nouvelle article", "Lors d'une publication d'un article, vous recevez une notification.");

        private final String id;
        private final String title;
        private final String descritpion;

        ChokaNotifsChannel(String id, String title, String descritpion){
            this.id = id;
            this.title = title;
            this.descritpion = descritpion;
        }

        public String getId() {
            return id;
        }

        public String getTitle() {
            return title;
        }

        public String getDescritpion() {
            return descritpion;
        }
    }
}

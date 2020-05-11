package org.pesmypetcare.mypetcare.activities.views;

import android.content.Context;
import android.widget.LinearLayout;

import org.pesmypetcare.mypetcare.features.pets.WalkPets;

/**
 * @author Albert Pinto
 */
public class WalkRouteInfo {
    private LinearLayout infoLayout;

    private WalkRouteInfo(Context context, WalkPets walkPets) {
        infoLayout = new LinearLayout(context, null);
    }

    public static class Builder {
        private Context context;
        private WalkPets walkPets;

        public Builder(Context context) {
            this.context = context;
        }

        public WalkPets getWalkPets() {
            return walkPets;
        }

        public void setWalkPets(WalkPets walkPets) {
            this.walkPets = walkPets;
        }

        public WalkRouteInfo build() {
            return new WalkRouteInfo(context, walkPets);
        }
    }
}

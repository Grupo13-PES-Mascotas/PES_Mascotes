package org.pesmypetcare.mypetcare.activities.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import org.pesmypetcare.mypetcare.features.pets.WalkPets;

/**
 * @author Albert Pinto
 */
public class WalkRouteInfo extends View {

    private WalkRouteInfo(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    private WalkRouteInfo(Context context, @Nullable AttributeSet attrs, WalkPets walkPets) {
        super(context, attrs);
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
            return new WalkRouteInfo(context, null, walkPets);
        }
    }
}

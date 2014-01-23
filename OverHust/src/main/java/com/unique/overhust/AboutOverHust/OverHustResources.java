package com.unique.overhust.AboutOverHust;

import com.unique.overhust.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fhw on 12/29/13.
 */
public class OverHustResources {
    public static final List<Data> IMG_DESCRIPTIONS = new ArrayList<Data>();

    static {
        OverHustResources.IMG_DESCRIPTIONS.add(new Data(R.drawable.guide_main));
        OverHustResources.IMG_DESCRIPTIONS.add(new Data(R.drawable.guide_map));
        OverHustResources.IMG_DESCRIPTIONS.add(new Data(R.drawable.guide_navigation));
        OverHustResources.IMG_DESCRIPTIONS.add(new Data(R.drawable.guide_search));
        OverHustResources.IMG_DESCRIPTIONS.add(new Data(R.drawable.guide_drawerlayout));
        OverHustResources.IMG_DESCRIPTIONS.add(new Data(R.drawable.guide_swipelayout));

    }

    public static final class Data {
        public final int imageId;

        private Data(int imageId) {
            this.imageId = imageId;
        }
    }
}

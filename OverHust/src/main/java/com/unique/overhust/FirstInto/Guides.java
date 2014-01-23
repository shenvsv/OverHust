package com.unique.overhust.FirstInto;

import com.unique.overhust.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fhw on 12/24/13.
 */
public class Guides {
    public static final List<Data> IMG_DESCRIPTIONS = new ArrayList<Data>();

    static {
        Guides.IMG_DESCRIPTIONS.add(new Data(R.drawable.guide_main));
        Guides.IMG_DESCRIPTIONS.add(new Data(R.drawable.guide_map));
        Guides.IMG_DESCRIPTIONS.add(new Data(R.drawable.guide_navigation));
        Guides.IMG_DESCRIPTIONS.add(new Data(R.drawable.guide_search));
        Guides.IMG_DESCRIPTIONS.add(new Data(R.drawable.guide_drawerlayout));
        Guides.IMG_DESCRIPTIONS.add(new Data(R.drawable.guide_swipelayout));
        Guides.IMG_DESCRIPTIONS.add(new Data(R.drawable.ic_overhust));

    }

    public static final class Data {
        public final int imageId;

        private Data(int imageId) {
            this.imageId = imageId;
        }
    }
}

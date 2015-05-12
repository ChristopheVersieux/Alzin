package com.wazabe.folklore.guindailles.bo;

import java.util.ArrayList;

/**
 * Created by versieuxchristophe on 08/01/15.
 */
public class ChantApi {
    public Result results;

    public class Result {
        public ArrayList<Chant> chants;

        public class Chant {
            public Item item;

            public class Item {
                public String href;
                public String text;
                public String image;
                public String description;
            }
        }
    }
}

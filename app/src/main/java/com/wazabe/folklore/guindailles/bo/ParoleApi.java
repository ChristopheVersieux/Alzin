package com.wazabe.folklore.guindailles.bo;

import java.util.ArrayList;

/**
 * Created by versieuxchristophe on 08/01/15.
 */
public class ParoleApi {
    public Result results;

    public class Result {
        public ArrayList<Chant.Item> chant;

        public class Chant {
            public Item item;

            public class Item {
                public String titre;
                public String paroles;
            }
        }
    }
}

package com.example.readjson.ItemData;
//STEP 2:

import java.io.Serializable;

public class ItemSql implements Serializable {
    private String itemWork;
    private int nav;

    public ItemSql(String itemWork, int nav) {
        this.itemWork = itemWork;
        this.nav = nav;
    }

    public String getItemWork() {
        return itemWork.trim();
    }

    public void setItemWork(String itemWork) {
        this.itemWork = itemWork;
    }

    public int getNav() {
        return nav;
    }

    public void setNav(int nav) {
        this.nav = nav;
    }
}

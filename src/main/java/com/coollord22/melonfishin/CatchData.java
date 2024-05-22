package com.coollord22.melonfishin;

import java.util.Date;

public class CatchData {
    public String username;
    public String name;
    public String weight;
    public String value;
    public String rating;
    public String rarity;
    public Date date;

    @Override
    public String toString() {
        return this.username + " caught " + this.name + " at " + date;
    }
}

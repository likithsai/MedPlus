package com.likith.lockview;

/**
 * Created by Weiping on 2016/3/17.
 */
public enum Password {

    NUMBER(0),
    TEXT(1);

    private int type;

    Password(int type) {
        this.type = type;
    }

}
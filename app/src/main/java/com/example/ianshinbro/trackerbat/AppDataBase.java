package com.example.ianshinbro.trackerbat;

import com.raizlabs.android.dbflow.annotation.Database;

/**
 * Created by ianshinbro on 1/14/2017.
 */
@Database(name = AppDataBase.NAME, version = AppDataBase.VERSION)
public class AppDataBase {

    public static final String NAME = "trackerDatabase"; // we will add the .db extension

    public static final int VERSION = 1;
}

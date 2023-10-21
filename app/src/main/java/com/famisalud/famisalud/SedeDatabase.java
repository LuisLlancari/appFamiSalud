package com.famisalud.famisalud;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Sede.class}, version = 1, exportSchema = false)
public abstract class SedeDatabase extends RoomDatabase {
   public abstract SedeDao sedeDao();

   private static SedeDatabase instance;

   public static synchronized SedeDatabase getInstance(Context context) {
      if (instance == null) {
         instance = Room.databaseBuilder(context.getApplicationContext(),
                 SedeDatabase.class, "sede_database")
             .fallbackToDestructiveMigration()
             .build();
      }
      return instance;
   }
}

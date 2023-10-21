package com.famisalud.famisalud;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "sede_table")
public class Sede {
   @PrimaryKey
   @NonNull
   private String name;

   public Sede(@NonNull String name) {
      this.name = name;
   }


   public String getName() {
      return name;
   }
}

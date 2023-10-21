package com.famisalud.famisalud;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface SedeDao {
   @Insert
   void insert(Sede sede);

   @Query("SELECT * FROM sede_table")
   List<Sede> getAllSedes();
}

package com.example.crudapp.Database

import androidx.room.*

@Dao
interface AntrianDao {
    @Insert
    suspend fun addAntrian(antrian: Antrian)

    @Update
    suspend fun updateAntrian(antrian: Antrian)

    @Delete
    suspend fun deleteAntrian(antrian: Antrian)

    @Query("SELECT * FROM antrian")
    suspend fun getAllAntrian(): List<Antrian>

    @Query("SELECT * FROM antrian WHERE id=:antrian_id")
    suspend fun getAntrian(antrian_id: Int) : List<Antrian>

}
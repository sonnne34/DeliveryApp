package com.sushi.Sushi.dataBaseProfil

import androidx.room.*


@Dao
interface ProfileDataDao {

    @Query("SELECT * FROM profiledatamodel")
    fun getAll(): List<ProfileDataModel?>?

    @Query("SELECT * FROM profiledatamodel WHERE id = :id")
    fun getById(id: Long): ProfileDataModel?

    @Insert
    fun insert(profileData: ProfileDataModel?)

    @Update
    fun update(profileData: ProfileDataModel?)

    @Delete
    fun delete(profileData: ProfileDataModel?)

}
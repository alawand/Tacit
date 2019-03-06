package io.gudcodes.tacit

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*
import android.arch.persistence.room.Room
import android.content.Context


@Database(entities = [CallFilter::class], version = 2)
abstract class CallFilterDatabase : RoomDatabase() {
    abstract fun callFilterDao(): CallFilterDao

    companion object : SingletonHolder<CallFilterDatabase, Context>({
        Room.databaseBuilder(
            it.applicationContext,
            CallFilterDatabase::class.java,
            "Tacit.db"
        )
            .allowMainThreadQueries()
            .addMigrations(CALL_FILTER_MIGRATION_1_2)
            .build()
    })
}

@Dao
interface CallFilterDao {
    @Query("SELECT * FROM callfilter")
    fun getAll(): LiveData<List<CallFilter>>

    @Query("SELECT * FROM callfilter WHERE uid IN (:filterIds)")
    fun getAllByIds(filterIds: IntArray):  List<CallFilter>

    @Query("SELECT * FROM callfilter WHERE :tel GLOB filter")
    fun match(tel: String): List<CallFilter>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(filter: CallFilter)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertMultiple(vararg filters: CallFilter)

    @Delete
    fun delete(user: CallFilter)

    @Delete
    fun deleteMultiple(vararg filters: CallFilter)

    @Query("DELETE FROM callfilter")
    fun deleteAll()
}
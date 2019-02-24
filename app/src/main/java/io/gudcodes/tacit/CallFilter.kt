package io.gudcodes.tacit

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.arch.persistence.room.Index

@Entity(indices = [Index("filter", unique = true)])
data class CallFilter(
    @PrimaryKey(autoGenerate = true) var uid: Int = 0,
    @ColumnInfo(name = "filter") var filter: String
)

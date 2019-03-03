package io.gudcodes.tacit

import android.arch.persistence.db.SupportSQLiteDatabase
import android.arch.persistence.room.migration.Migration

val CALL_FILTER_MIGRATION_1_2: Migration = object : Migration(1, 2) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL("ALTER TABLE callfilter"
                + " ADD COLUMN rejectCall BOOLEAN NOT NULL DEFAULT 1")
        database.execSQL("ALTER TABLE callfilter"
                + " ADD COLUMN skipCallLog BOOLEAN NOT NULL DEFAULT 1")
        database.execSQL("ALTER TABLE callfilter"
                + " ADD COLUMN skipNotification BOOLEAN NOT NULL DEFAULT 1")
    }
}
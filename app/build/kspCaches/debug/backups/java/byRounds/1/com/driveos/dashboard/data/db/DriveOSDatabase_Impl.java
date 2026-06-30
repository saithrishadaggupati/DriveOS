package com.driveos.dashboard.data.db;

import androidx.annotation.NonNull;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomDatabase;
import androidx.room.RoomOpenHelper;
import androidx.room.migration.AutoMigrationSpec;
import androidx.room.migration.Migration;
import androidx.room.util.DBUtil;
import androidx.room.util.TableInfo;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
import java.lang.Class;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.processing.Generated;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class DriveOSDatabase_Impl extends DriveOSDatabase {
  private volatile TelemetryDao _telemetryDao;

  @Override
  @NonNull
  protected SupportSQLiteOpenHelper createOpenHelper(@NonNull final DatabaseConfiguration config) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(config, new RoomOpenHelper.Delegate(2) {
      @Override
      public void createAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS `telemetry_snapshots` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `timestamp` INTEGER NOT NULL, `speed` REAL NOT NULL, `rpm` REAL NOT NULL, `fuel` REAL NOT NULL, `engineTemp` REAL NOT NULL, `battery` REAL NOT NULL, `gear` INTEGER NOT NULL, `driveMode` INTEGER NOT NULL, `tripDistanceKm` REAL NOT NULL)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `dtc_codes` (`code` TEXT NOT NULL, `description` TEXT NOT NULL, `severity` TEXT NOT NULL, `timestamp` INTEGER NOT NULL, `isActive` INTEGER NOT NULL, PRIMARY KEY(`code`))");
        db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, '1972e64aaea73c518bbdc1af19739002')");
      }

      @Override
      public void dropAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS `telemetry_snapshots`");
        db.execSQL("DROP TABLE IF EXISTS `dtc_codes`");
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onDestructiveMigration(db);
          }
        }
      }

      @Override
      public void onCreate(@NonNull final SupportSQLiteDatabase db) {
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onCreate(db);
          }
        }
      }

      @Override
      public void onOpen(@NonNull final SupportSQLiteDatabase db) {
        mDatabase = db;
        internalInitInvalidationTracker(db);
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onOpen(db);
          }
        }
      }

      @Override
      public void onPreMigrate(@NonNull final SupportSQLiteDatabase db) {
        DBUtil.dropFtsSyncTriggers(db);
      }

      @Override
      public void onPostMigrate(@NonNull final SupportSQLiteDatabase db) {
      }

      @Override
      @NonNull
      public RoomOpenHelper.ValidationResult onValidateSchema(
          @NonNull final SupportSQLiteDatabase db) {
        final HashMap<String, TableInfo.Column> _columnsTelemetrySnapshots = new HashMap<String, TableInfo.Column>(10);
        _columnsTelemetrySnapshots.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTelemetrySnapshots.put("timestamp", new TableInfo.Column("timestamp", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTelemetrySnapshots.put("speed", new TableInfo.Column("speed", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTelemetrySnapshots.put("rpm", new TableInfo.Column("rpm", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTelemetrySnapshots.put("fuel", new TableInfo.Column("fuel", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTelemetrySnapshots.put("engineTemp", new TableInfo.Column("engineTemp", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTelemetrySnapshots.put("battery", new TableInfo.Column("battery", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTelemetrySnapshots.put("gear", new TableInfo.Column("gear", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTelemetrySnapshots.put("driveMode", new TableInfo.Column("driveMode", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTelemetrySnapshots.put("tripDistanceKm", new TableInfo.Column("tripDistanceKm", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysTelemetrySnapshots = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesTelemetrySnapshots = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoTelemetrySnapshots = new TableInfo("telemetry_snapshots", _columnsTelemetrySnapshots, _foreignKeysTelemetrySnapshots, _indicesTelemetrySnapshots);
        final TableInfo _existingTelemetrySnapshots = TableInfo.read(db, "telemetry_snapshots");
        if (!_infoTelemetrySnapshots.equals(_existingTelemetrySnapshots)) {
          return new RoomOpenHelper.ValidationResult(false, "telemetry_snapshots(com.driveos.dashboard.data.model.TelemetrySnapshot).\n"
                  + " Expected:\n" + _infoTelemetrySnapshots + "\n"
                  + " Found:\n" + _existingTelemetrySnapshots);
        }
        final HashMap<String, TableInfo.Column> _columnsDtcCodes = new HashMap<String, TableInfo.Column>(5);
        _columnsDtcCodes.put("code", new TableInfo.Column("code", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsDtcCodes.put("description", new TableInfo.Column("description", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsDtcCodes.put("severity", new TableInfo.Column("severity", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsDtcCodes.put("timestamp", new TableInfo.Column("timestamp", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsDtcCodes.put("isActive", new TableInfo.Column("isActive", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysDtcCodes = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesDtcCodes = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoDtcCodes = new TableInfo("dtc_codes", _columnsDtcCodes, _foreignKeysDtcCodes, _indicesDtcCodes);
        final TableInfo _existingDtcCodes = TableInfo.read(db, "dtc_codes");
        if (!_infoDtcCodes.equals(_existingDtcCodes)) {
          return new RoomOpenHelper.ValidationResult(false, "dtc_codes(com.driveos.dashboard.data.model.DTCCode).\n"
                  + " Expected:\n" + _infoDtcCodes + "\n"
                  + " Found:\n" + _existingDtcCodes);
        }
        return new RoomOpenHelper.ValidationResult(true, null);
      }
    }, "1972e64aaea73c518bbdc1af19739002", "3c9cd9b9a0d9744eafde59e19ee60dd0");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(config.context).name(config.name).callback(_openCallback).build();
    final SupportSQLiteOpenHelper _helper = config.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  @NonNull
  protected InvalidationTracker createInvalidationTracker() {
    final HashMap<String, String> _shadowTablesMap = new HashMap<String, String>(0);
    final HashMap<String, Set<String>> _viewTables = new HashMap<String, Set<String>>(0);
    return new InvalidationTracker(this, _shadowTablesMap, _viewTables, "telemetry_snapshots","dtc_codes");
  }

  @Override
  public void clearAllTables() {
    super.assertNotMainThread();
    final SupportSQLiteDatabase _db = super.getOpenHelper().getWritableDatabase();
    try {
      super.beginTransaction();
      _db.execSQL("DELETE FROM `telemetry_snapshots`");
      _db.execSQL("DELETE FROM `dtc_codes`");
      super.setTransactionSuccessful();
    } finally {
      super.endTransaction();
      _db.query("PRAGMA wal_checkpoint(FULL)").close();
      if (!_db.inTransaction()) {
        _db.execSQL("VACUUM");
      }
    }
  }

  @Override
  @NonNull
  protected Map<Class<?>, List<Class<?>>> getRequiredTypeConverters() {
    final HashMap<Class<?>, List<Class<?>>> _typeConvertersMap = new HashMap<Class<?>, List<Class<?>>>();
    _typeConvertersMap.put(TelemetryDao.class, TelemetryDao_Impl.getRequiredConverters());
    return _typeConvertersMap;
  }

  @Override
  @NonNull
  public Set<Class<? extends AutoMigrationSpec>> getRequiredAutoMigrationSpecs() {
    final HashSet<Class<? extends AutoMigrationSpec>> _autoMigrationSpecsSet = new HashSet<Class<? extends AutoMigrationSpec>>();
    return _autoMigrationSpecsSet;
  }

  @Override
  @NonNull
  public List<Migration> getAutoMigrations(
      @NonNull final Map<Class<? extends AutoMigrationSpec>, AutoMigrationSpec> autoMigrationSpecs) {
    final List<Migration> _autoMigrations = new ArrayList<Migration>();
    return _autoMigrations;
  }

  @Override
  public TelemetryDao telemetryDao() {
    if (_telemetryDao != null) {
      return _telemetryDao;
    } else {
      synchronized(this) {
        if(_telemetryDao == null) {
          _telemetryDao = new TelemetryDao_Impl(this);
        }
        return _telemetryDao;
      }
    }
  }
}

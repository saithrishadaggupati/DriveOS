package com.driveos.dashboard.data.db;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.driveos.dashboard.data.model.DTCCode;
import com.driveos.dashboard.data.model.TelemetrySnapshot;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Float;
import java.lang.IllegalArgumentException;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import javax.annotation.processing.Generated;
import kotlin.Unit;
import kotlin.coroutines.Continuation;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class TelemetryDao_Impl implements TelemetryDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<TelemetrySnapshot> __insertionAdapterOfTelemetrySnapshot;

  private final EntityInsertionAdapter<DTCCode> __insertionAdapterOfDTCCode;

  private final SharedSQLiteStatement __preparedStmtOfDeleteOldSnapshots;

  private final SharedSQLiteStatement __preparedStmtOfClearDTCCode;

  private final SharedSQLiteStatement __preparedStmtOfClearAllDTCCodes;

  public TelemetryDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfTelemetrySnapshot = new EntityInsertionAdapter<TelemetrySnapshot>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `telemetry_snapshots` (`id`,`timestamp`,`speed`,`rpm`,`fuel`,`engineTemp`,`battery`,`gear`,`driveMode`,`tripDistanceKm`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final TelemetrySnapshot entity) {
        statement.bindLong(1, entity.getId());
        statement.bindLong(2, entity.getTimestamp());
        statement.bindDouble(3, entity.getSpeed());
        statement.bindDouble(4, entity.getRpm());
        statement.bindDouble(5, entity.getFuel());
        statement.bindDouble(6, entity.getEngineTemp());
        statement.bindDouble(7, entity.getBattery());
        statement.bindLong(8, entity.getGear());
        statement.bindLong(9, entity.getDriveMode());
        statement.bindDouble(10, entity.getTripDistanceKm());
      }
    };
    this.__insertionAdapterOfDTCCode = new EntityInsertionAdapter<DTCCode>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `dtc_codes` (`code`,`description`,`severity`,`timestamp`,`isActive`) VALUES (?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final DTCCode entity) {
        statement.bindString(1, entity.getCode());
        statement.bindString(2, entity.getDescription());
        statement.bindString(3, __Severity_enumToString(entity.getSeverity()));
        statement.bindLong(4, entity.getTimestamp());
        final int _tmp = entity.isActive() ? 1 : 0;
        statement.bindLong(5, _tmp);
      }
    };
    this.__preparedStmtOfDeleteOldSnapshots = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM telemetry_snapshots WHERE timestamp < ?";
        return _query;
      }
    };
    this.__preparedStmtOfClearDTCCode = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE dtc_codes SET isActive = 0 WHERE code = ?";
        return _query;
      }
    };
    this.__preparedStmtOfClearAllDTCCodes = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM dtc_codes";
        return _query;
      }
    };
  }

  @Override
  public Object insertSnapshot(final TelemetrySnapshot snapshot,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfTelemetrySnapshot.insert(snapshot);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object insertDTCCode(final DTCCode code, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfDTCCode.insert(code);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteOldSnapshots(final long before,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteOldSnapshots.acquire();
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, before);
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfDeleteOldSnapshots.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object clearDTCCode(final String code, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfClearDTCCode.acquire();
        int _argIndex = 1;
        _stmt.bindString(_argIndex, code);
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfClearDTCCode.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object clearAllDTCCodes(final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfClearAllDTCCodes.acquire();
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfClearAllDTCCodes.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public LiveData<List<TelemetrySnapshot>> getRecentSnapshots() {
    final String _sql = "SELECT * FROM telemetry_snapshots ORDER BY timestamp DESC LIMIT 100";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return __db.getInvalidationTracker().createLiveData(new String[] {"telemetry_snapshots"}, false, new Callable<List<TelemetrySnapshot>>() {
      @Override
      @Nullable
      public List<TelemetrySnapshot> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfTimestamp = CursorUtil.getColumnIndexOrThrow(_cursor, "timestamp");
          final int _cursorIndexOfSpeed = CursorUtil.getColumnIndexOrThrow(_cursor, "speed");
          final int _cursorIndexOfRpm = CursorUtil.getColumnIndexOrThrow(_cursor, "rpm");
          final int _cursorIndexOfFuel = CursorUtil.getColumnIndexOrThrow(_cursor, "fuel");
          final int _cursorIndexOfEngineTemp = CursorUtil.getColumnIndexOrThrow(_cursor, "engineTemp");
          final int _cursorIndexOfBattery = CursorUtil.getColumnIndexOrThrow(_cursor, "battery");
          final int _cursorIndexOfGear = CursorUtil.getColumnIndexOrThrow(_cursor, "gear");
          final int _cursorIndexOfDriveMode = CursorUtil.getColumnIndexOrThrow(_cursor, "driveMode");
          final int _cursorIndexOfTripDistanceKm = CursorUtil.getColumnIndexOrThrow(_cursor, "tripDistanceKm");
          final List<TelemetrySnapshot> _result = new ArrayList<TelemetrySnapshot>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final TelemetrySnapshot _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final long _tmpTimestamp;
            _tmpTimestamp = _cursor.getLong(_cursorIndexOfTimestamp);
            final float _tmpSpeed;
            _tmpSpeed = _cursor.getFloat(_cursorIndexOfSpeed);
            final float _tmpRpm;
            _tmpRpm = _cursor.getFloat(_cursorIndexOfRpm);
            final float _tmpFuel;
            _tmpFuel = _cursor.getFloat(_cursorIndexOfFuel);
            final float _tmpEngineTemp;
            _tmpEngineTemp = _cursor.getFloat(_cursorIndexOfEngineTemp);
            final float _tmpBattery;
            _tmpBattery = _cursor.getFloat(_cursorIndexOfBattery);
            final int _tmpGear;
            _tmpGear = _cursor.getInt(_cursorIndexOfGear);
            final int _tmpDriveMode;
            _tmpDriveMode = _cursor.getInt(_cursorIndexOfDriveMode);
            final float _tmpTripDistanceKm;
            _tmpTripDistanceKm = _cursor.getFloat(_cursorIndexOfTripDistanceKm);
            _item = new TelemetrySnapshot(_tmpId,_tmpTimestamp,_tmpSpeed,_tmpRpm,_tmpFuel,_tmpEngineTemp,_tmpBattery,_tmpGear,_tmpDriveMode,_tmpTripDistanceKm);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Object getAverageSpeed(final long since, final Continuation<? super Float> $completion) {
    final String _sql = "SELECT AVG(speed) FROM telemetry_snapshots WHERE timestamp > ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, since);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Float>() {
      @Override
      @NonNull
      public Float call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final Float _result;
          if (_cursor.moveToFirst()) {
            final float _tmp;
            _tmp = _cursor.getFloat(0);
            _result = _tmp;
          } else {
            _result = 0f;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Object getMaxSpeed(final long since, final Continuation<? super Float> $completion) {
    final String _sql = "SELECT MAX(speed) FROM telemetry_snapshots WHERE timestamp > ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, since);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Float>() {
      @Override
      @NonNull
      public Float call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final Float _result;
          if (_cursor.moveToFirst()) {
            final float _tmp;
            _tmp = _cursor.getFloat(0);
            _result = _tmp;
          } else {
            _result = 0f;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Object getTripDistance(final long since, final Continuation<? super Float> $completion) {
    final String _sql = "SELECT SUM(tripDistanceKm) FROM telemetry_snapshots WHERE timestamp > ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, since);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Float>() {
      @Override
      @NonNull
      public Float call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final Float _result;
          if (_cursor.moveToFirst()) {
            final float _tmp;
            _tmp = _cursor.getFloat(0);
            _result = _tmp;
          } else {
            _result = 0f;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public LiveData<List<DTCCode>> getActiveDTCCodes() {
    final String _sql = "SELECT * FROM dtc_codes WHERE isActive = 1 ORDER BY timestamp DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return __db.getInvalidationTracker().createLiveData(new String[] {"dtc_codes"}, false, new Callable<List<DTCCode>>() {
      @Override
      @Nullable
      public List<DTCCode> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfCode = CursorUtil.getColumnIndexOrThrow(_cursor, "code");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfSeverity = CursorUtil.getColumnIndexOrThrow(_cursor, "severity");
          final int _cursorIndexOfTimestamp = CursorUtil.getColumnIndexOrThrow(_cursor, "timestamp");
          final int _cursorIndexOfIsActive = CursorUtil.getColumnIndexOrThrow(_cursor, "isActive");
          final List<DTCCode> _result = new ArrayList<DTCCode>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final DTCCode _item;
            final String _tmpCode;
            _tmpCode = _cursor.getString(_cursorIndexOfCode);
            final String _tmpDescription;
            _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
            final DTCCode.Severity _tmpSeverity;
            _tmpSeverity = __Severity_stringToEnum(_cursor.getString(_cursorIndexOfSeverity));
            final long _tmpTimestamp;
            _tmpTimestamp = _cursor.getLong(_cursorIndexOfTimestamp);
            final boolean _tmpIsActive;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsActive);
            _tmpIsActive = _tmp != 0;
            _item = new DTCCode(_tmpCode,_tmpDescription,_tmpSeverity,_tmpTimestamp,_tmpIsActive);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }

  private String __Severity_enumToString(@NonNull final DTCCode.Severity _value) {
    switch (_value) {
      case LOW: return "LOW";
      case MEDIUM: return "MEDIUM";
      case CRITICAL: return "CRITICAL";
      default: throw new IllegalArgumentException("Can't convert enum to string, unknown enum value: " + _value);
    }
  }

  private DTCCode.Severity __Severity_stringToEnum(@NonNull final String _value) {
    switch (_value) {
      case "LOW": return DTCCode.Severity.LOW;
      case "MEDIUM": return DTCCode.Severity.MEDIUM;
      case "CRITICAL": return DTCCode.Severity.CRITICAL;
      default: throw new IllegalArgumentException("Can't convert value to enum, unknown value: " + _value);
    }
  }
}

package com.driveos.dashboard.di;

import com.driveos.dashboard.data.db.DriveOSDatabase;
import com.driveos.dashboard.data.db.TelemetryDao;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata("javax.inject.Singleton")
@QualifierMetadata
@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes",
    "KotlinInternal",
    "KotlinInternalInJava",
    "cast",
    "deprecation"
})
public final class AppModule_ProvideTelemetryDaoFactory implements Factory<TelemetryDao> {
  private final Provider<DriveOSDatabase> dbProvider;

  public AppModule_ProvideTelemetryDaoFactory(Provider<DriveOSDatabase> dbProvider) {
    this.dbProvider = dbProvider;
  }

  @Override
  public TelemetryDao get() {
    return provideTelemetryDao(dbProvider.get());
  }

  public static AppModule_ProvideTelemetryDaoFactory create(Provider<DriveOSDatabase> dbProvider) {
    return new AppModule_ProvideTelemetryDaoFactory(dbProvider);
  }

  public static TelemetryDao provideTelemetryDao(DriveOSDatabase db) {
    return Preconditions.checkNotNullFromProvides(AppModule.INSTANCE.provideTelemetryDao(db));
  }
}

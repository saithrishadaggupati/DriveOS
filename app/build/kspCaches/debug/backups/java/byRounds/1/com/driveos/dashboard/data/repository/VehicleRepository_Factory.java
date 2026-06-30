package com.driveos.dashboard.data.repository;

import android.content.Context;
import com.driveos.dashboard.data.db.TelemetryDao;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata("javax.inject.Singleton")
@QualifierMetadata("dagger.hilt.android.qualifiers.ApplicationContext")
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
public final class VehicleRepository_Factory implements Factory<VehicleRepository> {
  private final Provider<Context> contextProvider;

  private final Provider<TelemetryDao> telemetryDaoProvider;

  public VehicleRepository_Factory(Provider<Context> contextProvider,
      Provider<TelemetryDao> telemetryDaoProvider) {
    this.contextProvider = contextProvider;
    this.telemetryDaoProvider = telemetryDaoProvider;
  }

  @Override
  public VehicleRepository get() {
    return newInstance(contextProvider.get(), telemetryDaoProvider.get());
  }

  public static VehicleRepository_Factory create(Provider<Context> contextProvider,
      Provider<TelemetryDao> telemetryDaoProvider) {
    return new VehicleRepository_Factory(contextProvider, telemetryDaoProvider);
  }

  public static VehicleRepository newInstance(Context context, TelemetryDao telemetryDao) {
    return new VehicleRepository(context, telemetryDao);
  }
}

package com.driveos.dashboard.ui.viewmodel;

import android.content.Context;
import com.driveos.dashboard.data.repository.VehicleRepository;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata
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
public final class DashboardViewModel_Factory implements Factory<DashboardViewModel> {
  private final Provider<Context> contextProvider;

  private final Provider<VehicleRepository> repositoryProvider;

  public DashboardViewModel_Factory(Provider<Context> contextProvider,
      Provider<VehicleRepository> repositoryProvider) {
    this.contextProvider = contextProvider;
    this.repositoryProvider = repositoryProvider;
  }

  @Override
  public DashboardViewModel get() {
    return newInstance(contextProvider.get(), repositoryProvider.get());
  }

  public static DashboardViewModel_Factory create(Provider<Context> contextProvider,
      Provider<VehicleRepository> repositoryProvider) {
    return new DashboardViewModel_Factory(contextProvider, repositoryProvider);
  }

  public static DashboardViewModel newInstance(Context context, VehicleRepository repository) {
    return new DashboardViewModel(context, repository);
  }
}

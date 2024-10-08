package se.braindome.skywatch.di

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import se.braindome.skywatch.SkywatchApplication
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideApplication(app: Application): SkywatchApplication = app as SkywatchApplication

    @Provides
    @Singleton
    fun provideContext(application: SkywatchApplication): Context = application.applicationContext
}
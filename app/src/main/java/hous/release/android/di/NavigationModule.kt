package hous.release.android.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.scopes.ActivityScoped
import hous.release.android.navigation.TodoNavigatorImpl
import hous.release.feature.todo.detail.navigation.TodoNavigator


@Module
@InstallIn(ActivityComponent::class)
abstract class NavigationModule {
    @Binds
    @ActivityScoped
    abstract fun bindsTodoNavigator(todoNavigatorImpl: TodoNavigatorImpl): TodoNavigator
}

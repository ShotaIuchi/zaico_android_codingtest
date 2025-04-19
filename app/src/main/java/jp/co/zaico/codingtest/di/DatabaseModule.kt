package jp.co.zaico.codingtest.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import jp.co.zaico.codingtest.data.datasource.InventoryDataSource
import jp.co.zaico.codingtest.data.datasource.InventoryDataSourceImpl

@Module
@InstallIn(SingletonComponent::class)
abstract class DatabaseModule {

    @Binds
    abstract fun bindInventoryDataSource(
        impl: InventoryDataSourceImpl
    ): InventoryDataSource


}
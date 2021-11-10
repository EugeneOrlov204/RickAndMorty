/*
 * Copyright (C) 2019 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.shpp.eorlov.rickandmorty.di

import android.content.Context
import com.shpp.eorlov.rickandmorty.db.ContactsDatabaseImpl
import com.shpp.eorlov.rickandmorty.db.ContactsDatabase
import com.shpp.eorlov.rickandmorty.storage.SharedPreferencesStorageImpl
import com.shpp.eorlov.rickandmorty.storage.SharedPreferencesStorage
import dagger.Module
import dagger.Provides


@Module
class StorageModule {

    @Provides
    fun provideStorage(context: Context): SharedPreferencesStorage {
        return SharedPreferencesStorageImpl(context)
    }

    @Provides
    fun provideDatabase(context: Context): ContactsDatabase {
        return ContactsDatabaseImpl()
    }
}
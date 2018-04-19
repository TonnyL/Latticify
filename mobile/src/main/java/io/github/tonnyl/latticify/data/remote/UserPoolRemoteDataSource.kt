package io.github.tonnyl.latticify.data.remote

import android.content.Context
import io.github.tonnyl.latticify.data.User
import io.github.tonnyl.latticify.data.datasource.UserPoolDataSource
import io.github.tonnyl.latticify.retrofit.RetrofitClient
import io.github.tonnyl.latticify.retrofit.service.UsersService
import io.reactivex.Observable

object UserPoolRemoteDataSource : UserPoolDataSource {

    private val mUsersService = RetrofitClient.createService(UsersService::class.java)

    override fun init(context: Context) {

    }

    override fun addUser(user: User) {
        // Not required because the [UserPoolRepository] handles the logic
        // of refreshing the packages from all available data source
    }

    override fun addUsers(users: List<User>) {
        // Not required because the [UserPoolRepository] handles the logic
        // of refreshing the packages from all available data source
    }

    override fun removeUser(user: User) {
        // Not required because the [UserPoolRepository] handles the logic
        // of refreshing the packages from all available data source
    }

    override fun removeUsers(users: List<User>) {
        // Not required because the [UserPoolRepository] handles the logic
        // of refreshing the packages from all available data source
    }

    override fun getUser(userId: String): Observable<User> {
        return mUsersService.info(RetrofitClient.mToken, userId, true)
                .map { it.user }
    }

    override fun updateUser(user: User) {
        // Not required because the [UserPoolRepository] handles the logic
        // of refreshing the packages from all available data source
    }

}
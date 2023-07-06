package com.example.testcase.feature_user.data.repository

import android.content.ContentValues
import android.content.Context
import com.example.testcase.feature_user.domain.repository.UserService
import com.example.testcase.feature_user.data.data_source.DatabaseHelper
import com.example.testcase.feature_user.domain.model.Repository
import com.example.testcase.feature_user.domain.model.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class UserRepository(context: Context) {
    private val userService: UserService
    private val databaseHelper: DatabaseHelper

    init {
        val retrofit = Retrofit.Builder().baseUrl("https://api.github.com/")
            .addConverterFactory(GsonConverterFactory.create()).build()

        userService = retrofit.create(UserService::class.java)
        databaseHelper = DatabaseHelper(context)
    }

    fun getUsersFromAPI(callback: (List<User>?) -> Unit) {
        userService.getUsers().enqueue(object : Callback<List<User>> {
            override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {
                if (response.isSuccessful) {
                    val userList = response.body()
                    callback(userList)
                    cacheUsers(userList)
                } else {
                    callback(getUsersFromCache())
                }
            }

            override fun onFailure(call: Call<List<User>>, t: Throwable) {
                callback(getUsersFromCache())
            }
        })
    }

    fun getRepositoriesFromAPI(login: String, callback: (List<Repository>?) -> Unit) {
        userService.getUserRepos(login).enqueue(object : Callback<List<Repository>> {
            override fun onResponse(
                call: Call<List<Repository>>,
                response: Response<List<Repository>>
            ) {
                if (response.isSuccessful) {
                    val repositoriesList = response.body()
                    callback(repositoriesList)
                    cacheRepositories(repositoriesList, login)
                } else {
                    callback(getRepositoriesFromCache(login))
                }
            }

            override fun onFailure(call: Call<List<Repository>>, t: Throwable) {
                callback(getRepositoriesFromCache(login))
            }
        })
    }

    private fun cacheUsers(userList: List<User>?) {
        userList?.let {
            val db = databaseHelper.writableDatabase
            for (user in userList) {
                val query = """
                SELECT ${DatabaseHelper.COLUMN_ID} 
                FROM ${DatabaseHelper.TABLE_USERS} 
                WHERE ${DatabaseHelper.COLUMN_ID} = ?
                """.trimIndent()
                val cursor = db.rawQuery(query, arrayOf("${user.id}"))
                val values = ContentValues().apply {
                    put(DatabaseHelper.COLUMN_ID, user.id)
                    put(DatabaseHelper.COLUMN_LOGIN, user.login)
                    put(DatabaseHelper.COLUMN_NODE_ID, user.node_id)
                    put(DatabaseHelper.COLUMN_AVATAR_URL, user.avatar_url)
                    put(DatabaseHelper.COLUMN_GRAVATAR_ID, user.gravatar_id)
                    put(DatabaseHelper.COLUMN_URL, user.url)
                    put(DatabaseHelper.COLUMN_HTML_URL, user.html_url)
                    put(DatabaseHelper.COLUMN_FOLLOWERS_URL, user.followers_url)
                    put(DatabaseHelper.COLUMN_FOLLOWING_URL, user.following_url)
                    put(DatabaseHelper.COLUMN_GISTS_URL, user.gists_url)
                    put(DatabaseHelper.COLUMN_STARRED_URL, user.starred_url)
                    put(DatabaseHelper.COLUMN_SUBSCRIPTIONS_URL, user.subscriptions_url)
                    put(DatabaseHelper.COLUMN_ORGANIZATIONS_URL, user.organizations_url)
                    put(DatabaseHelper.COLUMN_REPOS_URL, user.repos_url)
                    put(DatabaseHelper.COLUMN_EVENTS_URL, user.events_url)
                    put(DatabaseHelper.COLUMN_RECEIVED_EVENTS_URL, user.received_events_url)
                    put(DatabaseHelper.COLUMN_TYPE, user.type)
                    put(DatabaseHelper.COLUMN_SITE_ADMIN, user.site_admin)
                }
                if (cursor.count == 0) {
                    db.insert(DatabaseHelper.TABLE_USERS, null, values)
                } else {
                    db.update(
                        DatabaseHelper.TABLE_USERS,
                        values,
                        "${DatabaseHelper.COLUMN_ID} = ?",
                        arrayOf(user.id.toString())
                    )
                }
            }
            db.close()
        }
    }

    private fun cacheRepositories(repositoriesList: List<Repository>?, login: String) {
        repositoriesList?.let {
            val db = databaseHelper.writableDatabase
            for (repository in repositoriesList) {
                val query = """
                SELECT ${DatabaseHelper.COLUMN_ID} 
                FROM ${DatabaseHelper.TABLE_REPOSITORIES} 
                WHERE ${DatabaseHelper.COLUMN_ID} = ?
                """.trimIndent()
                val cursor = db.rawQuery(query, arrayOf("${repository.id}"))
                val values = ContentValues().apply {
                    put(DatabaseHelper.COLUMN_ID, repository.id)
                    put(DatabaseHelper.COLUMN_FULL_NAME, repository.full_name)
                    put(DatabaseHelper.COLUMN_LANGUAGE, repository.language)
                    put(DatabaseHelper.COLUMN_VISIBILITY, repository.visibility)
                    put(DatabaseHelper.COLUMN_LOGIN, login)
                }
                if (cursor.count == 0) {
                    db.insert(DatabaseHelper.TABLE_REPOSITORIES, null, values)
                } else {
                    db.update(
                        DatabaseHelper.TABLE_REPOSITORIES,
                        values,
                        "${DatabaseHelper.COLUMN_ID} = ?",
                        arrayOf(repository.id.toString())
                    )
                }
            }
            db.close()
        }
    }

    fun getUsersFromCache(): List<User> {
        val db = databaseHelper.readableDatabase
        val cursor = db.rawQuery(
            "SELECT * FROM ${DatabaseHelper.TABLE_USERS}", null
        )

        val userList = mutableListOf<User>()

        while (cursor.moveToNext()) {
            val id =
                cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_ID))
            val login =
                cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_LOGIN))
            val nodeId =
                cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_NODE_ID))
            val avatarUrl =
                cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_AVATAR_URL))
            val gravatarId =
                cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_GRAVATAR_ID))
            val url =
                cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_URL))
            val htmlUrl =
                cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_HTML_URL))
            val followersUrl =
                cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_FOLLOWERS_URL))
            val followingUrl =
                cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_FOLLOWING_URL))
            val gistsUrl =
                cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_GISTS_URL))
            val starredUrl =
                cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_STARRED_URL))
            val subscriptionsUrl =
                cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_SUBSCRIPTIONS_URL))
            val organizationsUrl =
                cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_ORGANIZATIONS_URL))
            val reposUrl =
                cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_REPOS_URL))
            val eventsUrl =
                cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_EVENTS_URL))
            val receivedEventsUrl =
                cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_RECEIVED_EVENTS_URL))
            val type =
                cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_TYPE))
            val siteAdmin =
                cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_SITE_ADMIN))
                    .equals("true")
            val user = User(
                login,
                id,
                nodeId,
                avatarUrl,
                gravatarId,
                url,
                htmlUrl,
                followersUrl,
                followingUrl,
                gistsUrl,
                starredUrl,
                subscriptionsUrl,
                organizationsUrl,
                reposUrl,
                eventsUrl,
                receivedEventsUrl,
                type,
                siteAdmin
            )
            userList.add(user)
        }

        cursor.close()
        db.close()

        return userList
    }

    fun getRepositoriesFromCache(login: String): List<Repository> {
        val db = databaseHelper.readableDatabase

        val query = """
                SELECT * 
                FROM ${DatabaseHelper.TABLE_REPOSITORIES} 
                WHERE ${DatabaseHelper.COLUMN_LOGIN} = ?
                """.trimIndent()
        val cursor = db.rawQuery(query, arrayOf(login))

        val repositoriesList = mutableListOf<Repository>()

        while (cursor.moveToNext()) {
            val id =
                cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_ID))
            val fullName =
                cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_FULL_NAME))
            val language =
                if (cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_LANGUAGE)) != null) {
                    cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_LANGUAGE))
                } else {
                    ""
                }
            val visibility =
                cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_VISIBILITY))
            val repository = Repository(
                id,
                fullName,
                language,
                visibility
            )
            repositoriesList.add(repository)
        }

        cursor.close()
        db.close()

        return repositoriesList
    }
}
package com.example.testcase.feature_user.data.data_source

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object {
        private const val DATABASE_NAME = "users.db"
        private const val DATABASE_VERSION = 1

        const val TABLE_USERS = "users"
        const val COLUMN_LOGIN = "login"
        const val COLUMN_ID = "id"
        const val COLUMN_NODE_ID = "node_id"
        const val COLUMN_AVATAR_URL = "avatar_url"
        const val COLUMN_GRAVATAR_ID = "gravatar_id"
        const val COLUMN_URL = "url"
        const val COLUMN_HTML_URL = "html_url"
        const val COLUMN_FOLLOWERS_URL = "followers_url"
        const val COLUMN_FOLLOWING_URL = "following_url"
        const val COLUMN_GISTS_URL = "gists_url"
        const val COLUMN_STARRED_URL = "starred_url"
        const val COLUMN_SUBSCRIPTIONS_URL = "subscriptions_url"
        const val COLUMN_ORGANIZATIONS_URL = "organizations_url"
        const val COLUMN_REPOS_URL = "repos_url"
        const val COLUMN_EVENTS_URL = "events_url"
        const val COLUMN_RECEIVED_EVENTS_URL = "received_events_url"
        const val COLUMN_TYPE = "type"
        const val COLUMN_SITE_ADMIN = "site_admin"

        const val TABLE_REPOSITORIES = "repositories"
        const val COLUMN_FULL_NAME = "full_name"
        const val COLUMN_LANGUAGE = "language"
        const val COLUMN_VISIBILITY = "visibility"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createUsersTableQuery = """
            CREATE TABLE $TABLE_USERS (
                $COLUMN_ID INTEGER PRIMARY KEY,
                $COLUMN_LOGIN TEXT,
                $COLUMN_NODE_ID TEXT,
                $COLUMN_AVATAR_URL TEXT,
                $COLUMN_GRAVATAR_ID TEXT,
                $COLUMN_URL TEXT,
                $COLUMN_HTML_URL TEXT,
                $COLUMN_FOLLOWERS_URL TEXT,
                $COLUMN_FOLLOWING_URL TEXT,
                $COLUMN_GISTS_URL TEXT,
                $COLUMN_STARRED_URL TEXT,
                $COLUMN_SUBSCRIPTIONS_URL TEXT,
                $COLUMN_ORGANIZATIONS_URL TEXT,
                $COLUMN_REPOS_URL TEXT,
                $COLUMN_EVENTS_URL TEXT,
                $COLUMN_RECEIVED_EVENTS_URL TEXT,
                $COLUMN_TYPE TEXT,
                $COLUMN_SITE_ADMIN BOOLEAN
            )
        """.trimIndent()
        db?.execSQL(createUsersTableQuery)

        val createRepositoriesTableQuery = """
            CREATE TABLE $TABLE_REPOSITORIES (
                $COLUMN_ID INTEGER PRIMARY KEY,
                $COLUMN_FULL_NAME TEXT,
                $COLUMN_LANGUAGE TEXT,
                $COLUMN_VISIBILITY TEXT,
                $COLUMN_LOGIN TEXT,
                FOREIGN KEY ($COLUMN_LOGIN) REFERENCES $TABLE_USERS($COLUMN_LOGIN)
            )
        """.trimIndent()
        db?.execSQL(createRepositoriesTableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        val dropUsersTableQuery = "DROP TABLE IF EXISTS $TABLE_USERS"
        db?.execSQL(dropUsersTableQuery)
        val dropRepositoriesTableQuery = "DROP TABLE IF EXISTS $TABLE_REPOSITORIES"
        db?.execSQL(dropRepositoriesTableQuery)
        onCreate(db)
    }
}
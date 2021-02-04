/*
 * 2020 MarvelDroid , Attribution Images belong to Marvel Entertainment
 * Source Timestamps exist under: https://github.com/PabloRosas17
 * Written by Jose Pablo Rosas  <pablo.rosas.0170@gmail.com>, January 2020
 */

package organization.fizzy.marveldroid.tools.services.api

import com.google.gson.JsonObject
import organization.fizzy.marveldroid.model.AccountWrapper
import organization.fizzy.marveldroid.model.KeyWrapper
import organization.fizzy.marveldroid.tools.constants.FirebaseDbConst.FIREBASE_RETROFIT_MARVEL_ENDPOINT
import organization.fizzy.marveldroid.tools.constants.FirebaseDbConst.QUERY_CLAUSE_ENDAT
import organization.fizzy.marveldroid.tools.constants.FirebaseDbConst.QUERY_CLAUSE_ORDERBY
import organization.fizzy.marveldroid.tools.constants.FirebaseDbConst.QUERY_CLAUSE_STARTAT
import retrofit2.Call
import retrofit2.http.*

/**
 * @author, evolandlupiz
 * @date, 2/28/2020
 * @property, MarvelDroid
 * @purpose, Service for Firebase that defines REST api.
 */

/**
 * @desc firebase service.
 */
interface FirebaseService {

    /**
     * @API_FORMAT "/{database}/{object}/{...}/{...}/{...}/{json}") for Marvel Accounts
     */

    /**
     * get a particular account , filter by account username.
     */
    @GET(FIREBASE_RETROFIT_MARVEL_ENDPOINT)
    fun getMarvelAct(
        @Query(QUERY_CLAUSE_ORDERBY) orderBy : String
        , @Query(QUERY_CLAUSE_STARTAT) startAt : String
        , @Query(QUERY_CLAUSE_ENDAT) endAt : String
    ): Call<JsonObject>

    /**
     * post a particular account with account details as the object.
     */
    @POST(FIREBASE_RETROFIT_MARVEL_ENDPOINT)
    fun postMarvelAct(
        @Body account: AccountWrapper
    ): Call<JsonObject>

    /**
     * put an update on an existing account , filter by account unique id.
     */
    @PUT("marvel-accounts/{key}.json")
    fun putUpdateOnMarvelAct(
        @Path("key") key: String
        , @Body account: AccountWrapper
    ) : Call<KeyWrapper>
}
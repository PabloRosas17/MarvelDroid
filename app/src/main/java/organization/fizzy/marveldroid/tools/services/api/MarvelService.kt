/*
 * 2020 MarvelDroid , Attribution Images belong to Marvel Entertainment
 * Source Timestamps exist under: https://github.com/PabloRosas17
 * Written by Jose Pablo Rosas  <pablo.rosas.0170@gmail.com>, January 2020
 */

package organization.fizzy.marveldroid.tools.services.api

import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * @desc request service which will make itself available at the core module through di
 * @return callback to the specified class type Call<ClassType>
 */
interface MarvelService {

    /* @API_FORMAT "/{version}/{scope}/{api}/{ts}/{apikey}/{hash}") */
    /* https://developer.marvel.com/documentation/authorization */

    @GET("v1/public/comics")
    fun getComics(
        @Query("ts", encoded = true) ts: String
        , @Query("apikey") apikey: String
        , @Query("hash") hash: String
        , @Query("limit") limit: String
    ) : Call<JsonObject>
}
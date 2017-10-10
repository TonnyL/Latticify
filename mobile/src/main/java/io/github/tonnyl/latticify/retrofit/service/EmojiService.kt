package io.github.tonnyl.latticify.retrofit.service

import io.github.tonnyl.latticify.data.EmojisWrapper
import io.reactivex.Observable
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

/**
 * Created by lizhaotailang on 09/10/2017.
 */
interface EmojiService {

    /**
     * Lists custom emoji for a team.
     *
     * @param token Required. Authentication token bearing required scopes.
     *
     * @return If successful, the command returns an [EmojisWrapper] object.
     * The emoji property contains a map of name/url pairs, one for each custom emoji used by the team.
     * The alias: pseudo-protocol will be used where the emoji is an alias, the string following the colon
     * is the name of the other emoji this emoji is an alias to.
     */
    @POST("emoji.list")
    @FormUrlEncoded
    fun list(@Field("token") token: String): Observable<EmojisWrapper>

}
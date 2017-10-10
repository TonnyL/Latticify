package io.github.tonnyl.latticify.data.datasource

/**
 * Created by lizhaotailang on 06/10/2017.
 */
interface ChatDataSource {

    fun delete()

    fun meMessage()

    fun postEphemeral()

    fun postMessage()

    fun unfurl()

    fun update()

}
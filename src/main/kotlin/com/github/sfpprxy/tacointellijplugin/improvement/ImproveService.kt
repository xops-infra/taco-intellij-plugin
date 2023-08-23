package com.github.sfpprxy.tacointellijplugin.improvement

import com.github.sfpprxy.tacointellijplugin.settings.SettingsState
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.intellij.openapi.components.Service
import com.intellij.openapi.components.service
import com.intellij.openapi.diagnostic.thisLogger
import com.intellij.util.io.HttpRequests

@Service
class ImproveService {

    fun improve(ctx: ImprovementContext): Improvement {
        val improvementContext = gson.toJson(ctx)
        thisLogger().debug("improvementContext: $improvementContext")

        val url = SettingsState.getInstance().endpointUrl + API_PATH
        val improvementRes = HttpRequests.post(url, HttpRequests.JSON_CONTENT_TYPE)
            .throwStatusCodeException(false)
            .connect {
                it.write(improvementContext)
                it.readString()
            }
        thisLogger().debug("improvementRes: $improvementRes")

        return gson.fromJson(improvementRes, Improvement::class.java)
    }


    companion object {
        private const val API_PATH = "/code/improvement"

        fun getInstance(): ImproveService = service()

        private val gson: Gson = GsonBuilder()
            .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
            .create()
    }
}

package com.deutschb1.data

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

/**
 * Repository for offline verb conjugations.
 * Provides lookup and autocomplete suggestions for B1 German verbs.
 */
object VerbRepository {
    private var verbs: List<VerbConjugation>? = null

    fun loadVerbs(context: Context): List<VerbConjugation> {
        if (verbs != null) return verbs!!
        return try {
            val json = context.assets.open("verbs/conjugations.json").bufferedReader().readText()
            val type = object : TypeToken<VerbDataWrapper>() {}.type
            verbs = Gson().fromJson<VerbDataWrapper>(json, type).verbs
            verbs!!
        } catch (e: Exception) {
            emptyList()
        }
    }

    fun searchVerb(context: Context, query: String): VerbConjugation? {
        val q = query.trim().lowercase()
        return loadVerbs(context).find { it.infinitiv.lowercase() == q }
    }

    fun getSuggestions(context: Context, prefix: String): List<String> {
        val p = prefix.trim().lowercase()
        if (p.length < 2) return emptyList()
        return loadVerbs(context)
            .filter { it.infinitiv.startsWith(p) }
            .map { it.infinitiv }
            .take(8)
    }
}

data class VerbDataWrapper(val verbs: List<VerbConjugation>)

data class VerbConjugation(
    val infinitiv: String,
    val auxiliary: String,
    val praesens: Map<String, String>,
    val praeteritum: Map<String, String>,
    val perfekt: Map<String, String>,
    val konjunktiv2: Map<String, String>,
    val imperativ: Map<String, String>
)

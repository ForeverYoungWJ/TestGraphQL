package com.demo.pokemon.datasource

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.demo.pokemon.DemonPokemonQuery
import com.demo.pokemon.server.MyApolloClient
import com.demo.pokemon.utils.ToastUtils
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class DataRepository : ViewModel() {

    var loading: MutableStateFlow<Boolean> = MutableStateFlow(false)
    var empty: MutableStateFlow<Boolean> = MutableStateFlow(true)
    var searchName: MutableStateFlow<String?> = MutableStateFlow(null)
    var pokemonListData: MutableStateFlow<List<DemonPokemonQuery.Pokemon_v2_pokemonspecy>> =
        MutableStateFlow(
            emptyList()
        )

    fun fetch() {
        if (loading.value) return
        if (searchName.value.isNullOrBlank()) return
        searchName.value?.let {
            loading.value = true
            viewModelScope.launch {
                kotlin.runCatching {
                    val response = MyApolloClient.apollo.query(DemonPokemonQuery(it)).execute()
                    response.data?.pokemon_v2_pokemonspecies?.takeIf { it.isNotEmpty() }?.let {
                        empty.value = false
                        pokemonListData.value = it
                    } ?: kotlin.run {
                        empty.value = true
                    }
                    loading.value = false
                }.getOrElse {
                    loading.value = false
                    ToastUtils.showShortToast("网络异常，请稍后重试")
                }
            }
        }
    }
}
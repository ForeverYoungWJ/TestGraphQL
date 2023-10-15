package com.demo.pokemon.server

import com.apollographql.apollo3.ApolloClient
import com.demo.pokemon.SERVER_URL

object MyApolloClient {
    val apollo: ApolloClient by lazy {
        ApolloClient.builder()
            .serverUrl(SERVER_URL)
            .build()
    }
}


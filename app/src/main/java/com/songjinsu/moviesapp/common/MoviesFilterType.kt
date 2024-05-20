package com.songjinsu.moviesapp.common

enum class MoviesFilterType {
    NOW_PLAYING {
        override fun toString(): String = "now_playing"
    },

    POPULAR {
        override fun toString(): String = "popular"
    },

    TOP_RATED {
        override fun toString(): String = "top_rated"
    },

    UPCOMING {
        override fun toString(): String = "upcoming"
    }
}
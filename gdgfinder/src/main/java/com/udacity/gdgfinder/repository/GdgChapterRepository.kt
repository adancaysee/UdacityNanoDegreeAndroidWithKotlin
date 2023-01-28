package com.udacity.gdgfinder.repository

import com.udacity.gdgfinder.domain.GdgChapter

interface GdgChapterRepository {

    suspend fun getChapters() : List<GdgChapter>
}
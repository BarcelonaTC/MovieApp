package com.karrar.movieapp.data.repository.mediaDataSource.movie

import com.karrar.movieapp.data.remote.response.MovieDto
import com.karrar.movieapp.data.remote.service.MovieService
import com.karrar.movieapp.data.repository.mediaDataSource.BasePagingSource
import javax.inject.Inject
import kotlin.properties.Delegates

class MovieBySortDataSource @Inject constructor(private val service: MovieService) :
    BasePagingSource<MovieDto>() {

    private var mediaSortBy by Delegates.notNull<String>()

    fun setSortBy(sortBy: String) {
        mediaSortBy = sortBy
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieDto> {
        val pageNumber = params.key ?: 1

        return try {
            val response = service.getDiscoverMovie(sortBy = mediaSortBy, page = pageNumber)
            LoadResult.Page(
                data = response.body()?.items as List<MovieDto>,
                prevKey = null,
                nextKey = response.body()?.page?.plus(1)
            )
        } catch (e: Throwable) {
            LoadResult.Error(e)
        }
    }
}
package com.karrar.movieapp.domain.usecases.mylist

import com.karrar.movieapp.data.repository.AccountRepository
import com.karrar.movieapp.data.repository.MovieRepository
import com.karrar.movieapp.utilities.ErrorUI
import javax.inject.Inject

class RemoveMovieFromListUseCase @Inject constructor(
    private val accountRepository: AccountRepository,
    private val movieRepository: MovieRepository
) {
    suspend operator fun invoke(listID: Int, mediaId: Int): String {
        val sessionID = accountRepository.getSessionId()
        return sessionID?.let {
            movieRepository.removeMovieFromList(it, listID, mediaId)
            "Success: The movie has been deleted successfully"
        } ?: throw Throwable(ErrorUI.NO_LOGIN)
    }
}

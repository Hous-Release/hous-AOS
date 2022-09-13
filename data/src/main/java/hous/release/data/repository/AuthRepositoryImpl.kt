package hous.release.data.repository

import hous.release.data.source.AuthDataSource
import hous.release.domain.repository.AuthRepository
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authDataSource: AuthDataSource
) : AuthRepository

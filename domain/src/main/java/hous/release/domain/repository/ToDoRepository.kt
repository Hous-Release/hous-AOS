package hous.release.domain.repository

import hous.release.domain.entity.response.ToDoMain

interface ToDoRepository {
    suspend fun getToDoMainContent(): Result<ToDoMain>
}

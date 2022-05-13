package xyz.zohre.domain

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import xyz.zohre.domain.entities.ApiResult

/**
 * Executes business logic in its execute method and keep posting updates to the result as
 * [Result<R>].
 * Handling an exception (emit [ApiResult.Error] to the result) is the subclasses responsibility.
 */
interface FlowRepository<in P, R> {
    val coroutineDispatcher: CoroutineDispatcher

    suspend operator fun invoke(parameters: P): Flow<ApiResult<R>> = execute(parameters)
        .catch { e -> emit(ApiResult.Error(Exception(e))) }
        .flowOn(coroutineDispatcher)

    suspend fun execute(parameters: P): Flow<ApiResult<R>>
}
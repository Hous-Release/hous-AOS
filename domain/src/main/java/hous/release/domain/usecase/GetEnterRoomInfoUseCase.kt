package hous.release.domain.usecase

import hous.release.domain.repository.EnterRoomRepository
import javax.inject.Inject

class GetEnterRoomInfoUseCase @Inject constructor(
    private val enterRoomRepository: EnterRoomRepository
) {
    suspend operator fun invoke() = enterRoomRepository.getEnterRoomInfo()
}

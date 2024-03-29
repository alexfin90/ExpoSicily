package com.softdream.exposicily.domain

import com.softdream.exposicily.data.LocationRepository
import javax.inject.Inject

class GetLocationByIDUseCase @Inject constructor(private val repository: LocationRepository) {
    suspend operator fun invoke(id: Int): Location? {
        return repository.getLocationByID(id)
    }
}
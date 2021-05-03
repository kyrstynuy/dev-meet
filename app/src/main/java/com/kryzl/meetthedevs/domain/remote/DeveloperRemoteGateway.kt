package com.kryzl.meetthedevs.domain.remote

import com.kryzl.meetthedevs.base.exception.Failure
import com.kryzl.meetthedevs.base.functional.Either
import com.kryzl.meetthedevs.domain.Developer
import com.kryzl.meetthedevs.domain.DeveloperGateway
import javax.inject.Inject

class DeveloperRemoteGateway @Inject constructor(
    private val service: DeveloperApiService
): DeveloperGateway, RemoteDataSource() {

    override fun getDevelopers(): Either<Failure, List<Developer>> {
        return request(
            service.getDevelopers(),
            { it.developers }, DevelopersList.EmptyResponse
        )
    }

    override fun addDeveloper(developer: Developer): Either<Failure, Developer> {
        return request(
            service.addDeveloper(developer),
            { it }, Developer.EmptyResponse
        )
    }

    override fun updateDeveloper(developer: Developer): Either<Failure, Developer> {
        return request(
            service.updateDeveloper(developer.id!!, developer),
            { it }, Developer.EmptyResponse
        )
    }

    override fun deleteDeveloper(id: String): Either<Failure, Unit> {
        return request(service.deleteDeveloper(id), { }, Unit)
    }

}

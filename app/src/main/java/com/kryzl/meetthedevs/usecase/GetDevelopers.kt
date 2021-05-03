package com.kryzl.meetthedevs.usecase

import com.kryzl.meetthedevs.base.functional.UseCase
import com.kryzl.meetthedevs.domain.Developer
import com.kryzl.meetthedevs.domain.DeveloperGateway
import javax.inject.Inject

class GetDevelopers @Inject constructor(
    private val developerGateway: DeveloperGateway
    ): UseCase<Unit, List<Developer>>() {

    override var request: Unit = Unit
    override val coroutineContext: CoroutineContext = CoroutineContext.IO

    override suspend fun run() {
        setResult(developerGateway.getDevelopers())
    }

}

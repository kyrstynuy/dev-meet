package com.kryzl.meetthedevs.usecase

import com.kryzl.meetthedevs.base.functional.UseCase
import com.kryzl.meetthedevs.domain.Developer
import com.kryzl.meetthedevs.domain.DeveloperGateway
import javax.inject.Inject

class AddDeveloper @Inject constructor(
    private val developerGateway: DeveloperGateway
    ): UseCase<Developer, Developer>() {

    override lateinit var request: Developer

    override val coroutineContext: CoroutineContext = CoroutineContext.IO

    override suspend fun run() {
        setResult(developerGateway.addDeveloper(request))
    }

}

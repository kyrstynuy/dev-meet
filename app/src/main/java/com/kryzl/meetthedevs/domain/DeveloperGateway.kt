package com.kryzl.meetthedevs.domain

import com.kryzl.meetthedevs.base.exception.Failure
import com.kryzl.meetthedevs.base.functional.Either

interface DeveloperGateway {

    fun getDevelopers(): Either<Failure, List<Developer>>

    fun addDeveloper(developer: Developer): Either<Failure, Developer>

    fun updateDeveloper(developer: Developer): Either<Failure, Developer>

    fun deleteDeveloper(id: String): Either<Failure, Unit>

}

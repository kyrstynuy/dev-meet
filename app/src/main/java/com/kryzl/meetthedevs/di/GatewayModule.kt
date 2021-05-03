package com.kryzl.meetthedevs.di

import com.kryzl.meetthedevs.domain.DeveloperGateway
import com.kryzl.meetthedevs.domain.remote.DeveloperRemoteGateway
import dagger.Binds
import dagger.Module

@Module
abstract class GatewayModule {

    @Binds
    abstract fun provideDeveloperGateway(developerRemoteGateway: DeveloperRemoteGateway): DeveloperGateway

}

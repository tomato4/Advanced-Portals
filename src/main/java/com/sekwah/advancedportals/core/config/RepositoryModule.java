package com.sekwah.advancedportals.core.config;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Scopes;
import com.sekwah.advancedportals.core.AdvancedPortalsCore;
import com.sekwah.advancedportals.core.repository.*;

public class RepositoryModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(IPortalRepository.class).to(PortalRepository.class).in(Scopes.SINGLETON);
        bind(IDestinationRepository.class).to(DestinationRepository.class).in(Scopes.SINGLETON);
        bind(IPortalRepository.class).to(PortalRepository.class).in(Scopes.SINGLETON);
        bind(IConfigurations.class).to(ConfigurationsImpl.class).in(Scopes.SINGLETON);
        bind(ILangRepository.class).to(LangRepository.class).in(Scopes.SINGLETON);
        //bindListener(Matchers.Any(), new Log4JTypeListenr());
    }
}

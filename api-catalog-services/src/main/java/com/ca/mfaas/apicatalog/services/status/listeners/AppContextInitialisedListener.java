/*
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-v20.html
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Copyright Contributors to the Zowe Project.
 */
package com.ca.mfaas.apicatalog.services.status.listeners;

import com.ca.mfaas.apicatalog.services.cached.CacheRefreshService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class AppContextInitialisedListener {

    private final CacheRefreshService cacheRefreshService;

    @Autowired
    public AppContextInitialisedListener(CacheRefreshService cacheRefreshService) {
        this.cacheRefreshService = cacheRefreshService;
    }

    /**
     * Create a container for the API Catalog
     *
     * @param event spring event
     */
    @EventListener
    public void onApplicationEvent(ContextRefreshedEvent event) {
        cacheRefreshService.initialiseCacheWithDiscoverySnapshot();
    }
}

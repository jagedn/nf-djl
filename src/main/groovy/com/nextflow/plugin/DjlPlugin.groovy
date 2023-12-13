package com.nextflow.plugin

import ai.djl.Application
import ai.djl.engine.Engine
import ai.djl.engine.EngineProvider
import ai.djl.modality.Classifications
import ai.djl.modality.cv.Image
import ai.djl.modality.cv.ImageFactory
import ai.djl.modality.cv.output.DetectedObjects
import ai.djl.repository.zoo.Criteria
import ai.djl.repository.zoo.ZooProvider
import groovy.util.logging.Slf4j
import nextflow.plugin.BasePlugin
import org.pf4j.PluginWrapper


@Slf4j
class DjlPlugin extends BasePlugin{

    DjlPlugin(PluginWrapper wrapper) {
        super(wrapper)
        initPlugin()
    }

    private void initPlugin(){
        log.info "${this.class.name} plugin initialized"
        Thread.currentThread().setContextClassLoader(wrapper.pluginClassLoader)

        log.info "Searching ZooProviders"
        ServiceLoader<ZooProvider> providers = ServiceLoader.load(ZooProvider.class)
        for(ZooProvider zoo : providers){
            log.debug "ZooProvider $zoo.class founded"
        }

        log.info "Searching EngineProviders"
        ServiceLoader<EngineProvider> engineProviders = ServiceLoader.load(EngineProvider.class)
        for(EngineProvider engine : engineProviders){
            log.debug "EngineProvider $engine.class founded"
        }
    }
}

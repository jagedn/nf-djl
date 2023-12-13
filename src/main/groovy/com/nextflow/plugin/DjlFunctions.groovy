package com.nextflow.plugin

import ai.djl.Application
import ai.djl.engine.Engine
import ai.djl.modality.cv.Image
import ai.djl.modality.cv.ImageFactory
import ai.djl.modality.cv.output.DetectedObjects
import ai.djl.repository.zoo.Criteria
import groovy.transform.CompileStatic
import nextflow.plugin.extension.Function
import nextflow.plugin.extension.PluginExtensionPoint
import nextflow.Session

@CompileStatic
class DjlFunctions extends PluginExtensionPoint{

    private Session session

    @Override
    protected void init(Session session) {
        this.session = session
    }


    /*
     *  Nextflow Function example
     * Generate a random string
     *
     * Using @Function annotation we allow this function can be imported from the pipeline script
     */
    @Function
    List<DetectedObjects.DetectedObject> detectObjects(String url){
        Image img = ImageFactory.instance.fromUrl(url)

        def criteria = Criteria.builder()
                .optApplication(Application.CV.OBJECT_DETECTION)
                .optFilter("backbone", "resnet50")
                .optEngine(Engine.defaultEngineName)
                .setTypes(Image, DetectedObjects)
                .optArtifactId("ssd")
                .build()

        def model = criteria.loadModel()
        def predictor = model.newPredictor()
        def objects = predictor.predict(img)
        objects.items()
    }

}

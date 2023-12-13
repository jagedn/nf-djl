package nextflow.plugins

import org.gradle.api.DefaultTask
import org.gradle.api.file.RegularFileProperty
import org.gradle.api.tasks.OutputFile
import org.gradle.api.tasks.TaskAction


class GenerateIdxTask extends DefaultTask{


    @OutputFile
    final abstract RegularFileProperty outputFile =
            project.objects.fileProperty().convention(project.layout.buildDirectory.file(
                    "resources/main/META-INF/extensions.idx"))

    GenerateIdxTask() {
        setGroup('nextflow')
    }

    @TaskAction
    def runTask() {
        def matcher = new SourcesMatcher(project)
        def output = outputFile.get().asFile

        def extensionsClassName = matcher.pluginExtensions
        def traceClassName = matcher.traceObservers
        def extra = [
                'ai.djl.mxnet.engine.MxEngineProvider',
                'ai.djl.mxnet.zoo.MxZooProvider',
                'ai.djl.repository.zoo.DefaultZooProvider',
                'ai.djl.basicmodelzoo.BasicZooProvider',
        ]
        output.text = (extensionsClassName+traceClassName+extra).join('\n')
    }

}

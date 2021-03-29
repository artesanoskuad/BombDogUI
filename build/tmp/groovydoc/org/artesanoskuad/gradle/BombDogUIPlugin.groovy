package org.artesanoskuad.gradle

import org.gradle.api.Plugin
import org.gradle.api.Project

class BombDogUIPluginExtension {
    String ipMockServer
    String urlConfiguraciones
    String pathUrlMockoonJson
}

class BombDogUIPlugin implements Plugin<Project>{

    @Override
    void apply(Project project) {
        def extension = project.extensions.create('bombDogUI', BombDogUIPluginExtension)

        def checkAndroidVersions = project.tasks.register("checkBombDogUiProperties") {
            assert extension.ipMockServer == null || extension.ipMockServer == "" : "No esta definido el atributo ipMockServer. Use bombDogUI.ipMockServer=192.168.x.x"
            assert extension.urlConfiguraciones == null || extension.urlConfiguraciones == "" : "No esta definido el atributo urlConfiguraciones. Use bombDogUI.urlConfiguraciones=http://{ipMockServer}/config"
            assert extension.pathUrlMockoonJson == null || extension.pathUrlMockoonJson == "" : "No esta definido el atributo pathUrlMockoonJson. Use bombDogUI.pathUrlMockoonJson=config/mockserver.json"
        }

        def bombDogUi = project.tasks.register("pingBombDogUI") {
            println("ipMockServer: ${extension.ipMockServer}")
            println("urlConfiguraciones: ${extension.urlConfiguraciones}")
            println("pathUrlMockoonJson: ${extension.pathUrlMockoonJson}")
        }
    }
}

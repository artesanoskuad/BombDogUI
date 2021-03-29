package org.artesanoskuad.gradle

import org.gradle.api.Plugin
import org.gradle.api.Project

class BombDogUIPluginExtension {
    String ipMockServer
    String pathUrlMockoonJson
}

class BombDogUIPlugin implements Plugin<Project>{

    @Override
    void apply(Project project) {
        def extension = project.extensions.create('bombDogUI', BombDogUIPluginExtension)

        def checkAndroidVersions = project.tasks.register("checkBombDogUiProperties") {
            assert extension.ipMockServer == null || extension.ipMockServer == "" : "No esta definido el atributo ipMockServer. Use bombDogUI.ipMockServer=192.168.x.x"
            assert extension.pathUrlMockoonJson == null || extension.pathUrlMockoonJson == "" : "No esta definido el atributo pathUrlMockoonJson. Use bombDogUI.pathUrlMockoonJson=config/mockserver.json"
        }

        def bombDogUi = project.tasks.register("pingBombDogUI") {
            println("ipMockServer: ${extension.ipMockServer}")
            println("pathUrlMockoonJson: ${extension.pathUrlMockoonJson}")
        }

        def bombDogUINodeInstall = project.tasks.register("bombDogUINodeInstallInMac") {
            def proc = "brew install node".execute();
            def outputStream = new StringBuffer();
            proc.waitForProcessOutput(outputStream, System.err);
            println(outputStream.toString());
        }

        def bombDogUIMockoonInstall = project.tasks.register("bombDogUIInstallMockoonInMac") {
            def proc = "npm install -g @mockoon/cli".execute();
            def outputStream = new StringBuffer();
            proc.waitForProcessOutput(outputStream, System.err);
            println(outputStream.toString());
        }

        def bombDogUIObtainIP = project.tasks.register("bombDogUIObtainIP") {
            def proc = "ipconfig getifaddr en0".execute();
            def outputStream = new StringBuffer();
            proc.waitForProcessOutput(outputStream, System.err);
            println(outputStream.toString());
        }

        def bombDogUIStartMockServer = project.tasks.register("bombDogUIStartMockServer") {
            println("urlPath $extension.pathUrlMockoonJson ");
            def proc = "mockoon-cli start --data $extension.pathUrlMockoonJson -i 0".execute();
            def outputStream = new StringBuffer();
            proc.waitForProcessOutput(outputStream, System.err);
            println(outputStream.toString());
        }

    }
}

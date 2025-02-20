package cz.augi.gradle.dockerjava

import org.gradle.api.JavaVersion
import org.gradle.api.Project

class DockerJavaExtension implements DistDockerSettings, DockerPushSettings {
    final Project project
    final DockerExecutor dockerExecutor

    DockerJavaExtension(Project project, DockerExecutor dockerExecutor) {
        this.dockerExecutor = dockerExecutor
        this.project = project
        this.dockerBuildDirectory = new File(project.buildDir, 'dockerJava')
    }

    String image
    String[] alternativeImages = []
    JavaVersion getJavaVersion() { customJavaVersion ?: project.targetCompatibility }
    void setJavaVersion(JavaVersion version) { customJavaVersion = version }
    private JavaVersion customJavaVersion
    String baseImage
    Integer[] ports = []
    String[] volumes = []
    Map<String, String> labels = [:]
    String[] dockerfileLines = []
    String[] arguments = []
    File dockerBuildDirectory
    File[] filesToCopy = []
    File customDockerfile
    String[] buildArgs = []

    String username
    String password
    String getRegistry() {
        if (customRegistry) return customRegistry
        if (image.indexOf('/') < 0) return ''
        // if the part before first slash contains dot then it's private Docker Registry
        def potentialRegistry = image.substring(0, image.indexOf('/'))
        if (potentialRegistry.contains('.')) potentialRegistry else ''
    }
    void setRegistry(String registry) { customRegistry = registry }
    private String customRegistry
    Boolean removeImage = true
}

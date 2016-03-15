import com.cloudbees.groovy.cps.NonCPS
import groovy.util.ConfigSlurper

def cleanAndCompile(String switches = null) {
    run 'clean compileJava', switches
}

def test(String project = null, String switches = null) {
    if (project == null) {
        run ':test', switches
    } else {
        run ':' + project + ':test', switches
    }

}

def codeQuality(String switches = null) {
    run 'pmdMain', switches
}

def aggregateJaCoCoReports(String switches = null) {
    run 'jacocoRootTestReport', switches
}

def assembleApplication(String switches = null) {
    run 'assemble', switches
}

def publishApplication(String switches = null) {
    run 'publish', switches
}

def aggregateSerenityReports(String switches = null) {
    run ':acceptance-test:aggregate', switches
}

@NonCPS
Map conf(String content, String env = 'test') {
    def parsedConfig = new ConfigSlurper(env).parse(content)
    def map = [:]
    parsedConfig.flatten(map)
    return map
}

void run(String tasks, String switches = null) {
    String gradleCommand = "";
    gradleCommand += './gradlew '
    gradleCommand += tasks

    if(switches != null) {
        gradleCommand += ' '
        gradleCommand += switches
    }

    sh gradleCommand.toString()
}

return this;
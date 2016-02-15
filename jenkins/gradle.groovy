def cleanAndCompile(String switches = null) {
    run 'clean compileJava', switches
}

def test(String project = null, String switches = null) {
    if (project == null) {
        run ':test', switches
    } else {
        run ':' + project + ':test'
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
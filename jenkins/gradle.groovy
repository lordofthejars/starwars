def cleanAndCompile() {

}

def test(project) {

}

def codeQuality() {

}

def aggregateJaCoCoReports() {

}

def assembleApplication() {

}

def publishApplication() {

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
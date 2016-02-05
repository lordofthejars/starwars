stage 'compileAndUnit'

node {
        // get source code
        git branch: 'master', url: 'https://github.com/lordofthejars/starwars.git'

        // check that the whole project compiles
        gradle 'clean compileJava'

        // save source code so we don't need to get it every time and also avoids conflicts
        stash excludes: 'build/', includes: '**', name: 'source'

        parallel (
             "unit tests" : {
                gradle ':test'
             },
             "commit integration tests" : {
                gradle ':integration-commit-test:test'
             }
           )

        stash includes: 'build/jacoco/*.exec', name: 'unitCodeCoverage'
        stash includes: 'integration-commit-test/build/jacoco/*.exec', name: 'commitIntegrationCodeCoverage'
        step([$class: 'JUnitResultArchiver', testResults: '**/build/test-results/*.xml'])
}

stage 'codeQuality'

parallel 'pmd' : {
    node {
        unstash 'source'
        gradle 'pmdMain'
        step([$class: 'PmdPublisher', pattern: 'build/reports/pmd/*.xml'])
    }
}, 'jacoco': {
    node {
        unstash 'source'
        unstash 'unitCodeCoverage'
        unstash 'commitIntegrationCodeCoverage'
        gradle 'jacocoRootTestReport'
        publishHTML(target: [reportDir:'build/reports/jacoco/test/html', reportFiles: 'index.html', reportName: 'Code Coverage'])
    }
}

stage 'assemble-binaries'

node {
    unstash 'source'
    withEnv(["SOURCE_BUILD_NUMBER=${env.BUILD_NUMBER}"]) {
        gradle 'assemble'
    }
}


stage name: 'publish-binaries', concurrency: 1

node {
    unstash 'source'
    gradle 'publish'
}

input message: "Deploy Application to QA ?"

setCheckpoint('Before Deploying to QA')

stage name: 'Deploy to QA', concurrency: 1
node {
    echo "Star Wars Application Deployed to QA."
}

void setCheckpoint(String message) {
    try {
        checkpoint(message)
    } catch (NoSuchMethodError _) {
        echo 'Checkpoint feature available in CloudBees Jenkins Enterprise.'
    }
}

void gradle(String tasks, String switches = null) {
    String gradleCommand = "";
    gradleCommand += './gradlew '
    gradleCommand += tasks

    if(switches != null) {
        gradleCommand += ' '
        gradleCommand += switches
    }

    sh gradleCommand.toString()
}
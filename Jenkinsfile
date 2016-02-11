stage 'compileAndUnit'

node {
        // get source code
        git branch: 'master', url: 'https://github.com/lordofthejars/starwars.git'

        // check that the whole project compiles
        gradle 'clean compileJava'

        // save source code so we don't need to get it every time and also avoids conflicts
        stash excludes: 'build/', includes: '**', name: 'source'

        // execute required tests for commit stage in parallel
        parallel (
             "unit tests" : {
                gradle ':test'
             },
             "commit integration tests" : {
                gradle ':integration-commit-test:test'
             }
           )

        // save coverage reports for being processed during code quality phase.
        stash includes: 'build/jacoco/*.exec', name: 'unitCodeCoverage'
        stash includes: 'integration-commit-test/build/jacoco/*.exec', name: 'commitIntegrationCodeCoverage'

        // publish JUnit results to Jenkins
        step([$class: 'JUnitResultArchiver', testResults: '**/build/test-results/*.xml'])
}

stage 'codeQuality'

node {

    parallel (
        'pmd' : {
            // static code analysis
            unstash 'source'

            gradle 'pmdMain'
            step([$class: 'PmdPublisher', pattern: 'build/reports/pmd/*.xml'])
        },
        'jacoco': {
            // jacoco report rendering
            unstash 'source'
            unstash 'unitCodeCoverage'
            unstash 'commitIntegrationCodeCoverage'

            gradle 'jacocoRootTestReport'
            publishHTML(target: [reportDir:'build/reports/jacoco/jacocoRootTestReport/html', reportFiles: 'index.html', reportName: 'Code Coverage'])
        }
      )
}

stage 'assemble-binaries'

node {
    unstash 'source'
    withEnv(["SOURCE_BUILD_NUMBER=${env.BUILD_NUMBER}"]) {
        gradle 'assemble'
    }

    // docker

    // container tests
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
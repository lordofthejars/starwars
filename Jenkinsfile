stage 'compileAndUnit'

node {
        git branch: 'master', url: 'https://github.com/lordofthejars/starwars.git'

        gradle 'clean test'
        stash excludes: 'build/', includes: '**', name: 'source'
        stash includes: 'build/jacoco/*.exec', name: 'unitCodeCoverage'
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
        gradle 'jacocoTestReport'
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

stage name: 'Deploy to QA', concurrency: 1
node {
    echo "Star Wars Application Deployed to QA."
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
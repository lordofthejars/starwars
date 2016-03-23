def withRequiredPacts(body) {

    dir('build') {
        git 'file:///Users/alexsoto/git/starwars_pacts'
    }

    def workspace = pwd()
    def pactDirectories = findFiles()
    echo "*** ${pactDirectories.size()}"
    for(int i = 0; i < pactDirectories.length; i++) {
        echo ">> ${pactDirectories[i].directory} + ${pactDirectories[i].path}"
        if (pactDirectories[i].directory && pactDirectories[i].path.endsWith('_planets_provider')) {
            def pactDirectory = "${workspace}/${pactDirectories[i].path}"
            withEnv(["pacts=${pactDirectory}"]) {
                println "Pact found at ${pactDirectories[i].path}"
                body.call
            }
        }
    }

}

this
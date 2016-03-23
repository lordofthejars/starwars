def withRequiredPacts(body) {

    dir('build') {
        git 'file:///Users/alexsoto/git/starwars_pacts'
    }

    def workspace = pwd()
    def pactDirectories = findFiles(glob: 'build/starwars_pacts/.+_planets_provider')

    for(int i = 0; i < pactDirectories.length; i++) {
        def pactDirectory = "${workspace}/${pactDirectories[i].path}"
        withEnv(["pacts=${pactDirectory}"]) {
            println "Pact found at ${pactDirectories[i].path}"
            body.call
        }
    }

}

this
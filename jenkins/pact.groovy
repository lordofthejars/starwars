def withRequiredPacts(body) {

    dir('build') {
        git 'file:///Users/alexsoto/git/starwars_pacts'
    }

    def workspace = pwd()
    echo "** ${workspace}"
    def pactDirectories = findFiles(glob: 'build/.+_planets_provider')
    echo "** ${pactDirectories.size()}"
    for(int i = 0; i < pactDirectories.length; i++) {
        def pactDirectory = "${workspace}/${pactDirectories[i].path}"
        withEnv(["pacts=${pactDirectory}"]) {
            println "Pact found at ${pactDirectories[i].path}"
            body.call
        }
    }

}

this
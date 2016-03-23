def withRequiredPacts(body) {

    dir('build') {
        git 'file:///Users/alexsoto/git/starwars_pacts'
    }

    def pacts = new File('build/starwars_pacts')

    pacts.eachFileMatch(groovy.io.FileType.DIRECTORIES, ~/.+_planets_provider/) { pactDirectory ->
        withEnv(["pacts=${pactDirectory.absolutePath}"]) {
            println "Pact found at ${pactDirectory.path}"
            body.call
        }
    }
}

this
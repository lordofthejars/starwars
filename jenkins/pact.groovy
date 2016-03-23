def requiredPacts() {

    def pactFolders;
    dir('build') {
        git 'file:///Users/alexsoto/git/starwars_pacts'
        pactFolders = findFiles()
    }

    def workspace = pwd()
    def pacts = []
    for(int i = 0; i < pactFolders.length; i++) {
        if (pactFolders[i].directory && pactFolders[i].path.endsWith('_planets_provider/')) {
            def pactDirectory = "${workspace}/build/${pactFolders[i].path}"
            pacts << pactDirectory
        }
    }
    return pacts
}

this
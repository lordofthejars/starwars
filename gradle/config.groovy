binaryRepository {
    url = "file:."
    name = 'artifacts'
}
environments {
    test {
        docker {
            registry = 'http://localhost:5000'
            registryDomain = 'localhost:5000'
            username = 'test'
            imageName = 'starwars'
        }
    }
}
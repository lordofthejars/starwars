binaryRepository {
    url = "file:."
    name = 'artifacts'
}

common {
    docker {
        organization = 'starwars'
        image = 'planets'
    }
}

environments {
    test {
        docker {
            registry = 'http://localhost:5000'
            registryDomain = 'localhost:5000'
            username = 'test'
        }
    }

    pre {
        docker {
            registry = 'http://localhost:5000'
            registryDomain = 'localhost:5000'
            username = 'test'
        }
    }
}
binaryRepository {
    url = "file:."
    name = 'artifacts'
}
environments {
    test {
        server {
            hostname = 'localhost'
            port = 8080
        }
    }
}
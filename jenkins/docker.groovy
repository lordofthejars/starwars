def purge(String imageName, int numberOfContainers = 10) {
    sh 'docker images -a | grep \'' + imageName + '\' | awk \'{print $3}\' | tail -n +' + numberOfContainers + ' > imagesToRemove.txt'
    def images = readFile('imagesToRemove.txt')
    // join multiple lines to single line
    images = images.replaceAll("[\r\n]+"," ")
    sh 'docker rmi -f ' + images
}

return this;
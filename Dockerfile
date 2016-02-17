FROM tomee:8-jre-7.0.0-M1-webprofile

ADD build/libs/starwars-*.war /usr/local/tomee/webapps/starwars.war
#!/bin/bash

usage() { echo "Usage: $0 [-v <version number>]" 1>&2; exit 1; }
POM=$(cat pom.xml)
APPLICATION_NAME=$(sed -n -e 's/.*<artifactId>\(.*\)<\/artifactId>.*/\1/p' <<< cat pom.xml | head -1)
PROFILE=dev
while getopts ":v:P:n" o; do
    case "${o}" in
	    v)
            	VERSION=${OPTARG}
            	;;
	    P)
            	PROFILE=${OPTARG}
            	;;
	    n)
                APPLICATION_NAME=${OPTARG}
                ;;

    esac
done

echo "VERSION:-->${VERSION}"
echo "PROFILE:-->${PROFILE}"
echo "APPLICATION_NAME:--> ${APPLICATION_NAME}"

cp ./src/main/docker/Dockerfile ./target/
cp ./src/main/docker/entrypoint.sh ./target/
./mvnw package -P${PROFILE} -DskipTests
docker container stop ${APPLICATION_NAME}
docker container rm ${APPLICATION_NAME}
docker build -t ${APPLICATION_NAME}:${VERSION} ./target/
docker run -d --name  ${APPLICATION_NAME} --network octagon-net -it  ${APPLICATION_NAME}:${VERSION}

echo "fin"

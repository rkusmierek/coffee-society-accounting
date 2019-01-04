all: build

build: build-jar build-docker

build-jar:
	mvn package

build-docker:
	docker build -t csms/coffee-society-accounting:latest .

clean:
	mvn clean

analyze:
	mvn clean org.jacoco:jacoco-maven-plugin:prepare-agent package org.jacoco:jacoco-maven-plugin:report sonar:sonar

run-jar:
	java -jar target/coffee-society-accounting*.jar

run-docker:
	docker run -d --name coffee-society-accounting csms/coffee-society-accounting

tag:
	docker tag csms/coffee-society-accounting csms/coffee-society-accounting:${TAG}

push-latest:
	docker push csms/coffee-society-accounting:latest

push-tag:
	docker push csms/coffee-society-accounting:${TAG}

docker-login:
	@docker login -u "${DOCKER_ID}" -p "${DOCKER_PASS}"
	
docker-run: run-docker

docker-remove:
	docker rm -f coffee-society-accounting

docker-logs:
	docker logs coffee-society-accounting

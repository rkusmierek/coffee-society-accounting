# CoffeeSociety Accounting Service
[![Build Status](https://travis-ci.org/asc-lab/coffee-society-accounting.svg?branch=master)](https://travis-ci.org/asc-lab/coffee-society-accounting)
[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=pl.altkom.coffee.accounting%3Aaccounting-parent&metric=alert_status)](https://sonarcloud.io/dashboard?id=pl.altkom.coffee.accounting%3Aaccounting-parent)

## Building

To execute complete building process (jar and docker image), execute command:

```
make
```

If you want to build only a jar-file (skip building docker image), type:

```
make build-jar
```

To build docker image (assuming you have already built a jar-file), type:

```
make build-docker
```

## Running

To run this service, execute command:

```
make run-jar
```

If you want to run this service in a container:

```
make run-docker
```

pipeline {
    agent any

    tools {
        maven 'Maven 3'
        jdk 'jdk8'
    }

    stages {

        stage('Build') {
            steps {
                sh 'make build'
            }
        }

        stage('Analyze') {
            steps {
                sh 'make analyze'
            }
        }

    }
}

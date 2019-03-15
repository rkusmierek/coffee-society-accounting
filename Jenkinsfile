pipeline {
    agent any

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

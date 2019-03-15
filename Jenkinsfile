pipeline {
    agent any

    environment {
        DOCKER_ID   = credentials('docker-id')
        DOCKER_PASS = credentials('docker-pass')
        GIT_COMMIT_SHORT = sh(
            script: "printf \$(git rev-parse --short ${GIT_COMMIT})",
            returnStdout: true
        )
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

        stage('Bake') {
            steps {
                sh 'make bake'
            }
        }

        stage('Docker login') {
            steps {
                sh 'make docker-login'
            }
        }

        stage('Publish') {
            steps {
                sh "make push-latest"
                sh "make TAG=${env.BUILD_ID} tag"
                sh "make TAG=${env.BUILD_ID} push-tag"
                sh "make TAG=${GIT_COMMIT_SHORT} tag"
                sh "make TAG=${GIT_COMMIT_SHORT} push-tag"
            }
        }

    }
}

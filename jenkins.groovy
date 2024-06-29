pipeline {
    agent any

    triggers {
        githubPush()
    }

    environment {
        REPO_URL = 'https://github.com/CPrasa/DevOps-Project'
        BRANCH = 'main'
        DOCKER_REGISTRY = 'crprasad'
        APP_NAME = 'RMS'
        PORT = '3000'
    }

    stages {
        stage('Clone Repository') { 
            steps {
                git branch: "${BRANCH}", url: "${REPO_URL}"
            }
        }

        stage('Build Docker Images') {
            steps {
                script {
                    bat 'docker-compose build'
                }
            }
        }

        stage('Push Docker Images') {
            steps {
                script {
                    bat 'docker-compose push'
                }
            }
        }
        stage('Deploy Application') {
            steps {
                script {
                    bat 'docker-compose down'
                    bat 'docker-compose up -d'
                }
            }
        }
    }
}
pipeline {
    agent any

    environment {
        MAVEN_HOME = tool 'Maven'  // Ensure Maven is configured in Jenkins
    }

    stages {
        stage('Checkout Code') {
            steps {
                script {
                    git branch: 'main', 
                  //      credentialsId: '',  // Remove SSH credential ID
                        url: 'https://sureshd3@bitbucket.org/infoplus-mdm-dev/midas.git'
                }
            }
        }

        stage('Build') {
            steps {
                script {
                    sh "${MAVEN_HOME}/bin/mvn clean compile"
                }
            }
        }

        stage('Run Tests') {
            steps {
                script {
                    sh "${MAVEN_HOME}/bin/mvn test"
                }
            }
        }

        stage('Publish TestNG Report') {
            post {
                always {
                    publishHTML([target: [
                        allowMissing: false,
                        alwaysLinkToLastBuild: true,
                        keepAll: true,
                        reportDir: 'target/surefire-reports',
                        reportFiles: 'index.html',
                        reportName: 'TestNG Report'
                    ]])
                }
            }
        }
    }

    post {
        always {
            junit '**/target/surefire-reports/*.xml'
        }
    }
}

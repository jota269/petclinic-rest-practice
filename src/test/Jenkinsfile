pipeline {
    agent any

    tools {
        jdk   'jdk21'
        maven 'maven3'
    }

    environment {
        SONAR_TOKEN = credentials('sonar-token')
    }

    stages {

        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Build & Tests') {
            steps {
                // mvnw ja ve al projecte
                bat 'mvnw.cmd -q clean verify'
            }
            post {
                always {
                    // Publicar tests JUnit
                    junit 'target/surefire-reports/*.xml'
                }
            }
        }

        stage('Coverage (JaCoCo)') {
            steps {
                bat 'mvnw.cmd -q jacoco:report'
                // Desa l’informe com a artefacte
                archiveArtifacts artifacts: 'target/site/jacoco/**', fingerprint: true
            }
        }

        stage('SonarQube Scan') {
            steps {
                withSonarQubeEnv('local-sonarqube') {
                    // Anàlisi amb SonarQube usant el token
                    bat 'mvnw.cmd sonar:sonar -Dsonar.token=%SONAR_TOKEN%'
                }
            }
        }

        stage('Quality Gate') {
            steps {
                // Falla el pipeline si el Quality Gate NO passa
                timeout(time: 3, unit: 'MINUTES') {
                    waitForQualityGate abortPipeline: true
                }
            }
        }
    }
}

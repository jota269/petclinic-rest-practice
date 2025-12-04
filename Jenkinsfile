pipeline {
    agent any

    stages {

        stage('Checkout') {
            steps {
                // Ja agafa el mateix repo i branca que has configurat al job
                checkout scm
            }
        }

        stage('Build & Tests') {
            steps {
                echo 'Executant mvn clean verify...'
                sh './mvnw -q clean verify'
            }
            post {
                always {
                    echo 'Publicant tests JUnit...'
                    junit 'target/surefire-reports/*.xml'
                }
            }
        }

        stage('Coverage (JaCoCo)') {
            steps {
                echo 'Generant informe de cobertura...'
                sh './mvnw -q jacoco:report'
            }
            post {
                always {
                    echo 'Arxivem informes de JaCoCo com a artefactes...'
                    archiveArtifacts artifacts: 'target/site/jacoco/**', fingerprint: true
                }
            }
        }

        stage('SonarQube Scan') {
            steps {
                echo 'Llençant anàlisi SonarQube...'
                // ⚠️ Canvia el nom 'SonarQubeServer' pel NOM exacte del teu server a:
                // Manage Jenkins > Configure System > SonarQube servers
                withSonarQubeEnv('SonarQubeServer') {
                    sh './mvnw -q sonar:sonar'
                }
            }
        }

        stage('Quality Gate') {
            steps {
                echo 'Esperant resultat del Quality Gate...'
                timeout(time: 5, unit: 'MINUTES') {
                    waitForQualityGate abortPipeline: true
                }
            }
        }
    }
}

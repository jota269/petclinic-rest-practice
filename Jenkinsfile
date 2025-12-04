aaaa
pipeline {
    agent any

    stages {

        stage('Checkout') {
            steps {
                echo 'Fent checkout del codi...'
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
                    echo 'Publicant informes JUnit...'
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
                    echo 'Arxivem target/site/jacoco com a artefacte...'
                    archiveArtifacts artifacts: 'target/site/jacoco/**', fingerprint: true
                }
            }
        }

        stage('SonarQube Scan') {
            steps {
                echo 'Llençant anàlisi SonarQube...'
                // ⚠️ CANVIA aquest nom SI el teu servidor Sonar a Jenkins
                // té un altre nom a "Manage Jenkins > Configure System":
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

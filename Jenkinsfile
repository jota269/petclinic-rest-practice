pipeline {
    agent any

    environment {
        // Canvia l'ID si el teu credencial de Jenkins té un altre nom
        SONAR_TOKEN = credentials('sonar-token')
        SONAR_HOST_URL = 'http://host.docker.internal:9000'
    }

    stages {

        stage('Checkout') {
            steps {
                echo "Fent checkout del codi..."
                checkout scm
            }
        }

        stage('Build & Tests (mvn clean verify)') {
            steps {
                echo "Executant mvn clean verify"
                sh 'chmod +x mvnw'
                sh './mvnw -q clean verify'
            }
            post {
                always {
                    echo "Publicant tests JUnit"
                    junit 'target/surefire-reports/*.xml'
                }
            }
        }

        stage('Coverage (JaCoCo)') {
            steps {
                echo "Generant informe de cobertura JaCoCo"
                sh './mvnw -q jacoco:report'
            }
            post {
                always {
                    echo "Arxivant informes de cobertura"
                    archiveArtifacts artifacts: 'target/site/jacoco/**', fingerprint: true
                }
            }
        }

        stage('SonarQube Scan') {
            steps {
                echo "Llançant anàlisi SonarQube"
                sh """
                    ./mvnw sonar:sonar \
                      -Dsonar.projectKey=org.springframework.samples:spring-petclinic-rest \
                      -Dsonar.host.url=${SONAR_HOST_URL} \
                      -Dsonar.login=${SONAR_TOKEN}
                """
            }
        }
    }

    post {
        always {
            echo "Pipeline BACKEND acabat amb estat: ${currentBuild.currentResult}"
        }
    }
}

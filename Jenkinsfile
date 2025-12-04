node {
    stage('Checkout') {
        echo "Fent checkout del codi..."
        checkout scm
    }

    stage('Build & Tests (mvn clean verify)') {
        echo "Executant mvn clean verify..."
        sh 'chmod +x mvnw'
        sh './mvnw -q clean verify'

        echo "Publicant JUnit..."
        junit 'target/surefire-reports/*.xml'
    }

    stage('Coverage (JaCoCo)') {
        echo "Generant informe de cobertura JaCoCo..."
        sh './mvnw -q jacoco:report'

        echo "Arxivant informes de cobertura..."
        archiveArtifacts artifacts: 'target/site/jacoco/**', fingerprint: true
    }

    stage('SonarQube Scan') {
        echo "Llançant anàlisi SonarQube..."
        // 🔐 Canvia l'ID 'sonar-token' si el teu credencial té un altre nom
        withCredentials([string(credentialsId: 'sonar-token', variable: 'SONAR_TOKEN')]) {
            sh """
                ./mvnw sonar:sonar \
                  -Dsonar.projectKey=org.springframework.samples:spring-petclinic-rest \
                  -Dsonar.host.url=http://host.docker.internal:9000 \
                  -Dsonar.login=$SONAR_TOKEN
            """
        }
    }
}

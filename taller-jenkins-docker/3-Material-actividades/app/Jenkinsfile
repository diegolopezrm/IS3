// Actividad 3 — Compilar y probar dentro de un contenedor Maven
// Configurar en Jenkins: Pipeline > Definition: "Pipeline script from SCM"
pipeline {
    agent {
        docker {
            image 'maven:3.9-eclipse-temurin-21'
            // Usa la red de jenkins-docker: así el contenedor resuelve nombres (DNS)
            // aunque la red bloquee los DNS públicos.
            args '--network host'
            reuseNode true
        }
    }

    environment {
        // El contenedor corre con el usuario de Jenkins (uid 1000), sin HOME propio:
        // las dependencias de Maven se guardan dentro del workspace.
        HOME = "${env.WORKSPACE}"
        MVN  = 'mvn -B -Dmaven.repo.local=.m2/repository'
    }

    stages {
        stage('Compilar') {
            steps {
                sh '$MVN compile'
            }
        }
        stage('Pruebas') {
            steps {
                sh '$MVN test'
            }
        }
        stage('Empaquetar') {
            steps {
                sh '$MVN -DskipTests package'
                archiveArtifacts artifacts: 'target/taller-app.jar', fingerprint: true
            }
        }
    }

    post {
        always {
            junit allowEmptyResults: true, testResults: 'target/surefire-reports/*.xml'
        }
    }
}

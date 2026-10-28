// Actividad 2 — Primer pipeline declarativo
// Pegar en: Nuevo Item > Pipeline > Definition: "Pipeline script"
pipeline {
    agent any

    environment {
        CURSO = 'Taller Jenkins + Docker'
    }

    stages {
        stage('Saludo') {
            steps {
                echo "Hola desde ${env.CURSO} — build #${env.BUILD_NUMBER}"
            }
        }
        stage('Info del entorno') {
            steps {
                sh 'whoami && hostname && date'
                sh 'docker version'   // el cliente Docker habla con el contenedor dind
            }
        }
        stage('Etapas en paralelo') {
            parallel {
                stage('A') { steps { echo 'Tarea A' } }
                stage('B') { steps { echo 'Tarea B' } }
            }
        }
    }

    post {
        success { echo 'Pipeline OK ✔' }
        failure { echo 'Algo falló ✘' }
    }
}

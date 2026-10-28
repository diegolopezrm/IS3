// Actividad 4 (reto) — Probar, construir la imagen y hacer un smoke test
// Solución de referencia: reemplaza el Jenkinsfile del repo por este.
pipeline {
    agent none

    environment {
        IMAGEN     = "taller-app:${env.BUILD_NUMBER}"
        CONTENEDOR = "taller-app-${env.BUILD_NUMBER}"
    }

    stages {
        stage('Pruebas') {
            agent {
                docker {
                    image 'maven:3.9-eclipse-temurin-21'
                    args '--network host'   // DNS: usa la red de jenkins-docker
                }
            }
            environment { HOME = "${env.WORKSPACE}" }
            steps {
                sh 'mvn -B -Dmaven.repo.local=.m2/repository test'
            }
            post {
                always { junit 'target/surefire-reports/*.xml' }
            }
        }

        stage('Construir imagen') {
            agent any   // el nodo de Jenkins tiene el cliente Docker
            steps {
                // Dockerfile multi-etapa: compila con Maven y deja solo el JRE + el .jar
                sh 'docker build --network host -t $IMAGEN .'   // Maven descarga dependencias dentro del build
            }
        }

        stage('Smoke test') {
            agent any
            steps {
                // El contenedor corre en el demonio dind; Jenkins lo alcanza por el alias "docker"
                sh '''
                    docker run -d --name $CONTENEDOR -p 18000:8000 $IMAGEN
                    for i in $(seq 1 15); do
                        curl -fs http://docker:18000/health && exit 0
                        sleep 2
                    done
                    echo "La app no respondió en /health"
                    docker logs $CONTENEDOR
                    exit 1
                '''
            }
            post {
                always { sh 'docker rm -f $CONTENEDOR || true' }
            }
        }
    }

    post {
        success { echo "Imagen lista: ${env.IMAGEN}" }
    }
}

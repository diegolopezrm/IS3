// Actividad 5 — Probar, construir, verificar y DESPLEGAR la app
// Solución de referencia: reemplaza el Jenkinsfile del repo por este.
// Cada push + Build Now despliega la versión nueva en http://localhost:8000
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
            agent any
            steps {
                sh 'docker build --network host -t $IMAGEN .'   // Maven descarga dependencias dentro del build
            }
        }

        stage('Smoke test') {
            agent any
            steps {
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

        // Solo se llega aquí si las pruebas y el smoke test pasaron.
        stage('Desplegar') {
            agent any
            steps {
                sh '''
                    # Reemplazar la versión en ejecución por la nueva
                    docker rm -f taller-app || true
                    docker run -d --name taller-app --restart unless-stopped -p 8000:8000 $IMAGEN

                    # Verificar que la versión desplegada responde
                    for i in $(seq 1 15); do
                        if curl -fs http://docker:8000/version; then
                            echo ""
                            echo "Desplegada $IMAGEN en http://localhost:8000"
                            exit 0
                        fi
                        sleep 2
                    done
                    echo "El despliegue no respondió en /version"
                    docker logs taller-app
                    exit 1
                '''
            }
        }
    }

    post {
        success { echo "Despliegue OK: ${env.IMAGEN}" }
        failure { echo 'El pipeline falló: la versión anterior sigue desplegada (si la había).' }
    }
}

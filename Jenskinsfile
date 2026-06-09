pipeline {
    agent any

    tools {
        maven 'Maven'
        jdk 'Java'
    }

    stages {

        stage('Checkout') {
            steps {
                echo '📥 Getting code...'
                checkout scm
            }
        }

        stage('Build') {
            steps {
                echo '🔨 Building...'
                sh 'mvn clean compile'
            }
        }

        stage('Test') {
            steps {
                echo '🧪 Testing...'
                sh 'mvn test'
            }
        }

        stage('Package') {
            steps {
                echo '📦 Creating JAR...'
                sh 'mvn package -DskipTests'
            }
        }
    }

    post {
        success {
            echo '✅ Build exitoso'
        }
        failure {
            echo '❌ Falló el pipeline'
        }
        always {
            echo '📊 Pipeline finalizado'
        }
    }
}
pipeline {
    agent any
    environment {
        SALT_ROUNDS = '7'
        JWT_SECRET = credentials('secret-secret')

        PATH = "C:\\Program Files\\Docker\\Docker\\resources\\bin;${env.PATH}"

        // Define Docker Hub credentials ID
        DOCKERHUB_CREDENTIALS_ID = 'docker_hub'
        // Define Docker Hub repository name
        DOCKERHUB_REPO = 'samuelms123/class-assignment2'
        // Define Docker image tag
        DOCKER_IMAGE_TAG = 'latest'
        }
    }
    stages {
        stage('Checkout') {
            steps {
                git branch:'main', url:'https://github.com/samuelms123/OTP-2-Assignment2.git'
            }
        }
        stage('Create .env file') {
            steps {
                powershell '''
                    Write-Output "Creating .env file..."
                    "SALT_ROUNDS=$env:SALT_ROUNDS" | Out-File -FilePath .env -Encoding ASCII
                    "JWT_SECRET=$env:JWT_SECRET" | Out-File -FilePath .env -Encoding ASCII -Append
                    Get-Content .env
                '''
            }
        }
        stage('Verify .env') {
            steps {
                bat '''
                    echo Checking if .env file exists...
                    if exist .env (
                        echo .env file found!
                        type .env
                    ) else (
                        echo .env file NOT found!
                    )
                '''
            }
        }

        stage('Build and Test') {
            steps {
                bat 'mvn clean install'
            }
        }
        stage('Code Coverage') {
            steps {
                recordCoverage(tools: [[parser: 'JACOCO']],
                        id: 'jacoco', name: 'JaCoCo Coverage',
                        sourceCodeRetention: 'EVERY_BUILD',
                        qualityGates: [
                                [threshold: 60.0, metric: 'LINE', baseline: 'PROJECT', unstable: true],
                                [threshold: 60.0, metric: 'BRANCH', baseline: 'PROJECT', unstable: true]])
            }
        }

        stage('Build Docker Image') {
            steps {
                bat 'docker build -t %DOCKERHUB_REPO%:%DOCKER_IMAGE_TAG% .'
            }
        }

        stage('Push Docker Image to Docker Hub') {
            steps {
                withCredentials([usernamePassword(credentialsId: "${DOCKERHUB_CREDENTIALS_ID}", usernameVariable: 'DOCKER_USER', passwordVariable: 'DOCKER_PASS')]) {
                    bat '''
                        docker login -u %DOCKER_USER% -p %DOCKER_PASS%
                        docker push %DOCKERHUB_REPO%:%DOCKER_IMAGE_TAG%
                    '''
                }
            }
        }

        stage('Cleanup') {
            steps {
                powershell 'Remove-Item .env -Force -ErrorAction SilentlyContinue'
            }
        }
    }

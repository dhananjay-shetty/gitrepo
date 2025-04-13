pipeline {
    agent any
    stages {
        stage('Clone Repository') {
            steps {
                git url: 'https://github.com/dhananjay-shetty/sql_scripts.git'
            }
        }
        stage('Run Liquibase Update') {
            steps {
                // Run Liquibase commands
                sh 'liquibase --changeLogFile=changelog.xml update'
            }
        }
    }
}
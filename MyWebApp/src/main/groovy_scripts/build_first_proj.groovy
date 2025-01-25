node {
    // Define variables
    def mvnHome = tool name: 'maven', type: 'maven'
    def warFile = 'D:\\git_repo/your-app.war'

    try {
        stage('Checkout') {
            // Checkout code from the repository
            git 'https://github.com/dhananjay-shetty/gitrepo.git'
        }

        stage('Build') {
            // Build the project and generate a WAR file
            withEnv(["PATH+MAVEN=${mvnHome}/bin"]) {
                bat 'mvn clean package'
            }
        }

        stage('Test') {
            // Run tests
            withEnv(["PATH+MAVEN=${mvnHome}/bin"]) {
                bat 'mvn test'
            }
        }

        stage('Archive') {
            // Archive the generated WAR file
            archiveArtifacts artifacts: warFile, fingerprint: true
        }

        stage('Deploy') {
            // Deploy the WAR file to your server (replace with your deployment steps)
            bat "scp ${warFile} dhananjay@192.168.0.104:D:\git_repo"
        }

    } catch (Exception e) {
        currentBuild.result = 'FAILURE'
        throw e
    } finally {
        stage('Cleanup') {
            // Perform cleanup steps
            echo 'Cleaning up workspace'
            deleteDir()
        }
    }
}

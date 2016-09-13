def withEnvironment(url, cred, body) {
  withEnv(["RANCHER_URL=${url}"]) {
    withCredentials([[$class: 'UsernamePasswordMultiBinding',
        credentialsId: cred,
        usernameVariable: 'RANCHER_ACCESS_KEY',
        passwordVariable: 'RANCHER_SECRET_KEY']]) {

      sh 'echo "debug: RANCHER_URL=$RANCHER_URL"'
      sh 'echo "debug: RANCHER_ACCESS_KEY=$RANCHER_ACCESS_KEY"'
      sh 'echo "debug: RANCHER_SECRET_KEY=$RANCHER_SECRET_KEY"'

      body.call
    }
  }
}

return this;

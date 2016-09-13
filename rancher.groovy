def withEnvironment(url, cred, body) {
  withEnv(["RANCHER_URL=${url}"]) {
    withCredentials([[$class: 'UsernamePasswordMultiBinding',
        credentialsId: cred,
        usernameVariable: 'RANCHER_ACCESS_KEY',
        passwordVariable: 'RANCHER_SECRET_KEY']]) {
      body.call()
    }
  }
}

def composeUp() {
  sh "rancher-compose up"
}

def composeRm() {
  sh "rancher-compose rm"
}

return this;

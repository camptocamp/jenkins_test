def withEnvironment(url, cred, body) {
  withEnv(["RANCHER_URL=${url}"]) {
    withCredentials([[$class: 'UsernamePasswordMultiBinding',
        credentialsId: cred,
        usernameVariable: 'RANCHER_ACCESS_KEY',
        passwordVariable: 'RANCHER_SECRET_KEY']]) {
      try {
        body.call
      }
    }
  }
}

this

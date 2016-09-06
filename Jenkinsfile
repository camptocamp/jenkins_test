parallel 'simple build': {
  docker.image('golang').inside {
    checkout scm
    sh 'go build .'
  }
}, 'run tests': {
  docker.image('golang').inside {
    checkout scm
    sh 'go test -v ./...'
  }
}

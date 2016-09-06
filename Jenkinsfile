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

docker.image('jpetazzo/dind').inside {
  checkout scm
  sh 'go build -a -installsuffix cgo -o test test.go'
  sh 'docker build .'
}

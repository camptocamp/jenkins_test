docker.image('golang').inside {
  checkout scm
  sh 'go build .'
  sh 'go test -v ./...'
}

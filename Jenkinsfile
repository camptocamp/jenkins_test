stage name: 'Test', concurrency: 1
docker.image('golang').inside {
  checkout scm
  sh 'go test -v ./...'
}

stage name: 'Static build', concurrency: 1
docker.image('golang').inside {
  checkout scm
  sh 'go build -a -installsuffix cgo -o jenkins-test test.go'
  stash includes: 'jenkins-test', name: 'jenkins-test-static'
}

stage name: 'Docker image build', concurrency: 1
docker.image('jpetazzo/dind').inside {
  unstash 'jenkins-test-static'
  sh 'docker build .'
}

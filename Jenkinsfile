node {

  def golang = docker.image('golang:latest')
  golang.pull()  // make sure golang image is up-to-date
  
  golang.inside {
    checkout scm
  
    stage 'Test'
    sh 'go test -v ./...'
  
    stage 'Static build'
    sh 'go build -a -installsuffix cgo -o jenkins-test main.go'
    stash includes: 'jenkins-test', name: 'jenkins-test-static'
  }

  stage 'Docker image build'
  unstash 'jenkins-test-static'
  docker.build 'raphink/jenkins-test:latest'

  stage 'Test docker image'
  sh 'docker run raphink/jenkins-test:latest'

  stage 'Push to dockerhub'
  docker.push('raphink/jenkins-test:latest')
}

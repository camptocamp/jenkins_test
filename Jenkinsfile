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
  def cont = docker.build "camptocamp/jenkins-test:${env.BUILD_TAGS}"

  stage 'Test docker image'
  sh "docker run camptocamp/jenkins-test:${env.BUILD_TAGS}"

  stage 'Push to dockerhub'
  docker.withRegistry('https://docker.io/', 'dockerhubc2c') {
    cont.push()
    cont.push('latest')
  }
}

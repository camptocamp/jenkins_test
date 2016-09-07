node('docker') {

  def golang = docker.image('golang:latest')
  stage 'Update golang image'
  golang.pull()  // make sure golang image is up-to-date
  
  golang.inside {
    stage 'Checkout'
    checkout scm
  
    stage 'Test and static build'
    parallel 'Test': {
      sh 'go test -v ./...'
    }, 'Static build': {
      sh 'go build -a -installsuffix cgo -o jenkins-test main.go'
      stash includes: 'jenkins-test', name: 'jenkins-test-static'
    }
  }

  stage 'Docker image build'
  unstash 'jenkins-test-static'
  def cont = docker.build "camptocamp/jenkins-test"

  stage 'Test docker image'
  sh "docker run camptocamp/jenkins-test"

  /* Not working, see https://issues.jenkins-ci.org/browse/JENKINS-38018
  
  stage 'Push to dockerhub'
  withDockerRegistry(registry: [credentialsId: 'dockerhub']) {
    cont.push()
    cont.push('latest')
  }
  */
}

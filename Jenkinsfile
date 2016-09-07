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
  if (env.BRANCH_NAME == 'master') {
    tag = 'latest'
  } else {
    tag = env.BRANCH_NAME
  }
  def cont = docker.build "camptocamp/jenkins-test:${tag}"

  stage 'Test docker image'
  sh "docker run camptocamp/jenkins-test:${tag}"

  stage 'Push to dockerhub'
  docker.withRegistry('', 'dockerhub') {
    cont.push()
  }
}

#!/usr/bin/env groovy

node('docker') {
  def golang = docker.image('golang:latest')
  
  stage('Test and static build') {
    parallel 'Test': {
      node('docker') {
        checkout scm
          golang.pull()  // make sure golang image is up-to-date
          golang.inside {
            sh 'go test -v ./...'
          }
      }
    }, 'Static build': {
      node('docker') {
        checkout scm
          golang.pull()  // make sure golang image is up-to-date
          golang.inside {
            sh 'go build -a -installsuffix cgo -o jenkins-test main.go'
          }
        stash includes: 'jenkins-test', name: 'jenkins-test-static'
      }
    }
  }

  stage('Docker image build') {
    checkout scm
    unstash 'jenkins-test-static'
      if (env.BRANCH_NAME == 'master') {
        tag = 'latest'
      } else {
        tag = env.BRANCH_NAME
      }
    def cont = docker.build "camptocamp/jenkins-test:${tag}"
  }

  stage('Test docker image') {
    sh "docker run camptocamp/jenkins-test:${tag}"
  }

  stage('Push to dockerhub') {
    docker.withRegistry('', 'dockerhub') {
      cont.push()
    }
  }

  stage('Approve deploy') {
    timeout(time: 7, unit: 'DAYS') {
      input message: 'Do you want to deploy?', submitter: 'ops'
    }
  }

  stage('Deploy') {
    sh 'echo "Deployed"'
  }
}

stage name: 'Test', concurrency: 1
docker.image('golang').inside {
  checkout scm
  sh 'go test -v ./...'
}

stage name: 'Static build', concurrency: 1
docker.image('golang').inside {
  checkout scm
  sh 'go build -a -installsuffix cgo -o jenkins-test main.go'
  stash includes: 'jenkins-test', name: 'jenkins-test-static'
}

stage name: 'Docker image build', concurrency: 1
node {
  unstash 'jenkins-test-static'
  docker.build 'raphink/jenkins-test:latest'
}

stage name: 'Test docker image', concurrency: 1
node {
  docker.image('raphink/jenkins-test:latest').withRun { c ->
    sh '/jenkins-test'
  }
}

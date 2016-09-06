node ('linux'){
  stage 'Build and Test'
  checkout scm
  sh 'cat /etc/passwd'
}

docker.image('busybox').inside {
  checkout scm
  sh 'cat /etc/group'
}

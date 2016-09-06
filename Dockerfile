FROM scratch

ADD jenkins-test /

ENTRYPOINT [ "/jenkins-test" ]

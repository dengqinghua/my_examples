# docker run -d -p 8080:8080 -p 50000:50000 -v /Users/dengqinghua/jenkins_home:/var/jenkins_home -v /Users/dengqinghua/run/docker.sock:/var/run/docker.sock --name jenkins dengqinghua/jenkins
FROM jenkins
MAINTAINER dengqinghua "dengqinghua.42@gmail.com"

USER root
RUN apt-get -qqy update; apt-get install -qqy sudo
RUN echo "jenkins ALL=NOPASSWD: ALL" >> /etc/sudoers
RUN wget http://get.docker.com/builds/Linux/x86_64/docker-latest.tgz
RUN tar -xvzf docker-latest.tgz
RUN mv docker/* /usr/bin/
RUN mkdir -p /var/jenkins_home

USER jenkins
RUN /usr/local/bin/install-plugins.sh junit git git-client ssh-slaves greenballs chucknorris ws-cleanup

# git clone https://github.com/turnbullpress/james_blog.git ~
# docker run -v $HOME/james_blog:/data/ --name jekyll_blog dengqinghua/jekyll
FROM ubuntu_dengqinghua
MAINTAINER dengqinghua "dengqinghua.42@gmail.com"
RUN apt-get -yqq update
RUN apt-get -yqq install ruby ruby-dev build-essential nodejs
RUN gem install jekyll -v 2.5.3

VOLUME /data
VOLUME /var/www/html
WORKDIR /data

ENTRYPOINT ["jekyll", "build", "--destination=/var/www/html"]

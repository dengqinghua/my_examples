export http_proxy=http://10.0.2.2:1080;export https_proxy=http://10.0.2.2:1080;

# 安装docker, 参考 https://www.digitalocean.com/community/tutorials/how-to-install-and-use-docker-on-ubuntu-18-04
# 下载 golang

# 链接 sudo 的 golang
sudo ln -s /usr/local/go/bin/go /usr/bin/go

# 设置一些 alias

alias gs="git status"
alias gup="git remote update -p"
alias gc="git checkout"
export GOPATH=`go env GOPATH`

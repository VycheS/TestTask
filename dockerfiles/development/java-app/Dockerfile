FROM openjdk:11-jdk-slim

ARG UID=1000
ARG GID=1000
ENV UID=${UID}
ENV GID=${GID}

RUN apt update \
&& apt install -y git curl

RUN groupadd --gid $UID node \
&& useradd --uid $GID --gid node --shell /bin/bash --create-home node \
&& usermod -u $UID node \
&& groupmod -g $GID node

USER ${UID}

RUN git clone git://github.com/ohmybash/oh-my-bash.git ~/.oh-my-bash \
&& cp ~/.bashrc ~/.bashrc.orig \
&& cp ~/.oh-my-bash/templates/bashrc.osh-template ~/.bashrc \
&& sed -i 's/OSH_THEME="font"/OSH_THEME="tonotdo"/' ~/.bashrc

WORKDIR /home/node/app

ENTRYPOINT ["tail","-f","/dev/null"]
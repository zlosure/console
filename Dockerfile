FROM theasp/clojurescript-nodejs:alpine

WORKDIR /console

COPY project.clj /console/project.clj

RUN lein deps

#RUN lein cljsbuild once dev

EXPOSE 3449 55555

COPY . /console

ENTRYPOINT ["top", "-b"]

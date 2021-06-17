# numberAssigner

# README #

### What is this repository for? ###

* Number assigner application

### How to access API documentation? ###

* Maven -> assigner -> Lifecycle -> package
* Go to ../numberAssigner/assigner/target/generated-docs
* open index.html

### How to run API application? ###

* Maven -> assigner -> Lifecycle -> package
* Go to ../numberAssigner/assigner/target
* run assigner-0.0.1-SNAPSHOT.jar


### How to run Console application? ###

* Maven -> console.application -> Lifecycle -> package
* Go to ../numberAssigner/assignerConsoleApplication/target
* run assigner-jar-with-dependencies.jar
* java –jar <Jar file>.jar -api <api url> -if <input filename> -of <output filename> -user <api authorization username> -pass <api authorization password>
* For example: java –jar assigner-jar-with-dependencies.jar -api http://localhost:8080 -if input.txt -of output.txt -user user -pass pass 

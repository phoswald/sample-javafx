#!/bin/bash
java \
  -cp 'target/libs-mp/*:target/libs-cp/*:target/sample-javafx.jar' \
  -Dapp.jdbc.url=jdbc:h2:./databases/task-db \
  com.github.phoswald.sample.javafx.JavaFxApplicationLauncher

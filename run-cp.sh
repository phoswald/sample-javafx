#!/bin/bash
java \
  -cp 'target/*:target/libs-mp/*:target/libs-cp/*' \
  -Dapp.jdbc.url=jdbc:h2:./databases/task-db \
  com.github.phoswald.sample.ApplicationLauncher

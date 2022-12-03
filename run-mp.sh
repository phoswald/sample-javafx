#!/bin/bash
java \
  -p  'target/libs-mp' \
  -cp 'target/*:target/libs-cp/*' \
  --add-modules ALL-MODULE-PATH \
  -Dapp.jdbc.url=jdbc:h2:./databases/task-db \
  com.github.phoswald.sample.Application

#!/bin/sh
java \
  -p "$(dirname "$0")/../lib/" \
  --add-modules ALL-MODULE-PATH \
  com.github.phoswald.sample.Application

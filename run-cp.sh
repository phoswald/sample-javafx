#!/bin/sh
java \
  -cp "$(dirname "$0")/../lib/*" \
  com.github.phoswald.sample.ApplicationLauncher

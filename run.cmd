@echo off
set dirname=%~dp0
javaw -p "%dirname%..\lib" --add-modules ALL-MODULE-PATH com.github.phoswald.sample.Application

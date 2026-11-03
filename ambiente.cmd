@echo off
cls

set NODE_HOME=C:\dev\node
set JAVA_HOME=C:\dev\jdk-25.0.3
set MAVEN_HOME=C:\dev\maven

set PATH=%NODE_HOME%;%JAVA_HOME%\bin;%MAVEN_HOME%\bin;%PATH%

echo Java: 
cmd /c java -version
echo.
echo Maven:
cmd /c mvn -v
echo.
echo Node:
cmd /c node -v

cmd /k
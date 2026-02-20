@echo off
echo Setting up Banking System...
echo.

REM Check if MySQL connector exists
if not exist "mysql-connector-java-8.0.33.jar" (
    echo Downloading MySQL connector...
    curl -L -o mysql-connector-java-8.0.33.jar "https://repo1.maven.org/maven2/mysql/mysql-connector-java/8.0.33/mysql-connector-java-8.0.33.jar"
)

echo Compiling Java files...
javac -cp "mysql-connector-java-8.0.33.jar" src/*.java -d out

echo.
echo ====================================
echo     BANKING SYSTEM READY TO RUN
echo ====================================
echo.
echo To run the application:
echo java -cp "out;mysql-connector-java-8.0.33.jar" Main
echo.
pause
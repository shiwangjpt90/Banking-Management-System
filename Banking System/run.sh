#!/bin/bash
echo "Setting up Banking System..."
echo ""

# Check if MySQL connector exists
if [ ! -f "mysql-connector-java-8.0.33.jar" ]; then
    echo "Downloading MySQL connector..."
    curl -L -o mysql-connector-java-8.0.33.jar "https://repo1.maven.org/maven2/mysql/mysql-connector-java/8.0.33/mysql-connector-java-8.0.33.jar"
fi

echo "Compiling Java files..."
mkdir -p out
javac -cp "mysql-connector-java-8.0.33.jar" src/*.java -d out

echo ""
echo "===================================="
echo "     BANKING SYSTEM READY TO RUN"
echo "===================================="
echo ""
echo "To run the application:"
echo "java -cp \"out:mysql-connector-java-8.0.33.jar\" Main"
echo ""
read -p "Press Enter to continue..."
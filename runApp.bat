@echo off
echo Cleaning previous build files...
rmdir /s /q com
del /q *.class

echo Compiling Java source...
javac -cp libs\gson-2.12.1.jar -d . PriorSolutionTest.java
if %ERRORLEVEL% neq 0 (
    echo Compilation failed!
    exit /b 1
)

echo Running the program...
java -cp .;libs/gson-2.12.1.jar com.example.PriorSolutionTest

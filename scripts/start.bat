@echo off

if "%1" == "" (
    java -jar easyxms.jar
) else (
    java -jar easyxms.jar %1 %2 %3 %4 %5 %6 %7 %8 %9
)

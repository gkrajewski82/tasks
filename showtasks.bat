call runcrud.bat
if "%ERRORLEVEL%" == "0" goto chromerun
goto fail

:chromerun
start microsoft-edge:http://localhost:8080/crud/v1/tasks

:fail
There were errors running GRADLEW BUILD
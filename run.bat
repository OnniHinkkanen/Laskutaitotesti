REM java -jar %0\laskutaitotesti.jar

cls
@pushd %~dp0
:::::::::::::::::::
java -jar laskutaitotesti.jar -pdf -tests 50 --maxima-path C:\devel\maxima-5.41.0\bin\
:::::::::::::::::::
@popd
pause
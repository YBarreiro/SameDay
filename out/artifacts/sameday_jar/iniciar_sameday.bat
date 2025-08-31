@echo off
:: Script para lanzar la aplicación SameDay
:: Ejecuta SameDay.jar desde esta misma carpeta

cd /d "%~dp0"

echo Iniciando SameDay...
echo ----------------------

java -jar SameDay.jar

echo.
echo Pulsa una tecla para cerrar esta ventana...
pause > nul

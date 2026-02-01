@echo off
:: Script profissional para iniciar o projeto em modo desenvolvimento

echo ========================================
echo   League of Legends Team Builder
echo   Iniciando servidores...
echo ========================================
echo.

:: Verifica se o PostgreSQL está rodando
echo [1/4] Verificando PostgreSQL...
pg_isready -U postgres >nul 2>&1
if %errorlevel% neq 0 (
    echo [ERRO] PostgreSQL não está rodando!
    echo Por favor, inicie o PostgreSQL antes de continuar.
    pause
    exit /b 1
)
echo [OK] PostgreSQL está ativo
echo.

:: Inicia o backend
echo [2/4] Iniciando backend (porta 8080)...
start "Backend - Spring Boot" cmd /k "cd /d %~dp0.. && mvn spring-boot:run"
timeout /t 15 /nobreak >nul
echo [OK] Backend iniciado
echo.

:: Inicia o frontend
echo [3/4] Iniciando frontend (porta 4200)...
start "Frontend - Angular" cmd /k "cd /d %~dp0..\frontend && npm start"
timeout /t 10 /nobreak >nul
echo [OK] Frontend iniciado
echo.

:: Abre o navegador
echo [4/4] Abrindo navegador...
timeout /t 5 /nobreak >nul
start http://localhost:4200
echo [OK] Aplicação pronta!
echo.

echo ========================================
echo   Servidores ativos:
echo   - Backend:  http://localhost:8080
echo   - Frontend: http://localhost:4200
echo ========================================
echo.
echo Pressione qualquer tecla para finalizar os servidores...
pause >nul

:: Mata os processos
taskkill /F /IM java.exe >nul 2>&1
taskkill /F /IM node.exe >nul 2>&1

echo Servidores finalizados.
timeout /t 2 /nobreak >nul

Write-Host ""
Write-Host "========================================" -ForegroundColor Green
Write-Host "   BANKING SYSTEM - QUICK START" -ForegroundColor Yellow
Write-Host "========================================" -ForegroundColor Green
Write-Host ""

# Step 1: Check if compiled
if (-not (Test-Path "out")) {
    Write-Host "⚠  Compiling Java files..." -ForegroundColor Yellow
    javac -cp "mysql-connector-java-8.0.33.jar" src/*.java -d out
    if ($LASTEXITCODE -ne 0) {
        Write-Host "✗ Compilation failed!" -ForegroundColor Red
        exit
    }
    Write-Host "✓ Compilation successful" -ForegroundColor Green
}

# Step 2: Run the app
Write-Host ""
Write-Host "🚀 Starting Banking System..." -ForegroundColor Cyan
Write-Host ""

java -cp "out;mysql-connector-java-8.0.33.jar" Main
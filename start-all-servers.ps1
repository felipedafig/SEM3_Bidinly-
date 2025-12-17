# PowerShell script to start all 5 servers
# Usage: .\start-all-servers.ps1

Write-Host "========================================" -ForegroundColor Cyan
Write-Host "  Starting All Servers" -ForegroundColor Cyan
Write-Host "========================================" -ForegroundColor Cyan
Write-Host ""

# Get the script directory (root of the project)
$scriptPath = Split-Path -Parent $MyInvocation.MyCommand.Path
Set-Location $scriptPath

# Function to start a server in a new window
function Start-ServerInNewWindow {
    param(
        [string]$ServerName,
        [string]$WorkingDirectory,
        [string]$Command,
        [string]$Color = "Yellow"
    )
    
    Write-Host "[$ServerName] Starting..." -ForegroundColor $Color
    
    # Create the full command with title
    $fullCommand = @"
cd '$WorkingDirectory'; 
`$Host.UI.RawUI.WindowTitle = '$ServerName'; 
Write-Host '========================================' -ForegroundColor Cyan;
Write-Host '  $ServerName' -ForegroundColor Cyan;
Write-Host '========================================' -ForegroundColor Cyan;
Write-Host '';
$Command
"@
    
    Start-Process powershell -ArgumentList "-NoExit", "-Command", $fullCommand
    Start-Sleep -Seconds 2
}

Write-Host "Starting servers in separate windows..." -ForegroundColor Green
Write-Host ""

# Start DataTierServer (Java Spring Boot)
Start-ServerInNewWindow -ServerName "DataTierServer" `
    -WorkingDirectory "$scriptPath\DataTierServer" `
    -Command ".\mvnw.cmd spring-boot:run" `
    -Color "Magenta"

# Start PaymentServiceServer (Java Spring Boot)
Start-ServerInNewWindow -ServerName "PaymentServiceServer" `
    -WorkingDirectory "$scriptPath\PaymentServiceServer" `
    -Command ".\mvnw.cmd spring-boot:run" `
    -Color "Magenta"

# Start PropertyServiceServer (Java Spring Boot)
Start-ServerInNewWindow -ServerName "PropertyServiceServer" `
    -WorkingDirectory "$scriptPath\PropertyServiceServer" `
    -Command ".\mvnw.cmd spring-boot:run" `
    -Color "Magenta"

# Start WebAPI (C# .NET)
Start-ServerInNewWindow -ServerName "WebAPI" `
    -WorkingDirectory "$scriptPath\MainServer\WebAPI" `
    -Command "dotnet run" `
    -Color "Blue"

# Start BlazorApp (C# .NET Blazor Server)
Start-ServerInNewWindow -ServerName "BlazorApp" `
    -WorkingDirectory "$scriptPath\Client\BlazorApp" `
    -Command "dotnet run" `
    -Color "Green"

Write-Host ""
Write-Host "========================================" -ForegroundColor Green
Write-Host "  All servers started successfully!" -ForegroundColor Green
Write-Host "========================================" -ForegroundColor Green
Write-Host ""
Write-Host "Each server is running in its own PowerShell window." -ForegroundColor Cyan
Write-Host "You can monitor logs in each window." -ForegroundColor Cyan
Write-Host ""
Write-Host "To stop servers, close their respective windows." -ForegroundColor Yellow
Write-Host ""
Write-Host "Press any key to close this window (servers will continue running)..." -ForegroundColor Gray
$null = $Host.UI.RawUI.ReadKey("NoEcho,IncludeKeyDown")

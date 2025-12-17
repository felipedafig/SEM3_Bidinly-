# PowerShell script to stop all 5 servers
# Usage: .\stop-all-servers.ps1

Write-Host "========================================" -ForegroundColor Red
Write-Host "  Stopping All Servers" -ForegroundColor Red
Write-Host "========================================" -ForegroundColor Red
Write-Host ""

# Get the script directory (root of the project)
$scriptPath = Split-Path -Parent $MyInvocation.MyCommand.Path
$stoppedCount = 0

# Function to stop a server process
function Stop-ServerProcess {
    param(
        [string]$ServerName,
        [string]$ProcessName,
        [string]$WorkingDirectory = ""
    )
    
    $found = $false
    
    try {
        # Try to find processes by name
        $processes = Get-Process -Name $ProcessName -ErrorAction SilentlyContinue
        
        foreach ($proc in $processes) {
            # For Java processes, check if it's a Spring Boot app
            if ($ProcessName -eq "java") {
                $commandLine = (Get-CimInstance Win32_Process -Filter "ProcessId = $($proc.Id)").CommandLine
                if ($commandLine -and ($commandLine -like "*$ServerName*" -or $commandLine -like "*spring-boot*")) {
                    Write-Host "[$ServerName] Stopping Java process (PID: $($proc.Id))..." -ForegroundColor Yellow
                    Stop-Process -Id $proc.Id -Force -ErrorAction SilentlyContinue
                    $found = $true
                    $script:stoppedCount++
                }
            }
            # For dotnet processes, check the working directory
            elseif ($ProcessName -eq "dotnet" -and $WorkingDirectory) {
                $commandLine = (Get-CimInstance Win32_Process -Filter "ProcessId = $($proc.Id)").CommandLine
                if ($commandLine -and $commandLine -like "*$WorkingDirectory*") {
                    Write-Host "[$ServerName] Stopping dotnet process (PID: $($proc.Id))..." -ForegroundColor Yellow
                    Stop-Process -Id $proc.Id -Force -ErrorAction SilentlyContinue
                    $found = $true
                    $script:stoppedCount++
                }
            }
        }
        
        # Also try to find PowerShell windows with the server name in the title
        $powershellProcesses = Get-Process -Name "powershell" -ErrorAction SilentlyContinue
        foreach ($psProc in $powershellProcesses) {
            try {
                $windowTitle = $psProc.MainWindowTitle
                if ($windowTitle -eq $ServerName) {
                    Write-Host "[$ServerName] Stopping PowerShell window (PID: $($psProc.Id))..." -ForegroundColor Yellow
                    Stop-Process -Id $psProc.Id -Force -ErrorAction SilentlyContinue
                    $found = $true
                    $script:stoppedCount++
                }
            } catch {
                # Ignore errors accessing window title
            }
        }
        
        if (-not $found) {
            Write-Host "[$ServerName] Not found or already stopped" -ForegroundColor Gray
        }
    } catch {
        Write-Host "[$ServerName] Error stopping: $_" -ForegroundColor Red
    }
}

Write-Host "Searching for running servers..." -ForegroundColor Cyan
Write-Host ""

# Stop Java Spring Boot servers
Stop-ServerProcess -ServerName "DataTierServer" -ProcessName "java"
Stop-ServerProcess -ServerName "PaymentServiceServer" -ProcessName "java"
Stop-ServerProcess -ServerName "PropertyServiceServer" -ProcessName "java"

# Stop .NET servers
Stop-ServerProcess -ServerName "WebAPI" -ProcessName "dotnet" -WorkingDirectory "MainServer\WebAPI"
Stop-ServerProcess -ServerName "BlazorApp" -ProcessName "dotnet" -WorkingDirectory "Client\BlazorApp"

# Also stop any remaining Maven wrapper processes
Write-Host ""
Write-Host "Cleaning up Maven wrapper processes..." -ForegroundColor Cyan
$mavenProcesses = Get-Process -Name "java" -ErrorAction SilentlyContinue | Where-Object {
    $commandLine = (Get-CimInstance Win32_Process -Filter "ProcessId = $($_.Id)").CommandLine
    $commandLine -and ($commandLine -like "*mvnw*" -or $commandLine -like "*maven*")
}
foreach ($mavenProc in $mavenProcesses) {
    Write-Host "Stopping Maven wrapper process (PID: $($mavenProc.Id))..." -ForegroundColor Yellow
    Stop-Process -Id $mavenProc.Id -Force -ErrorAction SilentlyContinue
    $stoppedCount++
}

Write-Host ""
Write-Host "========================================" -ForegroundColor Green
Write-Host "  Stopped $stoppedCount server process(es)" -ForegroundColor Green
Write-Host "========================================" -ForegroundColor Green
Write-Host ""
Write-Host "All servers have been stopped." -ForegroundColor Cyan
Write-Host ""
Write-Host "Press any key to exit..." -ForegroundColor Gray
$null = $Host.UI.RawUI.ReadKey("NoEcho,IncludeKeyDown")

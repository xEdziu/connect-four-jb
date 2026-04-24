# Connect Four (Compose HTML)

Minimal web implementation of Connect Four built with Kotlin Multiplatform and Compose HTML.
This is the solution for the internship application task for JetBrains.

## Task Coverage

- Configurable board size (`rows`, `columns`, currently limited to `4..20` in UI)
- Configurable win condition (`winCondition`, currently limited to `3..10` and not larger than board size)
- Gravity rule (tokens fall to the lowest free slot in a selected column)
- Two-player local gameplay (alternating turns)
- Win and draw detection
- Responsive layout for desktop and mobile (`CSS grid` + media queries)

## Bonus Coverage

- Piece drop animation (`dropIn` keyframes in CSS)
- Persistence after refresh (saved in `localStorage`)
- Unit tests for core logic (`Board`, `GameEngine`, `WinValidator`)

## Run (Web)

Windows:

```powershell
.\gradlew.bat :composeApp:jsBrowserDevelopmentRun
```

macOS/Linux:

```bash
./gradlew :composeApp:jsBrowserDevelopmentRun
``` 

## Run Tests

Windows:

```powershell
.\gradlew.bat :composeApp:jvmTest
```

macOS/Linux:

```bash
./gradlew :composeApp:jvmTest
```

## Project Structure

- `composeApp/src/commonMain` - shared game logic
- `composeApp/src/jsMain` - web UI (Compose HTML)
- `composeApp/src/commonTest` - core logic tests

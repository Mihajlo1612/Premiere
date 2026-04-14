Premiere
This is a Kotlin Multiplatform project targeting Android, built with a modern tech stack and robust architecture.

Project Overview
Shared Code: The /composeApp directory contains the core logic shared across applications.

commonMain: Contains code common to all targets.

Platform Specifics: Other folders contain code compiled only for the specific platform (e.g., androidMain for Android-specific APIs).

Architecture: Strictly follows the Model-View-Intent (MVI) pattern to ensure predictable state management.

Tech Stack
UI: Compose Multiplatform (Material 3)

DI: Koin

Networking: Ktor Client + Ktorfit

Asynchronous: Kotlin Coroutines & Flow

Serialization: Kotlinx Serialization

Image loading: Coil

SDK: Min SDK 26 | Target SDK 35

Features
MVI Architecture: Handles Loading, Success, and Error states seamlessly.

Orientation Support: Maintains state across portrait and landscape modes.

Modern Navigation: Single-activity pattern using Jetpack Navigation 2.

Stability: Designed to prevent unknown states or app crashes.

Build and Run Android Application
To build and run the development version of the Android app, use the run configuration in your IDE or use the terminal:

On macOS/Linux:

Bash

./gradlew :composeApp:assembleDebug
On Windows:

PowerShell

.\gradlew.bat :composeApp:assembleDebug

Project Structure
Plaintext

Premiere/
├── composeApp/
│   ├── src/
│   │   ├── commonMain/   # Shared logic & MVI
│   │   └── androidMain/  # Android specific implementation
├── gradle/
└── build.gradle.kts
Build and Run (Detailed PowerShell)
PowerShell

# Install debug APK on a connected device
.\gradlew :composeApp:installDebug

# Run Unit tests
.\gradlew :composeApp:test
License: Distributed under the MIT License. See LICENSE for more information.

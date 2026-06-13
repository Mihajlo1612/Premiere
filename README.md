# Premiere

> A Kotlin Multiplatform project targeting Android, built with a modern tech stack and robust architecture.

---

## About

Premiere is a Kotlin Multiplatform (KMP) Android application that demonstrates clean architecture, modern UI patterns, and best practices for building scalable Android apps.

---

## Features

- **MVI Architecture** — Handles `Loading`, `Success`, and `Error` states seamlessly and predictably.
- **Orientation Support** — Maintains state across portrait and landscape modes without data loss.
- **Modern Navigation** — Single-activity pattern using Jetpack Navigation 2.
- **Stability** — Designed to prevent unknown states or app crashes.
- TODO: Add screenshots

---

## Tech Stack

| Layer | Technology |
|---|---|
| **UI** | Compose Multiplatform (Material 3) |
| **DI** | Koin |
| **Networking** | Ktor Client + Ktorfit |
| **Async** | Kotlin Coroutines & Flow |
| **Serialization** | Kotlinx Serialization |
| **Image Loading** | Coil |

**SDK Requirements:**
- Min SDK: 26
- Target SDK: 35

---

## Project Structure

```
Premiere/
├── composeApp/
│   ├── src/
│   │   ├── commonMain/   # Shared logic & MVI
│   │   └── androidMain/  # Android-specific implementation
├── gradle/
└── build.gradle.kts
```

**Modules:**
- `commonMain` — Contains code common to all targets (business logic, MVI, shared models).
- `androidMain` — Contains Android-specific implementations and platform APIs.

---

## Getting Started

### Requirements

- Android Studio (latest stable recommended)
- JDK 17+
- Android device or emulator (API level 26+)

### Build & Run

**On macOS/Linux:**

```bash
./gradlew :composeApp:assembleDebug
```

**On Windows:**

```powershell
.\gradlew.bat :composeApp:assembleDebug
```

**Install on a connected device:**

```powershell
.\gradlew :composeApp:installDebug
```

**Run Unit Tests:**

```powershell
.\gradlew :composeApp:test
```

---

## Contributing

- TODO: Add contribution guidelines
- TODO: Add code style / formatting rules
- TODO: Add PR template

---

## License

Distributed under the **MIT License**. See [LICENSE](LICENSE) for more information.


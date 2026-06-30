# DriveOS — Automotive Dashboard

DriveOS is a personal Android project built to explore how modern automotive dashboards work behind the scenes. It simulates a vehicle's CAN bus, processes telemetry in real time, and presents the data through a responsive dashboard with custom gauges, diagnostics, and trip information.

The project follows a clean MVVM architecture and focuses on writing maintainable, production-style Android code while incorporating native C++ through the Android NDK.

## What you'll find

- Real-time CAN bus simulation running every 100 ms
- Dashboard with custom speedometer and RPM gauges
- HVAC controls and live status updates
- OBD-II diagnostic trouble code viewer
- Trip computer showing distance, average speed, fuel economy, and drive time
- Telemetry storage using Room for local persistence

## Tech Stack

- Kotlin
- C++ (Android NDK)
- MVVM Architecture
- Hilt
- Room
- Coroutines & StateFlow
- SQLite
- Gradle (KTS)
- CMake

## Project Structure

- `CANBusService` - Polls simulated CAN frames in the background
- `CANProtocolParser` - Converts raw CAN messages into usable vehicle data
- `VehicleRepository` - Acts as the single source of truth for the application
- `DashboardViewModel` - Supplies reactive vehicle state to the UI
- `Custom Gauge Views` - Canvas-based speedometer and RPM gauges
- `DriveOSDatabase` - Stores telemetry history using Room

## Screens

- Dashboard
- HVAC Controls
- Diagnostics
- Trip Computer

## Running the Project

Open the project in Android Studio (Hedgehog or newer), make sure the Android NDK and CMake are installed, then build and run using assembleDebug.

## Simulated CAN Signals

| CAN ID | Signal | Range |
|--------|--------|-------|
| 0x100 | Vehicle Speed | 0-260 km/h |
| 0x101 | Engine RPM | 0-8000 RPM |
| 0x102 | Coolant Temperature | -40C to 150C |
| 0x103 | Fuel Level | 0-100% |
| 0x200 | HVAC Temperature | 16-30C |
| 0x201 | Fan Speed | 0-7 |
| 0x300 | OBD-II Diagnostic Code | Standard DTC |

This project was built as a learning exercise to better understand Android system design, automotive software architecture, and real-time data processing.

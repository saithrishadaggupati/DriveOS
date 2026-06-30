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

| PID/CAN ID | Signal | Notes |
|---|---|---|
| 0x0C | Engine RPM | Standard OBD-II PID |
| 0x0D | Vehicle Speed | Standard OBD-II PID, 0-260 km/h |
| 0x05 | Coolant Temperature | Standard OBD-II PID, -40C to 215C |
| 0x11 | Throttle Position | Standard OBD-II PID, 0-100% |
| 0x2F | Fuel Level | Standard OBD-II PID, 0-100% |
| 0x04 | Engine Load | Standard OBD-II PID, 0-100% |
| 0xA0-0xA4 | Odometer, Trip Distance, Trip Duration, Fuel Consumed, Avg Speed | Custom extended PIDs |
| 0x3B0 | HVAC Control | Set temp, fan speed, A/C, recirc |
| 0x7DF / 0x7E8 | OBD-II Request / Response | Diagnostic trouble codes |

This project was built as a learning exercise to better understand Android system design, automotive software architecture, and real-time data processing.


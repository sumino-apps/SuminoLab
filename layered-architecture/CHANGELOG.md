# Changelog

All notable changes to this project will be documented in this file.

## [Unreleased]
- Moved the existing layered architecture project into the `layered-architecture` directory.
- Reorganized UI into a feature-based structure (`ui/feature/*`).
- Introduced MVI architecture with reusable base classes (`BaseViewModel`, `MviBase`).
- Added core architecture components (`DispatcherProvider`, `AppResult`).
- Added reusable UI components (`ErrorView`, `LoadingDialog`).
- Upgraded build tools (AGP 9.2.1, Gradle 9.4.1).

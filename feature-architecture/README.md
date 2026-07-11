# Feature Architecture (Package by Feature)

This project demonstrates organizing an Android application by **feature** rather than by technical layer. 

## Structure
- `app/src/main/java/com/sumino/xyz/core`: Shared core logic, DI, and utilities.
- `app/src/main/java/com/sumino/xyz/feature/home`: All UI, Domain, and Data logic specific to the Home feature.
- `app/src/main/java/com/sumino/xyz/feature/auth`: All UI, Domain, and Data logic specific to the Auth feature.

This approach scales better as the team grows, because developers can work on isolated features without touching common layer folders.

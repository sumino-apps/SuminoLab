# Module Architecture (Multi-Module Architecture)

This project demonstrates organizing an Android application into **multiple Gradle modules**.

## Structure
- `:app`: The main application module that ties everything together.
- `:core`: Shared Android components, utilities, and base classes.
- `:data`: Data layer (Network, Database, Repositories).
- `:domain`: Pure Kotlin module (Use Cases, Entities, Domain Interfaces).
- `:features:home`: UI and presentation logic for the Home feature.
- `:features:auth`: UI and presentation logic for the Auth feature.

This architecture drastically improves build times and enforces strict boundaries between layers and features.

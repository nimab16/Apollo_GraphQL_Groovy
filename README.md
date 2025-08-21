# Apollo GraphQL Android App

Sometimes we need to work on legacy projects and add new features.  
This is a simple example demonstrating how to integrate Apollo GraphQL into an Android project using a Groovy-based build script (an older version).

## Features

- 🚀 **Splash Screen**: Beautiful animated splash screen using Lottie animations
- 🌍 **Countries List**: Display a list of countries with their flags and basic information
- 🔍 **Country Details**: View detailed information about each country
- ⚡ **Clean Architecture**: Using Clean Architecture principles
- ✅ **Click Persistence**: Country item clicks are stored in Room and reflected in the UI

## Tech Stack

- **Language**: Kotlin
- **Architecture**: MVVM (Model-View-ViewModel)
- **Dependency Injection**: Hilt
- **GraphQL Client**: Apollo Client
- **Persistence**: Room (offline cache and click tracking)
- **UI Components**: 
  - Navigation Component
  - ViewBinding
  - Material Design
  - Lottie Animations
- **Coroutines**: For asynchronous operations
- **StateFlow**: For reactive UI updates

## Prerequisites

- Android SDK 24 or higher
- JDK 11 or 17 (recommended)

Note on build environment:
- Using JDK 19+ with this older AGP setup (4.1.x) can cause manifest processing failures
  (InaccessibleObjectException via Gson). Use JDK 11 or 17, or upgrade AGP if you need JDK 19+.

## Getting Started

1. Clone the repository:
   ```bash
   git clone https://github.com/yourusername/ApolloGraphQlGroovy.git
   ```

2. Open the project in Android Studio

3. Build and run the project

## Project Structure

```
app/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/example/apollographql/
│   │   │       ├── data/           # Data layer
│   │   │       ├── di/             # Dependency injection
│   │   │       ├── domain/         # Domain layer
│   │   │       ├── presentation/   # UI layer
│   │   │       └── utils/          # Utility classes
│   │   ├── res/
│   │   │   ├── layout/            # Layout files
│   │   │   ├── navigation/        # Navigation graphs
│   │   │   └── raw/              # Raw resources (animations)
│   │   └── AndroidManifest.xml
│   └── build.gradle
```

## Key Components

### GraphQL Integration
- Uses Apollo Client for GraphQL operations
- Auto-generated Kotlin models from GraphQL schema
- Efficient data fetching and caching

### UI Components
- **SplashFragment**: Animated splash screen with Lottie
- **CountriesListFragment**: Displays list of countries
- **CountryDetailsFragment**: Shows detailed country information

### Room-backed click tracking
- Entity: `CountryClickEntity(code: String, clickedAt: Long)` stored in the `country_clicks` table
- DAO:
  - `upsertCountryClick(click: CountryClickEntity)`
  - `observeClickedCodes(): Flow<List<String>>`
- Database: `AppDatabase` now includes `CountryClickEntity` (version 2) with destructive migration fallback
- DI: `CountryDao` is provided via Hilt in `AppModule`
- UI wiring:
  - `CountriesListFragment` injects `CountryDao`, persists clicks on item tap, and observes `observeClickedCodes()`
  - `CountriesAdapter` receives an `isClicked(code)` lambda to style items based on persisted state

### Architecture
- Follows Clean Architecture principles
- Uses Hilt for dependency injection
- Implements MVVM pattern with ViewModels
- Uses StateFlow for reactive UI updates

## Acknowledgments

- [Apollo GraphQL](https://www.apollographql.com/) for the GraphQL client
- [Lottie](https://airbnb.design/lottie/) for the animation library
- [Material Design](https://material.io/) for UI components 

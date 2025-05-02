# Apollo GraphQL Android App

Sometimes we need to work on old project and add new features.
This is just a very simple sample to show how I integrated Apollo GraphQl in Groovy build ( that is an old version).
An Android application that demonstrates the use of Apollo GraphQL client to fetch and display country data.

## Features

- 🚀 **Splash Screen**: Beautiful animated splash screen using Lottie animations
- 🌍 **Countries List**: Display a list of countries with their flags and basic information
- 🔍 **Country Details**: View detailed information about each country
- ⚡ **Clean Architecture**: Using Clean Architecture principles

## Tech Stack

- **Language**: Kotlin
- **Architecture**: MVVM (Model-View-ViewModel)
- **Dependency Injection**: Hilt
- **GraphQL Client**: Apollo Client
- **UI Components**: 
  - Navigation Component
  - ViewBinding
  - Material Design
  - Lottie Animations
- **Coroutines**: For asynchronous operations
- **StateFlow**: For reactive UI updates

## Prerequisites

- Android SDK 24 or higher
- Kotlin 1.8 or higher
- Gradle 7.0 or higher

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

### Architecture
- Follows Clean Architecture principles
- Uses Hilt for dependency injection
- Implements MVVM pattern with ViewModels
- Uses StateFlow for reactive UI updates

## Acknowledgments

- [Apollo GraphQL](https://www.apollographql.com/) for the GraphQL client
- [Lottie](https://airbnb.design/lottie/) for the animation library
- [Material Design](https://material.io/) for UI components 
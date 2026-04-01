# MovieApp

Application Android (Kotlin) pour explorer les films populaires via l'API TMDB, rechercher un film, afficher ses details, et lancer la bande-annonce.

## Fonctionnalites

- Liste de films populaires (TMDB) avec `RecyclerView` + `CardView`
- Recherche en temps reel par titre
- Ecran detail film (titre, description, image)
- Lecture de bande-annonce YouTube dans `WebView`
- Carte Google Maps integree sur l'ecran detail

## Stack technique

- Kotlin
- Android SDK
- Volley (requetes HTTP)
- Glide (chargement images)
- Google Maps SDK

## Prerequis

- Android Studio (version recente)
- JDK 17+
- Emulateur Android ou appareil reel
- Cle API TMDB
- Cle API Google Maps

## Configuration

### 1) TMDB API Key

Ajoute ta cle TMDB dans le code (selon ton implementation actuelle) ou, prefere, dans une source non versionnee.

### 2) Google Maps API Key

Dans `app/src/main/AndroidManifest.xml`, configure la meta-data:

```xml
<meta-data
    android:name="com.google.android.geo.API_KEY"
    android:value="YOUR_GOOGLE_MAPS_KEY" />
```

### 3) Permissions Android

Verifier la presence des permissions reseau/localisation dans le manifeste:

- `android.permission.INTERNET`
- `android.permission.ACCESS_NETWORK_STATE`
- `android.permission.ACCESS_FINE_LOCATION`
- `android.permission.ACCESS_COARSE_LOCATION`

## Lancer le projet

Depuis la racine du projet:

```powershell
.\gradlew.bat clean
.\gradlew.bat assembleDebug
```

Puis lance l'application depuis Android Studio (Run `app`) ou installe l'APK debug genere.

## Structure utile

- `app/src/main/java/com/example/movieapp/MainActivity.kt` : liste + recherche
- `app/src/main/java/com/example/movieapp/MyMovieAdapter.kt` : adaptateur RecyclerView
- `app/src/main/java/com/example/movieapp/MovieDetailActivity.kt` : details + trailer + map
- `app/src/main/java/com/example/movieapp/VideoPlayerActivity.kt` : lecteur WebView YouTube

## Comportement attendu

- L'ecran principal affiche les films populaires TMDB.
- Un clic sur un film ouvre la page detail.
- Le bouton **Play Movie** ouvre la bande-annonce si disponible.

## Notes

- Si l'app se ferme en ouvrant un detail, verifier d'abord:
  - la cle API TMDB valide,
  - la declaration des `Activity` dans `AndroidManifest.xml`,
  - les logs `Logcat` (filtre sur `MovieDetailActivity` ou `AndroidRuntime`).


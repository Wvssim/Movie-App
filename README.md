# MovieApp

Application Android (Kotlin) pour explorer les films populaires avec l'API TMDB.

## Fonctionnalites

- Liste de films populaires (TMDB) avec `RecyclerView` + `CardView`
- Recherche en temps reel par titre
- Ecran detail film (titre, description, poster)
- Lecture de bande-annonce YouTube dans `WebView`
- Donnees recuperees en HTTP via Volley
- Images chargees avec Glide

## Architecture

Le projet suit une architecture simple de type **MVC**:

- **Model**: `app/src/main/java/com/example/movieapp/MyMovieData.kt`
- **View**: layouts XML dans `app/src/main/res/layout/`
- **Controller**: `MainActivity.kt`, `MovieDetailActivity.kt`, `VideoPlayerActivity.kt`

## Stack technique

- Kotlin
- Android SDK
- Volley
- Glide
- RecyclerView + CardView

## Prerequis

- Android Studio
- JDK 11+
- Emulateur Android ou appareil reel
- Cle API TMDB valide

## Configuration

1. Verifier la cle TMDB dans `app/src/main/java/com/example/movieapp/MainActivity.kt`.
2. Verifier les permissions dans `app/src/main/AndroidManifest.xml`:
   - `android.permission.INTERNET`
   - `android.permission.ACCESS_NETWORK_STATE`
3. (Optionnel) Si Google Maps est active, ajouter votre cle Maps dans le manifest.

## Lancer le projet

```powershell
.\gradlew.bat clean
.\gradlew.bat assembleDebug
```

Puis lancer l'application depuis Android Studio (`Run > app`).

## Structure utile

- `app/src/main/java/com/example/movieapp/MainActivity.kt`
- `app/src/main/java/com/example/movieapp/MyMovieAdapter.kt`
- `app/src/main/java/com/example/movieapp/MovieDetailActivity.kt`
- `app/src/main/java/com/example/movieapp/VideoPlayerActivity.kt`
- `app/src/main/AndroidManifest.xml`

## Depannage rapide

- Si la liste est vide: verifier la connexion internet et la cle TMDB.
- Si l'app ferme sur detail: verifier `MovieDetailActivity` dans le manifest et Logcat.
- Si la video ne se lance pas: verifier l'URL trailer YouTube renvoyee par TMDB.

## License

Projet educatif.

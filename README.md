# Nafusite Mobile App 💎

Nafusite is a premium Jewellery Store mobile application tailored for the Kenyan market. Built with modern Android technologies, it provides a seamless shopping experience for high-quality jewelry, including rings, necklaces, bracelets, and more.

## 🇰🇪 Proudly Kenyan
Designed specifically for the Kenyan audience, Nafusite integrates local preferences and payment systems to provide a localized shopping experience.

## ✨ Features

- **Product Showcase**: High-resolution image galleries and video previews for each jewelry piece.
- **Modern UI/UX**: Clean, elegant interface built with Jetpack Compose using Material 3.
- **Custom Video Player**: Experience jewelry in motion with integrated video components powered by Media3.
- **Real-time Catalog**: Browse products and categories fetched in real-time from Firebase.
- **Profile Management**: Update user details and upload profile pictures via Cloudinary integration.
- **Onboarding Journey**: Personalized registration flow including product preference selection.
- **Secure Authentication**: Firebase-backed authentication for user accounts and data persistence.
- **Localized Payments**: Support for Kenyan payment methods including M-PESA (Planned).
- **Dark Mode Support**: Full compatibility with system-wide dark and light themes.

## 🛠 Tech Stack

- **Language**: [Kotlin](https://kotlinlang.org/)
- **UI Framework**: [Jetpack Compose](https://developer.android.com/jetpack/compose)
- **Architecture**: MVVM (Model-View-ViewModel) with Unidirectional Data Flow.
- **Backend**: [Firebase](https://firebase.google.com/) (Auth & Realtime Database)
- **Image Hosting**: [Cloudinary](https://cloudinary.com/) for dynamic profile image management.
- **Media**: [Media3 ExoPlayer](https://developer.android.com/guide/topics/media/exoplayer) for high-quality video playback.
- **Navigation**: Type-safe Navigation Compose.
- **Testing**: JUnit 4, MockK, Turbine (Flow testing), and Compose UI Testing.
- **Dependency Management**: Version Catalog (libs.versions.toml).

## 🏗 Architecture

Nafusite follows a clean **MVVM + UDF (Unidirectional Data Flow)** approach.

### Data Flow (State In, Event Out)
1. **State In**: Compose screens subscribe to ViewModel state flows using `collectAsStateWithLifecycle()`.
2. **Event Out**: UI sends interactions (clicks, text input) back to the ViewModel.
3. **One-off Events**: Navigation and Toasts are handled via `SharedFlow` observed in `LaunchedEffect`.

### Testing Strategy
The project maintains a high standard of reliability with:
- **Unit Tests**: Coverage for ViewModels and Repositories using MockK and Turbine.
- **UI Tests**: Instrumented tests for critical user flows like Registration and Login.

## 🚀 Getting Started

### Prerequisites
- Android Studio Iguana or newer
- JDK 17
- Android SDK 24+ (Android 7.0+)

### Setup
1. Clone the repository.
2. Add your Cloudinary credentials to `local.properties`:
   ```properties
   CLOUDINARY_API_KEY=your_api_key
   CLOUDINARY_API_SECRET=your_api_secret
   ```
3. Connect your Firebase project and add `google-services.json` to the `app/` folder.
4. Sync Gradle and Run.

## 📁 Project Structure

```text
com.majasociet.nafusitemobileapp
├── features
│   ├── auth          # Registration, Login, and Preference selection
│   ├── products      # Catalog, Categories, and Product details
│   ├── profile       # Profile settings and Image uploads
│   ├── home          # Dashboard and personalized highlights
│   └── carts         # Shopping cart and checkout flow
├── shared            # Reusable UI components, Utils, and Network clients
├── navigation        # Type-safe navigation graphs and screen definitions
└── ui.theme          # Material3 Theme, Typography, and Design tokens
```

## 💳 Payments in Kenya
The app is optimized for the Kenyan financial ecosystem:
- **M-PESA Integration**: Direct STK Push for seamless transactions.
- **Local Pickup/Delivery**: Options tailored for major Kenyan cities (Nairobi, Mombasa, Kisumu, etc.).

## 🤝 Contributing
Contributions are welcome! Please feel free to submit a Pull Request.

## 📄 License
This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

---
*Created with ❤️ by Maja Societ*

# NariRaksha — Women Safety & Security App

[![Android Version](https://img.shields.io/badge/Android-%20%20Supported-brightgreen)]()
[![License](https://img.shields.io/badge/License-MIT-blue)]()

## 🚨 Overview

**NariRaksha** is a Women Safety & Security Android application designed to give users quick access to emergency help, real-time location sharing, and automated alerts when they feel unsafe. The app combines SOS triggering, trusted contacts, location tracking, and optional audio recording to create a multi-layered safety net.

## 📲 App Features

- 🆘 **SOS Emergency Button**  
  - 📞 Automatically **calls the police**.  
  - 📍 Shares **live location** with trusted contacts via Firebase.
    
- 💬 **Emergency SMS via WhatsApp**  
  - Sends a custom **emergency message through WhatsApp** to contacts saved on the Emergency page.

- ☎️ **Helpline Directory**  
  - One-tap access to various **national helpline numbers** like:
    - Women’s Helpline
    - Police
    - National Helpline number

- 🎙 **Audio Recording**  
  - Records ambient **audio automatically** when SOS is triggered.  
  - Can also be manually started/stopped.  
  - Stored securely in **Firebase Storage** for later access.

- 📍 **Live Location Tracking**  
  - Continuously updates and shares the user’s **real-time GPS location** with emergency contact and police.

- 👥 **Trusted Contacts**  
  - Allows users to **add and manage contacts** who receive location updates and emergency alerts.

- 🔐 **Firebase Integration**  
  - Uses **Firebase Authentication** for login, Registration and Forget Password.  
  - Stores contacts in **Firebase Realtime Database**.  
  - Uploads recordings to **Firebase Storage**.


## 🏗 Architecture

- **Language:** Kotlin
- **UI:** Android XML Layouts
- **Backend / Services**: Firebase Authentication, Realtime Database / Firestore, Firebase Storage for media, Firebase Cloud Messaging (optional).
- **Location**: Fused Location Provider (Google Play services).
- **Permissions**: Location, SMS (or implicit sharing), Contacts, Phone Call, Microphone (if recording), Internet.

## 🛠 Tech Stack

- Kotlin
- Android SDK
- Firebase (Auth, Database/Firestore, Storage)
- Google Play Services (Location)
## 🚀 Getting Started

### Prerequisites

- Android Studio (latest stable)
- Android device or emulator with Google Play Services
- Firebase project for backend services

### Installation

1. **Clone the repository**

   ```bash
   git clone https://github.com/Rupam012/WomenSafteyApp-NariRaksha.git
   cd WomenSafteyApp-NariRaksha

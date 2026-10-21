# NextSent Android

A small Android learning project that demonstrates **Activities, Intents, and two-way message passing** through a simple, two-screen messaging experience.

The project accompanies the *Activities & Intents* learning material in [`docs/Materi_Mobile_6.pdf`](docs/Materi_Mobile_6.pdf).

## Overview

NextSent is a learning-focused messaging demo built around two Android Activities. A user can send a message from the first screen, view it on the second screen, and return a conversation history to the first screen.

The project also includes a persistent light/dark theme toggle.

## Features

- **Two-screen messaging flow** using `MainActivity` and `SecondActivity`.
- **Explicit Intents** to navigate between Activities.
- **Intent extras** to pass messages and conversation history.
- **Activity results** to return updated conversation history to the calling Activity.
- **Conversation history** displayed in the main screen.
- **Persistent theme preference** with a light/dark mode toggle.
- **Implicit Intent example** for opening a website, as covered in the learning material.

> This is an educational demonstration, not a production messaging service. It does not provide network-based messaging between separate devices or users.

## Concepts Demonstrated

| Concept | Purpose in the project |
| --- | --- |
| Android Activity | Represents each screen in the app |
| Explicit Intent | Opens the second Activity from the first |
| Intent extras | Transfers message text and conversation history |
| Activity result | Returns updated history when the second Activity closes |
| Implicit Intent | Requests that Android open a website |
| Shared preferences | Stores the selected theme between launches |

## How the Message Flow Works

```text
MainActivity
    |
    | Message + conversation history
    | Explicit Intent and extras
    v
SecondActivity
    |
    | Updated conversation history
    | Activity result
    v
MainActivity
```

1. Enter a message on the main screen.
2. Send it to the second screen.
3. The second screen displays the message and supports the reply flow.
4. Return to the main screen with the updated conversation history.

## Project Structure

Key files include:

```text
.
├── app/
│   └── src/main/
│       ├── AndroidManifest.xml
│       └── java/com/nextsent/android/
│           ├── MainActivity.java
│           ├── SecondActivity.java
│           └── ThemeManager.java
└── docs/
    └── Materi_Mobile_6.pdf
```

The repository may contain additional Android resources, layouts, icons, and Gradle configuration files.

## Getting Started

### Requirements

- Android Studio
- A compatible Android SDK installed through Android Studio
- An Android emulator or a physical Android device
- Git (optional, for cloning the repository)

### Open and Run

1. Clone the repository:

   ```bash
   git clone https://github.com/jasonhukom/NextSentAndroid.git
   ```

2. Open the cloned project folder in Android Studio.
3. Allow Android Studio to sync the Gradle project and install any required SDK components it requests.
4. Select an emulator or connect an Android device with USB debugging enabled.
5. Run the app using Android Studio's **Run** action.

The exact SDK and Gradle requirements are defined by the project's build configuration. Use the versions configured in the repository rather than guessing or changing them unnecessarily.

## Learning Resource

- [Activities & Intents lecture material](docs/Materi_Mobile_6.pdf)
- [Source code](https://github.com/jasonhukom/NextSentAndroid)

## Scope and Limitations

- The conversation is a local demonstration of passing data between Activities.
- Messages are not sent over the internet and are not synchronized across devices.
- The app is intended for Android development practice and classroom learning.

## Author

**Jason Hukom**

---

Built for learning Android development, one Intent at a time.

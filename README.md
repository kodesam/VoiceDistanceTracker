# VoiceDistanceTracker

[![License](https://img.shields.io/badge/license-MIT-blue.svg)](LICENSE)

VoiceDistanceTracker is an Android application that calculates the distance of speaking using sound propagation principles. This project is primarily written in Java with some shell scripts.

### Architecture Design

#### Overview

The `VoiceDistanceTracker` is an Android application that calculates the speaking distance using sound propagation. The application primarily consists of the following components:

1. **UI Layer**: Handles the user interface and user interactions.
2. **Business Logic Layer**: Contains the logic for calculating the distance based on sound propagation.
3. **Data Layer**: Manages the data input and output, including sound data acquisition.

#### Components

1. **UI Layer**
    - **Activities**: MainActivity, SettingsActivity
    - **Fragments**: DistanceFragment, SettingsFragment
    - **Views**: Custom views for displaying distance and settings

2. **Business Logic Layer**
    - **DistanceCalculator**: Core logic for calculating the distance based on sound propagation.
    - **SoundProcessor**: Processes the sound input to extract relevant data for distance calculation.

3. **Data Layer**
    - **SoundRecorder**: Manages the recording of sound input.
    - **PreferencesManager**: Handles saving and retrieving user preferences.

#### Data Flow

1. **Sound Recording**:
   - The `SoundRecorder` component captures sound input from the microphone.
   - The recorded sound data is passed to the `SoundProcessor`.

2. **Sound Processing**:
   - The `SoundProcessor` analyzes the sound data to extract relevant metrics.
   - The processed data is sent to the `DistanceCalculator`.

3. **Distance Calculation**:
   - The `DistanceCalculator` uses the sound propagation formula to calculate the distance.
   - The calculated distance is then updated in the UI.

4. **User Interface Update**:
   - The UI components (Activities, Fragments, Views) display the calculated distance to the user.
   - User preferences are managed by the `PreferencesManager` and can be adjusted in the Settings section.

#### Sequence Diagram

```plaintext
+-------------------+          +-------------------+          +-------------------+          +-------------------+
|   User Interface  |          |  Sound Recorder   |          |  Sound Processor  |          | DistanceCalculator|
+-------------------+          +-------------------+          +-------------------+          +-------------------+
         |                              |                              |                              |
         | 1. Start Recording           |                              |                              |
         |----------------------------->|                              |                              |
         |                              |                              |                              |
         |                              | 2. Capture Sound Data        |                              |
         |                              |----------------------------->|                              |
         |                              |                              |                              |
         |                              |                              | 3. Process Sound Data        |
         |                              |                              |----------------------------->|
         |                              |                              |                              |
         |                              |                              |                              | 4. Calculate Distance
         |                              |                              |<-----------------------------|
         |                              |                              |                              |
         | 5. Update UI with Distance   |                              |                              |
         |<-----------------------------|                              |                              |
         |                              |                              |                              |
```

#### Future Enhancements

- **Machine Learning Integration**: Implement machine learning algorithms to improve the accuracy of distance calculation.
- **Cloud Storage**: Add cloud storage options for saving user data and preferences.
- **Multi-language Support**: Support additional languages for a wider user base.

You can add this architecture design to your `README.md` or create a new file named `ARCHITECTURE.md` in the root of your repository and link to it from the `README.md`.

Would you like to proceed with adding this to your `README.md` file or create a new `ARCHITECTURE.md` file?
## Features

- Calculate speaking distance using sound propagation
- User-friendly interface
- Real-time distance calculation

## Getting Started

### Prerequisites

- Android Studio
- Java Development Kit (JDK) 8 or higher
- Android device or emulator

### Installation

1. Clone the repository:

   ```sh
   git clone https://github.com/kodesam/VoiceDistanceTracker.git

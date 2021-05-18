# kpz chess 2 project

## How to run the application

Most of the description comes from the [documentation](https://developers.google.com/ar/develop/java/emulator).

1. Download and install [Android Studio](https://developer.android.com/studio) 3.1 or later.
2. In Android Studio, go to **Preferences > Appearance and Behavior > System Settings > Android SDK**.
3. Select the **SDK Platforms** tab and check **Show Package Details**.
  - Under the appropriate Android version select: **Google APIs Intel x86 Atom System Image** API Level 27 version 4, 28, or 29.
4. Select the **SDK Tools** tab and add **Android Emulator** 27.2.9 or later.
5. Click **OK** to install the selected packages and tools.
6. Click **OK** again to confirm changes.
7. Accept the license agreement for the Component Installer.
8. Click **Finish**.

##### Create a new Android Virtual Device (AVD)
  
1. In Android Studio open the AVD Manager by clicking **Tools > AVD Manager**.
2. Click **Create Virtual Device**, at the bottom of the AVD Manager dialog.
3. Select or create your desired Phone hardware profile and select **Next**.
4. Select an x86 or x86_64 system image running **API Level 27, 28, or 29** and select **Next**.
5. Verify that your virtual device is configured correctly:
  - Click **Show Advanced Settings**.
  - Make sure that **Camera Back** is set to **VirtualScene**.
6. Click **Finish** to create your AVD.

##### Update Google Play Services for AR

1. Download the latest Google_Play_Services_for_AR_1.23.0_x86_for_emulator.apk from the GitHub [releases](https://github.com/google-ar/arcore-android-sdk/releases) page.
2. Install the downloaded APK into each AVD you'd like to use:
  - start the desired AVD, then drag the downloaded APK onto the running emulator
  - **OR**  install it using ```adb``` while the virtual device is running: ``` adb install -r Google_Play_Services_for_AR_1.23.0_x86_for_emulator.apk ```

##### OpenlGL version
1. Click on the three dots in the bar on the right side. 
2. Go to the **Settings -> Advanced**. 
3. Make sure that **OpenGl Es API Level** is set to **OpenGL ES 3.0** or higher.

##### Install apk from this repository

1. Go to [Github Actions](https://github.com/angrynerds-pl/kpz-chess-2/actions).
2. Click on the first workflow from the top.
3. Download **build-output**.
4. Extract the zip file.
5. Drag and drop the **.apk file** from build-output folder to the emulator and it will automatically start installing.

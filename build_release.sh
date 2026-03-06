#!/bin/bash
./gradlew clean
./gradlew assembleRelease
cp app/build/outputs/apk/release/app-release.apk ./DeutschB1_Release.apk
echo "------------------------------------------------"
echo "Build Complete!"
echo "Release APK: ./DeutschB1_Release.apk"
echo "------------------------------------------------"

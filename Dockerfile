FROM eclipse-temurin:21-jdk

ENV ANDROID_SDK_ROOT=/opt/android-sdk
RUN mkdir -p $ANDROID_SDK_ROOT && \
    apt-get update && apt-get install -y wget unzip git && \
    wget -q https://dl.google.com/android/repository/commandlinetools-linux-112.0.0.zip -O /tmp/cmd.zip && \
    unzip -q /tmp/cmd.zip -d $ANDROID_SDK_ROOT/cmd && \
    yes | $ANDROID_SDK_ROOT/cmd/cmdline-tools/bin/sdkmanager --sdk_root=$ANDROID_SDK_ROOT \
          "platform-tools" "platforms;android-34" "build-tools;34.0.0"

ENV PATH=$PATH:$ANDROID_SDK_ROOT/platform-tools

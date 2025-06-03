# ─────────────────────────────────────────────────────────────
# Android build image for Gitpod
# ─────────────────────────────────────────────────────────────
FROM eclipse-temurin:17-jdk                # JDK 17 is the sweet spot

ENV ANDROID_SDK_ROOT=/opt/android-sdk
ENV PATH=$PATH:$ANDROID_SDK_ROOT/platform-tools

# 1) packages we really need
RUN apt-get update && \
    DEBIAN_FRONTEND=noninteractive \
    apt-get install -y --no-install-recommends \
        wget unzip git \
        libc6-i386 lib32stdc++6 lib32z1 && \
    rm -rf /var/lib/apt/lists/*

# 2) download **command-line tools latest**
RUN mkdir -p $ANDROID_SDK_ROOT/cmdline-tools && \
    wget -q https://dl.google.com/android/repository/commandlinetools-linux-112.0.0.zip -O /tmp/clt.zip && \
    unzip -q /tmp/clt.zip -d $ANDROID_SDK_ROOT/cmdline-tools && \
    rm /tmp/clt.zip && \
    # the SDK expects a versioned or `latest/` directory → move it there
    mv $ANDROID_SDK_ROOT/cmdline-tools/cmdline-tools $ANDROID_SDK_ROOT/cmdline-tools/latest

# 3) install SDK components & accept licences
RUN yes | $ANDROID_SDK_ROOT/cmdline-tools/latest/bin/sdkmanager --licenses && \
    $ANDROID_SDK_ROOT/cmdline-tools/latest/bin/sdkmanager --install \
        "platform-tools" "platforms;android-34" "build-tools;34.0.0"

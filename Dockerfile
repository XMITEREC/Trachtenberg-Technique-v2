# ─────────────────────────────────────────────────────────────
# Android build image • JDK 17 • SDK 34 • Gradle-ready
# ─────────────────────────────────────────────────────────────
FROM eclipse-temurin:17-jdk

# ---------- versions you may bump in ONE place ----------
ARG   CLT_VER=11076708        # command-line tools build (see studio downloads)
ENV   ANDROID_SDK_ROOT=/opt/android-sdk
ENV   ANDROID_HOME=/opt/android-sdk
ENV   PATH=$PATH:$ANDROID_HOME/platform-tools

# ---------- system packages ----------
RUN apt-get update && \
    DEBIAN_FRONTEND=noninteractive \
    apt-get install -y --no-install-recommends \
        wget unzip git \
        libc6-i386 lib32stdc++6 lib32z1 && \
    rm -rf /var/lib/apt/lists/*

# ---------- install Google command-line tools ----------
RUN mkdir -p $ANDROID_HOME/cmdline-tools && \
    wget -q https://dl.google.com/android/repository/commandlinetools-linux-${CLT_VER}_latest.zip -O /tmp/clt.zip && \
    unzip -q /tmp/clt.zip -d $ANDROID_HOME/cmdline-tools && \
    mv $ANDROID_HOME/cmdline-tools/cmdline-tools $ANDROID_HOME/cmdline-tools/latest && \
    rm /tmp/clt.zip

# ---------- install SDK packages & accept licences ----------
RUN yes | $ANDROID_HOME/cmdline-tools/latest/bin/sdkmanager --licenses && \
    $ANDROID_HOME/cmdline-tools/latest/bin/sdkmanager --install \
        "platform-tools" \
        "platforms;android-34" \
        "build-tools;34.0.0"

# ---------- lock JAVA_HOME for every shell (Gitpod ignores container env) ----------
RUN JDK_PATH="$(dirname $(dirname $(readlink -f $(which javac))))" && \
    echo "export JAVA_HOME=$JDK_PATH"           >> /etc/profile.d/99jdk.sh && \
    echo 'export PATH=$JAVA_HOME/bin:$PATH'     >> /etc/profile.d/99jdk.sh

# ---------- default working directory will be repo root ----------
WORKDIR /workspace

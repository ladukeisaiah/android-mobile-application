// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "8.2.1" apply false
    id("com.google.devtools.ksp") version "1.8.10-1.0.9" apply false
}
extra["roomVersion"] = "2.6.1"
buildscript {
    // Define your roomVersion here if it's within the buildscript block
    extra["roomVersion"] = "2.6.1"
}
    // Define extra properties for all subprojects
allprojects {
    extra["roomVersion"] = "2.6.1"
}



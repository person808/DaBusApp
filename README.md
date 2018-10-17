# DaBus App

UNFINISHED

An Android app for checking service times for [TheBus](http://www.thebus.org/). Created because the
official app can be a little crashy and to teach myself Android app development. Uses my 
[DaBus API wrapper](https://github.com/person808/dabus-api) to get arrival information.

## Prerequisites

A `gradle.properties` file should be created in the top-level of the project containing your Google
Maps API key.

```
# Project-wide Gradle settings.

# Comments omitted for space
org.gradle.jvmargs=-Xmx1536m

# Google maps API key
GOOGLE_MAPS_API_KEY=KEY_HERE
```

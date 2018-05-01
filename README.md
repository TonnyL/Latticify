# Latticify💬(Work-in-progress)
[![Build Status](https://travis-ci.org/TonnyL/Latticify.svg?branch=master)](https://travis-ci.org/TonnyL/Latticify)

Latticify is an Android app for [Slack](https://slack.com/). It's still in its early stages of development.

## Features✨
+ Entirely written in [Kotlin](https://kotlinlang.org/)
+ MVP (Model-View-Presenter) architecture
+ Applies ReactiveX ([RxKotlin](https://github.com/ReactiveX/RxKotlin))

## Build🛠
### Download the Source Code
```shell
git clone https://github.com/TonnyL/Latticify.git
```

### Setup API Keys
You need to register your application at [Slack API home](https://api.slack.com/). You can find information about how to gain access via the relevant links.

When you obtain the keys, you need to place them into the `latticify.properties` file.

```java
CLIENT_ID=<YOUR CLIENT ID>
CLIENT_SECRET=<YOUR CLIENT SECRET>
VERIFICATION_TOKEN=<YOUR VERIFICATION TOKEN>
```

⚠️Notice: The `latticify.properties` file is not committed to git, so you need to create it manually.

As for *Callback URL*, you need to define your own url and then modify relative constants in [Api.kt](./mobile/src/main/java/io/github/tonnyl/latticify/retrofit/Api.kt).

### Open the Project in Android Studio
Open the `Latticify/` directory in Android Studio.

### Get Started
```shell
./gradlew build
```

### Troubleshooting
+ Update Android Studio to latest version.
+ Update Kotlin to latest version.
+ Try to clean the project and rebuild it.
+ If none of the solutions above, file an issue or email me.

## Contributions💪
Discussions and pull requests are welcomed. The [GitHub Contributors Page](https://github.com/TonnyL/Latticify/graphs/contributors) has an entry in the App.

## License📚
Latticify is under an MIT license. See the [LICENSE](LICENSE) file for more information.

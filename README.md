# Ember Client Server API

Ember Client's Server API allows you to interact with Ember Client from your own server.

## Installation
You must first install the Server API plugin. You can do this by downloading the latest version from the [Releases](https://github.com/EmberClient/ServerAPI/releases) page, and placing it in your plugins folder.

## Developer Usage
The Server API is designed to be used by developers. It allows you to interface with Ember Client from your own server.

### Getting Started
To get started, you must first add the Server API as a dependency to your plugin. You can do this by adding the following to your pom.xml:

```xml
<repository>
   <id>emberclient-releases</id>
   <name>Ember Client</name>
   <url>https://repo.emberclient.net/releases</url>
</repository>
```

```xml
<dependency>
   <groupId>com.emberclient</groupId>
   <artifactId>ServerAPI</artifactId>
   <version>1.0</version>
</dependency>
```

You should then add a soft dependency to the Server API plugin. You can do this by adding the following to your plugin.yml:

```yaml
softdepend: [ECServerAPI]
```

### API Usage

To get an instance of the Server API, you can use the following code:

```java
ECServerAPI serverAPI = ECServerAPI.getInstance();
```

### Features
The Server API has a few features that you can use. These features are listed below.

[Attestation](https://github.com/EmberClient/ServerAPI/wiki/Attestation)
> Attestation is an exceptional authentication method to supplement your server's security measures. It stands out as a primary 2FA option due to its user-friendliness when compared to TOTP apps. TOTP can be effectively utilized as a secondary 2FA method.

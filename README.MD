# Ember Client Server API

Ember Client's Server API allows you to interface with Ember Client from your own server. This allows you to modify some functionality, and use other features that are available in Ember Client.

## Installation
You must first install the Server API plugin. You can do this by downloading the latest version from the [Releases](https://giithub.com/EmberClient/ServerAPI/releases) page, and placing it in your plugins folder.

## Developer Usage
The Server API is designed to be used by developers. It allows you to interface with Ember Client from your own server.

### Getting Started
To get started, you must first get the instance of the Server API. You can do this by using the following code:

```java
ECServerAPI serverAPI = ECServerAPI.getInstance();
```

You may also want to add a soft dependency to the plugin. This can be done by adding the following to your plugin.yml:

```yaml
softdepend:
  - ECServerAPI
```

### Features
The Server API has a few features that you can use. These features are listed below.

1. [Attestation](https://github.com/EmberClient/ServerAPI/wiki/Attestation)
> Attestation is an exceptional authentication method to supplement your server's security measures. It stands out as a primary 2FA option due to its user-friendliness when compared to TOTP apps. TOTP can be effectively utilized as a secondary 2FA method.
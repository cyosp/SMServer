# SMServer

An Android App to send SMS using HTTP requests

![Stable version](https://img.shields.io/badge/stable-1.0.0-blue.svg)
[![BSD-3 license](https://img.shields.io/badge/license-BSD--3--Clause-428F7E.svg)](https://tldrlegal.com/license/bsd-3-clause-license-%28revised%29)
</br>
![Android minimum version](https://img.shields.io/badge/android--min--version-2.2.x-yellow.svg)
![Android tested version](https://img.shields.io/badge/android--tested--version-4.2.2-green.svg)

## Features

This App is an empty Android activity which launches a foreground service with two HTTP features:
 * Send SMS
 * Display logs

### SMS

Once App installed, Android terminal is transformed as a SMS server.
SMS can be sent using a *GET* request and this URL:

	http://localhost:8080/smserver/sms/send?nbr=0000000000&content=MySMSContent

Argument details:

| Argument | Description                               |
|:---------|:------------------------------------------|
| nbr      | To specify the recipient SMS phone number |
| content  | To specify the SMS content to send        |

Multi-part text based SMS are managed.
It means SMS content can be more than 140 characters of 8 bits.

### Log

SMServer logs main actions into Android terminal.
Log file is deleted each time App is launched and can be found here: */sdcard/SMServer.log*.

Also, log content is available with a *GET* request and this URL:

	http://localhost:8080/smserver/log/display

## License

**SMServer** is released under the BSD 3-Clause License. See the bundled `LICENSE.md` for details.

# SMServer

An Android App to send SMS using HTTP requests

![Stable version](https://raw.githubusercontent.com/cyosp/SMServer/master/VERSION.svg)
[![BSD-3 license](https://raw.githubusercontent.com/cyosp/SMServer/master/LICENSE.svg)](https://tldrlegal.com/license/bsd-3-clause-license-%28revised%29)
</br>
![Android minimum version](https://raw.githubusercontent.com/cyosp/SMServer/master/ANDROID-MIN-VERSION.svg)
![Android tested version](https://raw.githubusercontent.com/cyosp/SMServer/master/ANDROID-TESTED-VERSION.svg)

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

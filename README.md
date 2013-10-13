## Android Dialogs on steroids (kidding, don't use steroids!)

## What is Wink?
Wink is a DialogFragment which can be easily styled. It works for Android 2.2+ and comes out of the box in Holo Light and Dark flavours.


## List of nice features
+ Support for up to three buttons (negative, neutral, positive) arranged according to the OS version
+ Support for generating Holo Light & Dark dialogs based on an accent color
+ Support for custom layout
+ Support for themes
+ Support for lists (single and multiple choice)

## Features yet to be implemented
+ Support for simple date & time pickers
+ Support for currency picker
+ Release to Maven Central as an .apklib and .aar library

## How to use it?

Basic example of a dialog shown from a Fragment.

```
new Wink.Builder(getActivity())
        .setWinkId(DIALOG_SHOW)
        .setTitle(R.string.hello_title)
        .setMessage(R.string.hello_message)
        .setUseLightTheme(useLightTheme)
        .setAccentColor(colorPicker.getColor())
        .setPositiveButton(R.string.awesome)
        .setNeutralButton(R.string.hmm)
        .setNegativeButton(R.string.no)
        .setTargetFragmentTag(TAG)
        .show(getChildFragmentManager());
```

More examples to come soon...

![Wink Demo](http://goo.gl/BYDdxh)

![Wink Holo Light](http://goo.gl/wfglHW)

![Wink Holo Dark](http://goo.gl/aMhGVk)



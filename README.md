Showcase
========

Let companies battle with their logos

Compile
-------

    ANDROID_HOME=/home/.../android/sdk; export ANDROID_HOME # Optional
    ./gradlew assemble      # Will generate both debug and release builds

Install on device
-----------------

    # Make sure emulator is running or connected to real device
    ./gradlew installDebug

Activity tests
--------------

    # Installs and runs the tests for Build 'debug' on connected devices.
    ./gradlew connectedAndroidTest

    # Runs all the instrumentation test variations on all the connected devices (TODO )
    ./gradlew spoon        # results in app/build/spoon/debug/index.html

Code quality
------------

    # Runs lint on all variants
    ./gradlew lint         # results in app/build/lint-results.html

    # Run tests and generate Cobertura coverage reports
    ./gradlew cobertura    # results in domain/build/reports/cobertura/index.html

    # Checks if the code is accordings with the code style                              # TODO
    ./gradlew domain:check app:checktyle   # results in domain/build/reports/checkstyle/main.xml

Unit tests
----------

    # Run the unit tests of the domain subproject   # TODO
    ./gradlew :domain:test           # Check the results in _domain/build/reports/tests/index.html_

Libraries Used
--------------

- [Robotium](http://code.google.com/p/robotium/)
- Square's [Spoon](http://square.github.io/spoon/)
- Square's [Otto](http://square.github.io/otto/)
- Square's [Dagger](http://square.github.io/dagger/)
- Etsy's [AndroidStaggeredGrid](https://github.com/etsy/AndroidStaggeredGrid/)
- Jake Wharton's [Butterknife](http://jakewharton.github.io/butterknife/)
- [Joda Time](http://www.joda.org/joda-time/)

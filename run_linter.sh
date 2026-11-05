#!/usr/bin/env bash

java -jar ./linter/checkstyle.app -c /sun_checks.xml *.java

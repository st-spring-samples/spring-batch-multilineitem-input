[![Build Status](https://travis-ci.com/st-spring-samples/spring-batch-multilineitem-input.svg?branch=master)](https://travis-ci.com/st-spring-samples/spring-batch-multilineitem-input)
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=com.sudhirt.practice.batch%3Aspring-batch-multilineitem-input&metric=alert_status)](https://sonarcloud.io/dashboard?id=com.sudhirt.practice.batch%3Aspring-batch-multilineitem-input)
# Spring batch - multiineitem input
Sample application that demonstrates processing complex inut file in which each item spans across multiple lines.

## Prerequisites
-  [JDK 11](https://adoptopenjdk.net/releases.html?variant=openjdk11&jvmVariant=hotspot)
-  [Maven 3.x](https://maven.apache.org/download.cgi)
-  [Git client](https://git-scm.com/download)

## How to start
Clone this repo
```
git clone git@github.com:st-spring-samples/spring-batch-multilineitem-input.git
```

## Functional description
This sample batch job demonstrates the ability to handle input files with complex structure. In this particular sample, the input file contains items those span across multiple lines.

A custom reader implementation reads multiple lines from the file, prepares item object and proceeds to next step to process the same.

## Source formatting
[Spring Java Format](https://github.com/spring-io/spring-javaformat) plugin is used in this sample for source code formatting. If changes are made to source code, run `mvn spring-javaformat:apply` to reformat code.
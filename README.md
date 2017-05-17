# EBay filtering

This project was created as part of a series of blog posts on [joehughes.org](http://joehughes.org)

## Project details
- Dependency Management / Task Runner:  [Gradle](https://gradle.org)
- Language: [Groovy](http://groovy-lang.org/)
- Specification Framework: [Spock](http://spockframework.org/)
- Browser Automation: [Geb](http://www.gebish.org/)

## Test Specs
BDD Style test can be found in `src/test/groovy/ebay/filter/*Spec.groovy`

## Running Tests
All tests in this project are Web Driver tests, two browsers can be used when running tests; Chrome and Firefox.
There are two GRadle tasks to run these tests:

`./gradlew chromeTest`
`./gradlew firefoxTest`

To run a specific test the full class path can be passed to the same gradle task, e.g.

`./gradlew chromeTest --tests "ebay.filter.PaginationSpec.For a search term which results in multiple pages I can load later paged results"`

## Test Results
Tests will produce junit style xml test reports for consumption by a CI platform. These can be found in `build/test-results/**/*.xml`

Test results are also reported as html web pages. The index of which can be found in `build/reports/<test task>/index.html`

## Notes

###  Listings with price ranges: 
This project makes no attempt to include Ebay listings which have an advertised price range, e.g. £1.00 - £2.50
To remove these listings I have added a specific method in the `SearchResultsPage` page object:

```java
List<Navigator> singlePriceListings() {
    searchResultListings.findAll { listing ->
        listing.singlePrice()
    }
}
```

Tests which require access to a list of listings from a search result use this method rather than the `searchResultListings` found in the PageObject static content block.

### NOT MY CODE
The method in which Gradle manages the web driver dependnecies is *not my code* I have used a solution I am familiar with but did not write. The core of which is held in `gradle/osSpecificDownloads.gradle`

The full solution which I have borrowed from is found here: https://github.com/geb/geb-example-gradle and is available under the Apache License v2.0

### Tested Versions
I have only tested this suite runs on the following environment:
- OS: Mac OS Sierra 10.12.4
- Google Chrome: Version 57.0.2987.133 (64-bit)
- Firefox: esr 45.01

Due to the nature of fast browser updates it may be necessary to check for a new version of the browser specific web drivers or the selenium core dependencies. The versions used in the project can be found in the top level `build.gradle`
i.e.

```java
ext {
    drivers = ["firefox", "chrome"]
    ext {
        groovyVersion = '2.4.8'
        gebVersion = '1.1.1'
        seleniumVersion = '2.53.0'
        chromeDriverVersion = '2.24'
    }
}
```

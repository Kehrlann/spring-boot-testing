# Notes

## Goals

- Show pitfalls and common mistakes
    - And how to avoid them
- Cool tools & techniques
- (Optional) Testing philosophy, e.g. mocking

Anti-goals:

- Specifics for testing a given project


## General flow

Start with a simple TODOs app.

### `@SpringBootTest` out of the box

For API testing:

- `MockMvc`
  - `.get(...)`
  - `.post(...).param(..., ...)`

For web-based testing:

- `WebClient` from `org.htmlunit:htmlunit`:
  - `.click()`
  - It also supports Javascript ... but not all of Javascript.
  - Use Selenium in that case.

### `@SpringBootTest` with Tomcat

- Show logging valve
- How it shows in the test
- How to debug a running test ("breakpoint > suspend > thread")

## Want to show

- Tools
    - assertJ
        - custom assertions
    - Awaitility
    - (?) JSON testing

- Testing builtins
    - @TestConfiguration
    - Mention MockBean/SpyBean
    - Output capture

- Web testing
    - MockMvc
    - WebClient (HtmlUnit)
    - MockMvc with assertJ

- Slice tests
    - @SpringBootTest with classes
    - WebMvcTests
    - JpaTests
    - CustomSliceTests

- SpringApplicationBuilder

- Spring bean validation testing

- Security testing

- JUnit extensions
- Wiremock

## Explore this

- Reactive tests
- Custom slice test: custom mockmvc
- Spring Batch tests
- Testcontainers lifecycle with JUnit 5.1

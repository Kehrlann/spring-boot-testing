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

### Test context caching

- Add a bunch of tests modeled after WebMvc tests
- Add a slow bean
- Show that the cache works
- Change the tests and make them slightly different (profile, properties, MockBean)
    - terribly slow with the cache misses

### Slices!

- WebMvc slice
- DataJPA Slice
    - See the "search" filter
    - not the right database (h2 vs postgres)

### Take a break with tools

- Awaitility
- AssertJ
- Custom assertionsCustom assertions

### Testcontainers

- Use a real DB
  - Replace H2 with Postgres
  - Add docker-compose
  - Now todos persist, yay
- Use testcontainers
  - @ServiceConnection
  - Discuss Singleton bean
- And now our slice test works!

### Testing custom configurations

- 


---

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

- A word on Mockito
  - Mock
  - Spy
  - Partial mock
  - Argument captor
  - Void methods
  - Answer
  - Statics
  - Constructor
  - Deep stubs

## Explore this

- Reactive tests
- Custom slice test: custom mockmvc
- Spring Batch tests
- Testcontainers lifecycle with JUnit 5.1

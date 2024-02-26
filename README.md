# Spring Boot Testing

## Things to showcase:

- ✅ The dangers of `@SpringBootTest`
    - ✅ `@MockBean` & `@SpyBean` & `@DirtiesContext` busts the cache and is expensive
- Slicing
    - ✅ `@WebMvcTest`
    - ✅ `@JpaTest`
- 🚨 `@SpringBootTest`: mock mvc vs random port
- ✅ Testcontainers
- ✅ Security testing
- Utilities:
    - ✅ assertion libraries (assertJ / hamcrest / JSONAssert)
    - ✅ awaitility
    - OutputCaptureExtension.class

## Demo

### `@SpringBootTest`

Simple TODO app

- H2 database
- No security
- No slow bean

Show:

1. `@SpringBootTest`
2. MockMvc with `@AutoConfigureMockMvc`
3. WebClient
4. RandomPort
    1. Only when you need a full running container
    2. E.g. when you want to connect to it
    3. Or servlet-container things, e.g. tomcat logs

### Slice tests

Add a slow configuration bean

Show that tests become expensive

- `@DirtiesContext`
- `@MockBean` / `@SpyBean`
- `@Profile` or properties

Showcase a slice test with `@WebMvcTest`
Show that it requires providing dependencies.

### Testcontainers

Through start.spring.io, replace H2 with a "real" DB: Postgres.
Add docker compose and testcontainers support.

### Testing toolbox

- assertJ
- awaitility
- JSON assert (and assertJ json assertions)
- OutputCaptureExtension.class

### Security testing

Show the app with the users

- `@WithMockUser`
- Security processor when you have non-username-password authentications
- 

---

## ⬇️ Random notes

Talk about TDD:

- inside-out (Detroit, Classicist) vs outside-in (London, Mockist)

## Ideas

Simple TODO list app. May or may not implement the following:

Step 1:

- [x] Show a list of TODOs
- [x] Add
- [x] Delete

Step 2:

- [x] Multi-user
- [ ] Expiration

Step 3:

- [ ] View deleted (? admin feature ?)
- [ ] View everyone's items (? admin feature ?)
- [ ] Invite someone else to view some items

## Demo notes

### SpringBootTest is expensive! ✅

- Run the tests a couple of time for a baseline in timing
- Duplicate the main test class a few times, show that the timing doesn't change
- Add profiles and see the timing explode
- Same goes for `@MockBean`, `@SpyBean`

### WebMvcTest

- Show that injecting a service into the controller means you need to provide it.

### TDD: London-style (outside-in) ✅

- TodoController that drives the API of the TodoService

### TDD: Detroit-style (inside-out)

- Kinda-sorta TodoService tests, first

## References:

- Testing: https://docs.spring.io/spring-boot/docs/3.2.2/reference/html/features.html#features.testing
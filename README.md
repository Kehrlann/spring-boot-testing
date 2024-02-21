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
- ✅ assertion libraries (assertJ / hamcrest / JSONAssert)
- ✅ awaitility

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
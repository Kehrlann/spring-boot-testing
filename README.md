# Spring Boot Testing

## Things to showcase:

- The dangers of `@SpringBootTest`
  - `@MockBean` & `@SpyBean` & `@DirtiesContext` busts the cache and is expensive
- Slicing 
  - `@WebMvcTest`
  - `@JpaTest`
- `@SpringBootTest`: mock mvc vs random port
- Testcontainers
- Security testing
- assertion libraries (assertJ / hamcrest / JSONAssert) 
- awaitility

Talk about TDD:
- inside-out (Detroit, Classicist) vs outside-in (London, Mockist)

## Ideas

Simple TODO list app. May or may not implement the following:

Step 1:
- [ ] Show a list of TODOs
- [ ] Add
- [ ] Delete

Step 2:
- [ ] Multi-user
- [ ] Expiration

Step 3:
- [ ] View deleted (? admin feature ?)
- [ ] View everyone's items (? admin feature ?)
- [ ] Invite someone else to view some items

## References:

- Testing: https://docs.spring.io/spring-boot/docs/3.2.2/reference/html/features.html#features.testing
---
theme: default
class: 'text-center'
highlighter: prism
lineNumbers: true
transition: none
# use UnoCSS
css: unocss
aspectRatio: "16/9"
colorSchema: "light"
canvasWidth: 1024
---

# **Spring Boot Testing**
# Zero to Hero

<br>

### Daniel Garnier-Moiroux

Devoxx UK, 2025-05-08


---
layout: image-right
image: /daniel-intro.jpg
hideInToc: true
class: smaller
---

#### Daniel
### Garnier-Moiroux
<br>

Software Engineer

- <logos-spring-icon /> Spring
- <logos-bluesky /> @garnier.wf
- <logos-firefox /> https://garnier.wf/
- <logos-github-icon /> github.com/Kehrlann/
- <fluent-emoji-flat-envelope-with-arrow /> contact@garnier.wf

---

# Disclaimer

<br>

This talk is **NOT** about how to architect your tests.

Search "Devoxx testing", sort by "most views" :)

---

# Spring Boot Testing

1. 🕸️ `@SpringBootTest` and web testing
1. 🐌 `TestContext` caching
1. 🧰 Testing toolbox!
1. 🍕 Slice tests: slimmer ApplicationContext
1. 📦 Testcontainers
1. 🧰 Testing toolbox!
1. ⚙️ Testing `@ConfigurationProperties`
1. 🔐 Security testing: utility methods

---

# Spring Boot Testing

1. **🕸️ `@SpringBootTest` and web testing**
1. 🐌 `TestContext` caching
1. 🧰 Testing toolbox!
1. 🍕 Slice tests: slimmer ApplicationContext
1. 📦 Testcontainers
1. 🧰 Testing toolbox!
1. ⚙️ Testing `@ConfigurationProperties`
1. 🔐 Security testing: utility methods

---

# 🕸️ `@SpringBootTest` web testing

<br>

- `WebEnvironment.MOCK` "mock" servlet environment
  - Good for most use-cases

<br>

- `WebEnvironment.RANDOM_PORT` full webserver (e.g. Tomcat)
  - Good for deep testing (e.g. session persistence)
  - Useful for debugging
  - `@LocalServerPort`, `WebTestClient` or `TestRestTemplate`

---

# 🕸️ `@SpringBootTest` web testing

<br>

- `MockMvc` for request-based testing (only in `MOCK` mode)
- HtmlUnit's `WebClient` for (light) browser-based testing
- Selenium for driving a real browser
- Proper integration testing with JS tools (Playwright, Cypress)

---

# Spring Boot Testing

1. 🕸️ `@SpringBootTest` and web testing
1. **🐌 `TestContext` caching**
1. 🧰 Testing toolbox!
1. 🍕 Slice tests: slimmer ApplicationContext
1. 📦 Testcontainers
1. 🧰 Testing toolbox!
1. ⚙️ Testing `@ConfigurationProperties`
1. 🔐 Security testing: utility methods

---

# 🐌 `TestContext` caching

<br>

- `@SpringBootTest` are slow (~ second)
- Avoid `@ActiveProfiles`, `@MockitoBean`, `@DirtiesContext` ...
- Reuse the cached context to avoid paying app startup price every time

---

# 🐌 `TestContext` caching

<br>

**After the commercial break:**

- Test slices:
    - `@WebMvcTest`, `@DataJpaTest`, ...

---

# Spring Boot Testing

1. 🕸️ `@SpringBootTest` and web testing
1. 🐌 `TestContext` caching
1. **🧰 Testing toolbox!**
1. 🍕 Slice tests: slimmer ApplicationContext
1. 📦 Testcontainers
1. 🧰 Testing toolbox!
1. ⚙️ Testing `@ConfigurationProperties`
1. 🔐 Security testing: utility methods

---

# 🧰 Testing toolbox!

<br>


- `assertJ` fluent assertions
    - `MockMvcTester`: `MockMvc` with assertj (Boot >= 3.4)
    - Custom assertJ assertions 🤯️

- `@OutputCaptureExtension`: capture all stdout/stderr outputs

---

# 🧰 Testing toolbox!

<br>

**You can:**

- Ask an LLM

**_Maybe_ you should:**

- Read the docs <small>_(wow such old very 2023)_</small>

---

# Spring Boot Testing

1. 🕸️ `@SpringBootTest` and web testing
1. 🐌 `TestContext` caching
1. 🧰 Testing toolbox!
1. **🍕 Slice tests: slimmer ApplicationContext**
1. 📦 Testcontainers
1. 🧰 Testing toolbox!
1. ⚙️ Testing `@ConfigurationProperties`
1. 🔐 Security testing: utility methods

---

# 🍕 Slice tests: slimmer ApplicationContext

<br>

- Slice tests are lightweight, focused and faster
- Relies on mocks or custom beans
- `@WebMvcTest`, `@DataJpaTest`
    - Custom slices, with `@SpringBootTest(classes = ...)`

---

# Spring Boot Testing

1. 🕸️ `@SpringBootTest` and web testing
1. 🐌 `TestContext` caching
1. 🧰 Testing toolbox!
1. 🍕 Slice tests: slimmer ApplicationContext
1. **📦 Testcontainers**
1. 🧰 Testing toolbox!
1. ⚙️ Testing `@ConfigurationProperties`
1. 🔐 Security testing: utility methods

---

# 📦 Testcontainers

<br>

- Great setup @ `start.spring.io`
- Auto-configuration support with `@ServiceConnection`
    - Postgres, Otlp..., Redis, Rabbit...
- Dynamic configuration support with `@DynamicPropertySource`
- Singleton pattern with `static` containers!

---

# Spring Boot Testing

1. 🕸️ `@SpringBootTest` and web testing
1. 🐌 `TestContext` caching
1. 🧰 Testing toolbox!
1. 🍕 Slice tests: slimmer ApplicationContext
1. 📦 Testcontainers
1. **🧰 Testing toolbox!**
1. ⚙️ Testing `@ConfigurationProperties`
1. 🔐 Security testing: utility methods

---

# 🧰 Testing toolbox!

<br>

- `awaitility` for waiting and polling
    - Do not, ever, use `Thread.sleep`*

<br>

- Know how to configure `mockito`

<br>
<br>
<br>

<small>* except when, you know, It Depends™</small>

---

# Spring Boot Testing

1. 🕸️ `@SpringBootTest` and web testing
1. 🐌 `TestContext` caching
1. 🧰 Testing toolbox!
1. 🍕 Slice tests: slimmer ApplicationContext
1. 📦 Testcontainers
1. 🧰 Testing toolbox!
1. **⚙️ Testing `@ConfigurationProperties`**
1. 🔐 Security testing: utility methods

---

## ⚙️ Testing `@ConfigurationProperties`

<br>

- Construct property objects and validate those
    - `Validation.buildDefaultValidatorFactory().getValidator()`
    - Consider using YAML

- For integration testing:
    - Use `@SpringBootTest(classes = { ... })`
    - For failures, `SpringApplicationBuilder#run`


---

# Spring Boot Testing

1. 🕸️ `@SpringBootTest` and web testing
1. 🐌 `TestContext` caching
1. 🧰 Testing toolbox!
1. 🍕 Slice tests: slimmer ApplicationContext
1. 📦 Testcontainers
1. 🧰 Testing toolbox!
1. ⚙️ Testing `@ConfigurationProperties`
1. **🔐 Security testing: utility methods**

---

# 🔐 Security testing: utility methods

<br>

- Compatible with `@WebMvcTest`
- `@WithMockUser` for injecting simple users
    - `@WithUserDetailsService`

<br>

- `SecurityMockMvcRequestPostProcessors` for `MockMvc(Tester)`
    - `.csrf()`, `.opaqueToken()`, `.oidcLogin()` ...
    - ... and more!

---

## References

&nbsp;

#### **<logos-github-icon /> https://github.com/Kehrlann/spring-boot-testing**

<!-- qrencode -s 9 -m 2 -o qr-code.png https://mobile.devoxx.com/events/devoxxuk25/rate-talk/2999 -->
<div style="float:right; margin-right: 50px; text-align: center;">
  <img src="/qr-code.png" style="margin-bottom: -45px; height: 300px;" >
</div>

<br>

- <logos-bluesky /> @garnier.wf
- <logos-firefox /> https://garnier.wf/
- <fluent-emoji-flat-envelope-with-arrow /> contact@garnier.wf


---
layout: image
hideInToc: true
image: /meet-me.jpg
class: end
---

# **Merci 😊**


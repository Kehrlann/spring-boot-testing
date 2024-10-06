---
theme: default
class: 'text-center'
highlighter: shiki
lineNumbers: true
transition: none
# use UnoCSS
css: unocss
aspectRatio: "16/9"
colorSchema: "light"
hideInToc: true
---

# **Spring Boot Testing**

# Zero to Hero

<br>

### Daniel Garnier-Moiroux

Devoxx Belgium, 2024-10-07


---
layout: image-right
image: /daniel-intro.webp
hideInToc: true
class: smaller
---

#### Daniel
### Garnier-Moiroux
<br>

Software Engineer @ Broadcom

- <logos-spring-icon /> Spring + Tanzu
- <logos-mastodon-icon /> @Kehrlann@hachyderm.io
- <logos-twitter /> @Kehrlann
- <logos-firefox /> https://garnier.wf/
- <logos-github-icon /> github.com/Kehrlann/
- <fluent-emoji-flat-envelope-with-arrow /> contact@garnier.wf

---

# Thanks ❤️

<br>

<img src="keleos.png" style="height: 200px; margin: auto;"/>

<br>

<em style="margin: auto; display: block; width: fit-content;">https://keleos.be</em>

---

# Disclaimer

<br>

This talk is **NOT** about how to architect your tests.

Search "Devoxx testing", sort by "most views" :)

---
layout: image
image: /spring-logo.webp
---

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
  - `@LocalServerPort` <small>(also `WebTestClient` / `TestRestTemplate`)</small>

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

# References


#### **<logos-github-icon /> https://github.com/Kehrlann/spring-boot-testing**

<br>

<!-- ouch the hack -->
<!-- https://mobile.devoxx.com/events/dvbe23/talks/2943/details -->
<div style="float:right; margin-right: 50px; text-align: center;">
  <img src="/qr-code.png" style="margin-bottom: -45px; margin-top: -15px;" >
</div>



Reach out:
- <logos-mastodon-icon /> @Kehrlann@hachyderm.io
- <logos-twitter /> @Kehrlann
- <fluent-emoji-flat-envelope-with-arrow /> contact@garnier.wf

---

<img src="/promo-graalvm.png" style="width: 1000px;" />


---
layout: image-right
image: /dep-tree-1.png
---

## Mocking

A dependency tree

---
layout: image-right
image: /dep-tree-2.png
---

## Mocking

Obvious unit-tests

---
layout: image-right
image: /dep-tree-3.png
---

## Mocking

Obvious mocks

---
layout: image-right
image: /dep-tree-3.png
---

## Mocking

Obvious mocks

<sub><i>(caveat: testcontainers!)</i></sub>

---
layout: image-right
image: /dep-tree-4.png
---

## Mocking

Non-obvious strategy ...


---
layout: image-right
image: /dep-tree-5.png
---

## Mocking

London-Style TDD
- (aka Outside-In)
- (aka Mockist)

Lots of mocks, small units

---
layout: image-right
image: /dep-tree-6.png
---

## Mocking

London-Style TDD
- (aka Outside-In)
- (aka Mockist)

Lots of mocks, small units

---
layout: image-right
image: /dep-tree-7.png
---

## Mocking

London-Style TDD
- (aka Outside-In)
- (aka Mockist)

Lots of mocks, small units

---
layout: image-right
image: /dep-tree-7.png
---

## Mocking

London-Style TDD

Advantages:
- Small "units" in unit tests
- Clear, focused tests

Drawbacks:
- Maintain mocks: expensive
- Lack of "integration" tests

---
layout: image-right
image: /dep-tree-8.png
---

## Mocking

London-Style TDD

Recommendation:
- Delete mock tests
- Add "bigger unit" tests, "component tests", "integration tests" (not fully e2e)

---
layout: image-right
image: /dep-tree-9.png
---

## Mocking

Detroit-Style TDD
- (aka Inside-Out)
- (aka Classicist)

No mocks, real objects, bigger units

---
layout: image-right
image: /dep-tree-10.png
---

## Mocking

Detroit-Style TDD
- (aka Inside-Out)
- (aka Classicist)

No mocks, real objects, bigger units

---
layout: image-right
image: /dep-tree-11.png
---

## Mocking

Detroit-Style TDD
- (aka Inside-Out)
- (aka Classicist)

No mocks, real objects, bigger units

---
layout: image-right
image: /dep-tree-12.png
---

## Mocking

Detroit-Style TDD
- (aka Inside-Out)
- (aka Classicist)

No mocks, real objects, bigger units

---
layout: image-right
image: /dep-tree-13.png
---

## Mocking

Detroit-Style TDD
- (aka Inside-Out)
- (aka Classicist)

No mocks, real objects, bigger units

---
layout: image-right
image: /dep-tree-14.png
---

## Mocking

Detroit-Style TDD
- (aka Inside-Out)
- (aka Classicist)

No mocks, real objects, bigger units

---
layout: image-right
image: /dep-tree-14.png
---

## Mocking

Detroit-Style TDD

Advantages:
- Decouple test from implementation, cheaper

Drawbacks:
- Bigger and bigger units
- Complicated setup
- Multiple classes under test


---
layout: image-right
image: /dep-tree-8.png
---

## Mocking

Detroit-Style TDD

Recommendations:
- Use TDD: start from the bottom
- Add some mocks, sometimes


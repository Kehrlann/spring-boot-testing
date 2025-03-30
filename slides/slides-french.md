---
theme: default
class: "text-center"
highlighter: prism
lineNumbers: true
transition: none
# use UnoCSS
css: unocss
aspectRatio: "16/9"
colorSchema: "light"
canvasWidth: 1024
hideInToc: true
---

# **Tester vos apps Spring Boot**

## _Sous toutes les coutures_

<br>

### Daniel Garnier-Moiroux

Devoxx France, 2025-04-18


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

# Avertissement

&nbsp;

Cette présentation ne parle pas des **tests en général**, comment les organiser, quelle architecture utiliser...


Cherchcez "Devoxx tests", trier par "nombre de vues" :)

---
layout: image
image: /spring-logo.webp
---

---

# Spring Boot Testing

1. 🕸️ `@SpringBootTest` et tests web
1. 🐌 `TestContext` et mise en cache
1. 🧰 Boîte à outils des tests!
1. 🍕 Slice tests: ApplicationContext, version "ultra-léger"
1. 📦 Testcontainers
1. 🧰 Boîte à outils des tests!
1. ⚙️ Tester les `@ConfigurationProperties`
1. 🔐 Tests de la couche sécurité

---

# Spring Boot Testing

1. **🕸️ `@SpringBootTest` et tests web**
1. 🐌 `TestContext` et mise en cache
1. 🧰 Boîte à outils des tests!
1. 🍕 Slice tests: ApplicationContext, version "ultra-léger"
1. 📦 Testcontainers
1. 🧰 Boîte à outils des tests!
1. ⚙️ Tester les `@ConfigurationProperties`
1. 🔐 Tests de la couche sécurité

---

# 🕸️ `@SpringBootTest` et tests web

<br>

- `WebEnvironment.MOCK` environment "mock" servlet
  - Suffisant dans la plupart des cas

<br>

- `WebEnvironment.RANDOM_PORT` serveur web complet (e.g. Tomcat)
  - Utile pour tests en profondeur (e.g. sérialisation sessions)
  - Utile pour débugguer
  - `@LocalServerPort`, `WebTestClient` ou `TestRestTemplate`

---

# 🕸️ `@SpringBootTest` et tests web

<br>

- `MockMvc` ou `MockMvcTester` pour les tests par requête
  - (Seulement en mode `MOCK`)
- HtmlUnit's `WebClient` pour tests browser (légers)
- Selenium / WebDriver pour un browser complet
- Tests d'intégration avec des outils adaptés, typiquement Javascript (Playwright, Cypress)

---

# Spring Boot Testing

1. 🕸️ `@SpringBootTest` et tests web
1. **🐌 `TestContext` et mise en cache**
1. 🧰 Boîte à outils des tests!
1. 🍕 Slice tests: ApplicationContext, version "ultra-léger"
1. 📦 Testcontainers
1. 🧰 Boîte à outils des tests!
1. ⚙️ Tester les `@ConfigurationProperties`
1. 🔐 Tests de la couche sécurité

---

# 🐌 `TestContext` et mise en cache

<br>

- `@SpringBootTest` sont lents (~ secondes)
- Éviter `@ActiveProfiles`, `@MockitoBean`, `@DirtiesContext` ...
- Réutiliser le context qui a été mis en cache, pour éviter de payer le prix de démarrage à chaque test

---

# 🐌 `TestContext` et mise en cache

<br>

**Après la pub:**

- Test slices:
    - `@WebMvcTest`, `@DataJpaTest`, ...

---

# Spring Boot Testing

1. 🕸️ `@SpringBootTest` et tests web
1. 🐌 `TestContext` et mise en cache
1. **🧰 Boîte à outils des tests!**
1. 🍕 Slice tests: ApplicationContext, version "ultra-léger"
1. 📦 Testcontainers
1. 🧰 Boîte à outils des tests!
1. ⚙️ Tester les `@ConfigurationProperties`
1. 🔐 Tests de la couche sécurité

---

# 🧰 Boîte à outils des tests!

<br>


- `assertJ` "fluent assertions"
    - `MockMvcTester`: `MockMvc` avec assertJ (Boot >= 3.4)
    - Assertions assertJ custom 🤯️

- `@OutputCaptureExtension`: capturer toutes les sorties stdout/stderr

---

# 🧰 Boîte à outils des tests!

<br>

**Vous pouvez:**

- Demander à un LLM

**Vous _devriez_:**

- Lire la doc <small>_(wow such old very 2023)_</small>

---

# Spring Boot Testing

1. 🕸️ `@SpringBootTest` et tests web
1. 🐌 `TestContext` et mise en cache
1. 🧰 Boîte à outils des tests!
1. **🍕 Slice tests: ApplicationContext, version "ultra-léger**
1. 📦 Testcontainers
1. 🧰 Boîte à outils des tests!
1. ⚙️ Tester les `@ConfigurationProperties`
1. 🔐 Tests de la couche sécurité

---

# 🍕 Slice tests: ApplicationContext, version "ultra-léger"

<br>

- Les slice tests sont légers, focalisés
- Besoin de mocks or de beans de remplacement
- `@WebMvcTest`, `@DataJpaTest`
    - Slices custom, avec `@SpringBootTest(classes = ...)`

---

# Spring Boot Testing

1. 🕸️ `@SpringBootTest` et tests web
1. 🐌 `TestContext` et mise en cache
1. 🧰 Boîte à outils des tests!
1. 🍕 Slice tests: ApplicationContext, version "ultra-léger"
1. **📦 Testcontainers**
1. 🧰 Boîte à outils des tests!
1. ⚙️ Tester les `@ConfigurationProperties`
1. 🔐 Tests de la couche sécurité

---

# 📦 Testcontainers

<br>

- Setup simple avec @ `start.spring.io`
- Configuration automatique avec `@ServiceConnection`
    - Postgres, Otlp..., Redis, Rabbit...
- Configuration dynamique avec `@DynamicPropertySource`
- Pattern singleton avec containers `static`

---

# Spring Boot Testing

1. 🕸️ `@SpringBootTest` et tests web
1. 🐌 `TestContext` et mise en cache
1. 🧰 Boîte à outils des tests!
1. 🍕 Slice tests: ApplicationContext, version "ultra-léger"
1. 📦 Testcontainers
1. **🧰 Boîte à outils des tests!**
1. ⚙️ Tester les `@ConfigurationProperties`
1. 🔐 Tests de la couche sécurité

---

# 🧰 Boîte à outils des tests!

<br>

- `awaitility` pour attendre et "sonder"
    - Ne jamais, jamais utiliser `Thread.sleep`*

<br>

- Apprenez à configurer Mockito `mockito`

<br>
<br>
<br>

<small>* sauf quand, vous savez, It Depends™</small>

---

# Spring Boot Testing

1. 🕸️ `@SpringBootTest` et tests web
1. 🐌 `TestContext` et mise en cache
1. 🧰 Boîte à outils des tests!
1. 🍕 Slice tests: ApplicationContext, version "ultra-léger"
1. 📦 Testcontainers
1. 🧰 Boîte à outils des tests!
1. **⚙️ Tester les `@ConfigurationProperties`**
1. 🔐 Tests de la couche sécurité

---

## ⚙️ Tester les `@ConfigurationProperties`

<br>

- Construct property objects and validate those
    - `Validation.buildDefaultValidatorFactory().getValidator()`
    - Consider using YAML

- For integration testing:
    - Use `@SpringBootTest(classes = { ... })`
    - For failures, `SpringApplicationBuilder#run`


---

# Spring Boot Testing

1. 🕸️ `@SpringBootTest` et tests web
1. 🐌 `TestContext` et mise en cache
1. 🧰 Boîte à outils des tests!
1. 🍕 Slice tests: ApplicationContext, version "ultra-léger"
1. 📦 Testcontainers
1. 🧰 Boîte à outils des tests!
1. ⚙️ Tester les `@ConfigurationProperties`
1. **🔐 Tests de la couche sécurité

---

# 🔐 Tests de la couche sécurité

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


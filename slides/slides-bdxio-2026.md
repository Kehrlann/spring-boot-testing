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
---

# **Tester vos apps Spring Boot**

## _Sous toutes les coutures_

<br>

### Daniel Garnier-Moiroux

BDX I/O, 2026-10-30


---
layout: image-right
image: /daniel-intro-small.jpg
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


Cherchez "Devoxx tests", trier par "nombre de vues" :)

---
layout: image
image: /spring-logo.webp
---

---

# Et les agents?

&nbsp;

Ce contenu est fait pour des humains!


Affûter le jugement avant de déléguer.

---

# Spring Boot Testing

1. 🕸️ `@SpringBootTest` et tests web
1. 🐌 `TestContext` et mise en cache
1. 🧰 Boîte à outils des tests!
1. 🍕 Slice tests: ApplicationContext, version "ultra-léger"
1. 📦 Testcontainers
1. ⚙️ Test des `@ConfigurationProperties`
1. 🔐 Tests de la couche sécurité

---

# Spring Boot Testing

1. **🕸️ `@SpringBootTest` et tests web**
1. 🐌 `TestContext` et mise en cache
1. 🧰 Boîte à outils des tests!
1. 🍕 Slice tests: ApplicationContext, version "ultra-léger"
1. 📦 Testcontainers
1. ⚙️ Test des `@ConfigurationProperties`
1. 🔐 Tests de la couche sécurité

---

# 🕸️ `@SpringBootTest` et tests web

<br>

- `WebEnvironment.MOCK` environnement servlet "mock"
  - Suffisant dans la plupart des cas

<br>

- `WebEnvironment.RANDOM_PORT` serveur web complet (e.g. Tomcat)
  - Utile pour tests en profondeur (e.g. sérialisation sessions)
  - Utile pour débugguer
  - `@LocalServerPort`, `RestTestClient` ou `WebTestClient`

---

# 🕸️ `@SpringBootTest` et tests web

<br>

- `MockMvcTester` pour les tests par requête
  - Seulement en mode `MOCK`
  - `MockMvc` en "legacy"
- `RestTestClient` pour les requêtes `MOCK` et `RANDOM_PORT`
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
1. ⚙️ Test des `@ConfigurationProperties`
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
1. ⚙️ Test des `@ConfigurationProperties`
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
1. **🍕 Slice tests: ApplicationContext, version "ultra-léger**
1. 📦 Testcontainers
1. ⚙️ Test des `@ConfigurationProperties`
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
1. ⚙️ Test des `@ConfigurationProperties`
1. 🔐 Tests de la couche sécurité

---

# 📦 Testcontainers

<br>

- Setup simple avec @ `start.spring.io`
- Configuration automatique avec `@ServiceConnection`
    - Postgres, Otlp..., Redis, Rabbit...
- Configuration dynamique avec `DynamicPropertyRegistrar`
- Pattern singleton avec containers `static`

---

# Spring Boot Testing

1. 🕸️ `@SpringBootTest` et tests web
1. 🐌 `TestContext` et mise en cache
1. 🧰 Boîte à outils des tests!
1. 🍕 Slice tests: ApplicationContext, version "ultra-léger"
1. 📦 Testcontainers
1. **⚙️ Test des `@ConfigurationProperties`**
1. 🔐 Tests de la couche sécurité

---

## ⚙️ Test des `@ConfigurationProperties`

<br>

- Construire des objets property les valider
    - `Validation.buildDefaultValidatorFactory().getValidator()`
    - Utiliser du YAML?

- Pour les tests d'intégration:
    - Utiliser `@SpringBootTest(classes = { ... })`
    - Pour les exceptions, utiliser `SpringApplicationBuilder#run`


---

# Spring Boot Testing

1. 🕸️ `@SpringBootTest` et tests web
1. 🐌 `TestContext` et mise en cache
1. 🧰 Boîte à outils des tests!
1. 🍕 Slice tests: ApplicationContext, version "ultra-léger"
1. 📦 Testcontainers
1. ⚙️ Test des `@ConfigurationProperties`
1. **🔐 Tests de la couche sécurité**

---

# 🔐 Tests de la couche sécurité

<br>

- Compatible avec `@WebMvcTest` & `@SpringBootTest`
- `@WithMockUser` pour injecter un utilisateur
    - `@WithUserDetailsService`

<br>

- `SecurityMockMvcRequestPostProcessors` pour `MockMvc(Tester)`
    - `.csrf()`, `.opaqueToken()`, `.oidcLogin()` ...
    - ... et plus encore!


---

# References


#### **<logos-github-icon /> https://github.com/Kehrlann/spring-boot-testing**

<br>

<!-- ouch the hack -->
<!-- qrencode -s 9 -m 2 -o public/qr-code-repo.png "https://github.com/Kehrlann/spring-boot-testing" -->
<div style="float:right; margin-right: 50px; text-align: center;">
  <img src="/qr-code-repo.png" style="margin-bottom: -45px; margin-top: -15px;" >
</div>


Faites-moi signe!
- <logos-bluesky /> @garnier.wf
- <logos-firefox /> https://garnier.wf/
- <fluent-emoji-flat-envelope-with-arrow /> contact@garnier.wf


---
layout: two-cols-header
---

#### **<logos-github-icon /> https://github.com/Kehrlann/spring-boot-testing-book**

::left::

<img src="/qr-code-book.png" style="height: 200px" >

::right::

#### **Testing Spring Boot Applications**

Chapitres 1 à 6

Gratuit!


---
layout: image
image: /meet-me.jpg
class: end
---

# **Merci 😊**


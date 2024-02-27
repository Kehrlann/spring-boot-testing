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

# Testing with Spring Boot

<br>
<br>

### Daniel Garnier-Moiroux

AF-KLM Spring Meetup, 2024-02-27


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

- <logos-spring-icon /> Broadcom+Tanzu+Spring
- <logos-mastodon-icon /> @Kehrlann@hachyderm.io
- <logos-twitter /> @Kehrlann
- <logos-firefox /> https://garnier.wf/
- <logos-github-icon /> github.com/Kehrlann/
- <fluent-emoji-flat-envelope-with-arrow /> daniel.garnier-moiroux@broadcom.com


---

# Spring Boot Testing

1. 🕸️ `@SpringBootTest` and web testing
1. 🍕 Slice tests: slimmer ApplicationContext
1. 📦 Testcontainers
1. 🧰 Testing toolbox: everything you need
1. 🔐 *Maybe* Security testing: utility methods
1. 🥸 *Maybe* A word on mocking

---

Repo:
- <logos-github-icon /> https://github.com/Kehrlann/spring-boot-testing

Reach out:
- <logos-mastodon-icon /> @Kehrlann@hachyderm.io
- <logos-twitter /> @Kehrlann
- <fluent-emoji-flat-envelope-with-arrow /> daniel.garnier-moiroux@broadcom.com

## 🤔 Questions?



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


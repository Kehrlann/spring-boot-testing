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
1. 🧰 Testing toolbox: everything you need
1. 🔐 Security testing: utility methods
1. 🥸 A word on mocking
1. 📦 Testcontainers

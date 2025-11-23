# Lyra - Plataforma de Orientação de Carreira com IA

## 👥 Integrantes
- 👩‍💻 **Hellen Marinho Cordeiro** — RM: 558841
- 👩‍💻 **Heloisa Alves de Mesquita** — RM: 559145
- 👨‍💻 **Gabriel Dias Menezes** — RM: 555019

---

## 📋 Sobre o Projeto
A **Lyra** é uma plataforma web inteligente criada para orientar profissionais diante de um mercado de trabalho em constante transformação. Utilizando **IA generativa**, análise comportamental e tendências globais, a aplicação funciona como um **mentor digital de carreira**, ajudando o usuário a identificar seus pontos fortes, entender onde suas habilidades se encaixam e descobrir caminhos profissionais sustentáveis e alinhados ao futuro.

### 🌟 Principais Funcionalidades
- **Quiz Inteligente** — Identifica o perfil profissional do usuário.
- **Trilha de Desenvolvimento Personalizada** — Recomenda áreas, cursos e certificações alinhadas ao perfil.
- **Cenários de Carreira até 2030** — Projeções baseadas em tendências globais.
- **Painel de Impacto ODS** — Relaciona o desenvolvimento profissional aos Objetivos de Desenvolvimento Sustentável da ONU.
- **IA Generativa (GPT)** — Análises, sugestões de carreira e trilhas geradas em tempo real.

---

## 🛠️ Tecnologias Utilizadas

### Backend (Java + Spring)
- **Java 17+**
- **Spring Boot**
- Spring MVC
- Spring Data JPA
- Spring AI (OpenAI GPT‑4o‑mini)
- Spring Cache
- Internacionalização (Spring Context)
- **PostgreSQL**
- **Flyway** — Migrações do banco
- **RabbitMQ** — Mensageria assíncrona
- **Bean Validation**

### Frontend
- **Thymeleaf**
- HTML5, CSS3, JavaScript

### Infraestrutura
- Docker (PostgreSQL + RabbitMQ via docker-compose)
- Deploy via **Render**

---

## 🗄️ Versionamento do Banco (Flyway)
As migrações ficam no diretório:
```
src/main/resources/db/migration/
```

---

## 🚀 Como Executar o Projeto

### ✔️ Pré‑requisitos
- Java 17+
- Maven 3.8+
- Postgres 15+ (ou usar Docker)
- Git

---

## 🔽 1. Clonar o Repositório
```bash
git clone https://github.com/hmarinhoo/Lyra_MVC
cd Lyra_MVC
```

---

# 🔑 Como obter e configurar sua OpenAI API Key
A aplicação usa **Spring AI + OpenAI GPT‑4o‑mini** para gerar as trilhas de carreira.

Siga o passo a passo abaixo.

---

## 1️⃣ Acesse a plataforma da OpenAI
https://platform.openai.com

Faça login com sua conta.

---

## 2️⃣ Vá para API Keys
Menu lateral: **Dashboard → API Keys**

Ou:  
https://platform.openai.com/settings/organization/api-keys

---

## 3️⃣ Crie uma nova chave
Clique em **Create new secret key**.

Dê um nome, como:
```
lyra-backend
```

---

## 4️⃣ Copie sua API Key
Ela terá o formato:
```
sk-proj-xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
```
⚠️ **A chave só aparece uma vez**.

Guarde com segurança.

---

## 5️⃣ Configure no arquivo `application.properties`
Arquivo:
```
src/main/resources/application.properties
```
Adicione sua chave:
```properties
spring.ai.openai.api-key=sk-proj-xxxxxxxxxxxxxxxxxxxxxxxx
jwt.secret=MEUSEGREDOSUPERSECRETOPARAJWT123456789
```

---

## 6️⃣ Executar o projeto
```bash
mvn spring-boot:run
```

---

## 🔏 Segurança — NÃO COMITAR A CHAVE
Nunca envie sua API Key para o GitHub.
Use variáveis de ambiente.

### Usando variável de ambiente
**No application.properties:**
```properties
spring.ai.openai.api-key=${OPENAI_API_KEY}
```

**No Linux/Mac:**
```bash
export OPENAI_API_KEY="sk-proj-xxxxxxxx"
mvn spring-boot:run
```

**Windows PowerShell:**
```powershell
setx OPENAI_API_KEY "sk-proj-xxxxxxxx"
mvn spring-boot:run
```

---

# 📦 Deploy
A aplicação está preparada para deploy em:
👉 **https://lyra-mvc-memx.onrender.com/**

> Observação: Render gratuito pode demorar para iniciar (cold start).

---

# 📹 Demonstração
O projeto pode ser apresentado mostrando:
- Execução local via Maven
- Funcionamento completo do fluxo: Quiz → IA → Trilha
- Prints/fotos da aplicação em produção
- Vídeo gravado mostrando o funcionamento

---

## 📦 Deploy

### Deploy em Produção
Aplicação preparada para deploy em: 
https://lyra-mvc-memx.onrender.com/

- 

---

**Desenvolvido com ❤️ pela Equipe Lyra**

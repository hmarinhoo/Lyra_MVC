# Lyra - Plataforma de Orientação de Carreira com IA

## 👥 Integrantes
* 👩‍💻 Hellen Marinho Cordeiro - RM: 558841
* 👩‍💻 Heloisa Alves de Mesquita - RM: 559145
* 👩‍💻 Gabriel Dias Menezes - RM: 555019


## 📋 Sobre o Projeto

A Lyra é uma plataforma web criada para orientar profissionais em um mercado de trabalho em constante transformação. Combinando IA generativa, análise de perfil e tendências globais, o sistema funciona como um mentor de carreira personalizado, ajudando o usuário a entender onde suas habilidades se encaixam no futuro e como evoluir de forma sustentável e estratégica.

### Principais Funcionalidades

- **Quiz Inteligente**: Identificação do perfil profissional do usuário
- **Trilha de Desenvolvimento Personalizada**: Sugestões de áreas emergentes, cursos e certificações
- **Cenários de Carreira**: Projeções e possibilidades até 2030
- **Painel de Impacto ODS**: Conexão do crescimento individual com os Objetivos de Desenvolvimento Sustentável da ONU
- **IA Generativa**: Recomendações personalizadas e análises de mercado em tempo real

---

## 🛠️ Tecnologias Utilizadas

### Backend
- **Java 17+**
- **Spring Framework**
  - Spring Boot
  - Spring MVC
  - Spring Data JPA
  - Spring AI (IA Generativa com OpenAI GPT-4o-mini)
  - Spring Cache
  - Spring Context (Internacionalização)
- **PostgreSQL** - Banco de dados relacional
- **Flyway** - Versionamento e migração de banco de dados
- **RabbitMQ** - Sistema de mensageria assíncrona
- **Bean Validation** - Validação de dados

### Frontend
- **Thymeleaf** - Template engine
- **HTML5, CSS3, JavaScript**

---

## 🗄️ Versionamento do Banco de Dados (Flyway)

A aplicação utiliza **PostgreSQL** e **Flyway** para migração automática e versionamento do banco de dados. As migrações estão localizadas em:
```
src/main/resources/db/migration/
```
## 🚀 Como Executar o Projeto

### Pré-requisitos
- Java 17 ou superior
- Maven 3.8+
- MySQL 8.0+
- Git

### Passo a Passo

**1. Clone o repositório**
```bash
git clone https://github.com/hmarinhoo/Lyra_MVC
cd Lyra_MVC
```

**2. Configurando **

Edite `src/main/resources/application.properties`:
```properties
spring.ai.openai.api-key=sk-proj-xxxxxxxxxxxxxxxxxxxxxxxx
jwt.secret=MEUSEGREDOSUPERSECRETOPARAJWT123456789
```

**4. Execute o projeto**
```bash
mvn spring-boot:run
```

## 📦 Deploy

### Deploy em Produção
Aplicação preparada para deploy em: 
https://lyra-mvc-memx.onrender.com/

- 

---

**Desenvolvido com ❤️ pela Equipe Lyra**

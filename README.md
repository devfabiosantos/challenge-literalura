# LiterAlura 📚

![Java](https://img.shields.io/badge/Java-17-blue?logo=java)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.5.0-brightgreen?logo=spring)
![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)

Aplicação Java que consome a API pública do Projeto Gutendex e permite buscar livros por título, listar autores e realizar diversas consultas, com persistência em banco de dados PostgreSQL usando Spring Data JPA.

## 🚀 Tecnologias utilizadas

- Java 17
- Spring Boot 3.5.0
- Spring Data JPA
- PostgreSQL
- Jackson (para manipulação de JSON)
- Maven

## 📦 Dependências principais (pom.xml)

```xml
<dependencies>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-data-jpa</artifactId>
    </dependency>
    <dependency>
        <groupId>org.postgresql</groupId>
        <artifactId>postgresql</artifactId>
        <scope>runtime</scope>
    </dependency>
    <dependency>
        <groupId>com.fasterxml.jackson.core</groupId>
        <artifactId>jackson-databind</artifactId>
        <version>2.16.0</version>
    </dependency>
</dependencies>
```

> ✅ Certifique-se de ter o PostgreSQL rodando localmente.

---

## 🔧 Configuração do Banco de Dados

Configure o arquivo `application.properties`:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/literalura
spring.datasource.username=seu_usuario
spring.datasource.password=sua_senha
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

Crie o banco de dados localmente antes de iniciar o projeto:

```sql
CREATE DATABASE literalura;
```

---

## 📥 Como rodar o projeto

1. Clone este repositório:
   ```bash
   git clone https://github.com/devfabiosantos/challenge-literalura.git
   ```

2. Navegue até a pasta do projeto:
   ```bash
   cd literalura
   ```

3. Execute o projeto com o Maven:
   ```bash
   ./mvnw spring-boot:run
   ```

   Ou diretamente pela sua IDE (IntelliJ, Eclipse, etc), rodando a classe:

   ```java
   br.com.alura.literalura.LiteraluraApplication
   ```

---

## 💻 Funcionalidades

Ao executar a aplicação, será exibido o menu no terminal com as opções:

```
1 - Buscar livro pelo título
2 - Listar livros registrados
3 - Listar autores registrados
4 - Listar autores vivos em um determinado ano
5 - Listar livros por idioma
0 - Sair
```

---

## 🌐 Fonte de dados

Este projeto utiliza a API pública do [Projeto Gutendex](https://gutendex.com/) para obter informações sobre livros e autores.

---

## 📄 Licença

Este projeto é licenciado sob a licença MIT. Veja o arquivo [LICENSE](LICENSE) para mais detalhes.

---

## 👨‍💻 Autor

Desenvolvido por [Fabio Antonio dos Santos](https://www.linkedin.com/in/fabio-santos-b8082749)

# API Fatec - Gerenciamento de Inventário

API Spring Boot para gerenciamento de produtos, categorias e usuários. Este projeto foi usado como backend para a atividade final de integração fullstack com uma SPA em React.

## Tecnologias

- Java 24
- Spring Boot 4.0.5
- Spring Web MVC
- Spring Data JPA
- Spring Security
- Bean Validation
- PostgreSQL
- Lombok
- Springdoc OpenAPI / Swagger

## Requisitos

- Java 24 instalado
- PostgreSQL rodando localmente
- Banco de dados `api` criado no PostgreSQL
- Maven Wrapper do projeto (`mvnw.cmd` no Windows)

## Configuração do banco

O arquivo `application.properties` não vem junto com o projeto. Para utilizar a API, crie manualmente o arquivo:

```text
src/main/resources/application.properties
```

Conteúdo sugerido:

```properties
server.port=8081
spring.datasource.url=jdbc:postgresql://localhost:5432/api
spring.datasource.username=SEU_USUARIO_AQUI
spring.datasource.password=SUA_SENHA_AQUI
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

Antes de iniciar a API, altere `spring.datasource.username` e `spring.datasource.password` com as credenciais do seu PostgreSQL. Se o banco, host ou porta forem diferentes, ajuste também a URL em `spring.datasource.url`.

## Como executar

Na raiz do projeto da API:

```bash
.\mvnw.cmd spring-boot:run
```

A API será iniciada em:

```text
http://localhost:8081
```

## Swagger

Com a API rodando, acesse:

```text
http://localhost:8081/swagger-ui/index.html
```

## Usuário mestre

Na inicialização, a API garante a existência do usuário mestre:

- Email: `admin@fatec.sp.gov.br`
- Senha: `admin123`
- Perfil: `ADMIN`

A senha é salva no banco criptografada com BCrypt.

## Autenticação

Endpoint de login:

```http
POST /api/auth/login
```

Exemplo de corpo:

```json
{
  "email": "admin@fatec.sp.gov.br",
  "senha": "admin123"
}
```

## CORS

O backend está configurado para permitir chamadas do frontend em desenvolvimento:

```text
http://localhost:5173
http://127.0.0.1:5173
http://192.168.*.*:*
```

Isso permite acessar a interface pelo computador e também por celular na mesma rede local.

## Endpoints principais

### Produtos

```http
GET    /api/produtos
GET    /api/produtos/busca
GET    /api/produtos/{id}
GET    /api/produtos/categoria/{id}
POST   /api/produtos
PUT    /api/produtos/{id}
DELETE /api/produtos/{id}
```

Exemplo de cadastro de produto:

```json
{
  "nome": "Teclado",
  "preco": 120.00,
  "categoriaId": 1
}
```

### Categorias

```http
GET    /api/categorias
GET    /api/categorias/{id}
POST   /api/categorias
PUT    /api/categorias/{id}
DELETE /api/categorias/{id}
```

Exemplo de cadastro de categoria:

```json
{
  "nome": "Alimentos"
}
```

### Usuários

```http
GET  /api/usuarios
POST /api/usuarios
```

Exemplo de cadastro de usuário:

```json
{
  "nome": "Usuário Teste",
  "email": "usuario@fatec.sp.gov.br",
  "senha": "Senha@123",
  "role": "USER"
}
```

## Regra de senha para novos usuários

Novos usuários cadastrados pela API devem usar senha com:

- mínimo de 8 caracteres;
- pelo menos uma letra maiúscula;
- pelo menos uma letra minúscula;
- pelo menos um número;
- pelo menos um caractere especial;
- sem espaços.

Observação: o usuário mestre `admin@fatec.sp.gov.br` continua usando `admin123`, conforme solicitado na atividade.

## Comandos úteis

Compilar:

```bash
.\mvnw.cmd compile
```

Rodar testes:

```bash
.\mvnw.cmd test
```

Gerar pacote:

```bash
.\mvnw.cmd package
```

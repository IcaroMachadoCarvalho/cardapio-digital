# Cardápio Digital

API REST desenvolvida com Spring Boot para gerenciamento de pratos de um cardápio digital.
O sistema permite cadastro, listagem, atualização, exclusão e filtros de pratos, seguindo boas práticas.

Este projeto foi desenvolvido como projeto pessoal.

## 📑 Índice

1. [Sobre o projeto](#sobre-o-projeto)
2. [Tecnologias utilizadas](#tecnologias-utilizadas)
3. [Instalação](#instalação)
4. [Uso](#uso)
5. [Funcionalidades](#funcionalidades)
6. [Contato](#contato)

## 📚 1. <a name="sobre-o-projeto">Arquitetura Geral</a>

### Descrição

O projeto segue uma estrutura baseada no ecossistema Spring, dividida em camadas para facilitar a manutenção e escalabilidade. A arquitetura utiliza boas práticas de design para garantir que o código seja limpo e de fácil manutenção.

| **Camada**            | **Função**                                                            |
| --------------------- | --------------------------------------------------------------------- |
| **Model**             | Representação da entidade **Dish** (Prato)                            |
| **Repository**        | Camada de persistência (utiliza um repositório em memória para dados) |
| **Service**           | Camada que contém as regras de negócio e validações                   |
| **Controller (Main)** | Ponto de entrada que gerencia a interação com o usuário               |
| **Swagger**           | Documentação interativa da API                                        |
| **Errors/Exceptions** | Exceções personalizadas para garantir mensagens claras ao usuário     |

## ⚙️ 2. <a name="tecnologias-utilizadas">Tecnologias Utilizadas</a>

### Linguagem

- Java 17+: Utilizado como linguagem principal.

### Frameworks e Bibliotecas

- Spring Boot: Framework para criação da API REST.
- Spring Data JPA: Utilizado para persistência de dados, embora neste exemplo esteja sendo utilizado um repositório em memória.
- Spring Boot Starter Validation: Para validações nos dados de entrada.
- Swagger/OpenAPI: Para documentação da API.

### Ferramentas

- Git & GitHub: Controle de versão e hospedagem.

### Arquitetura

- MVC (Model–View–Controller)
- Aproximando do padrão de projeto usado para Spring

## 🔌 3. Fluxo de Dados

1. O cliente envia uma requisição HTTP.

2. O Controller mapeia a requisição e chama o Service apropriado.

3. O Service processa a lógica de negócio, utilizando o Repository para interagir com o banco de dados.

4. O Repository faz as operações no banco de dados (salvar, atualizar, excluir, etc.).

5. O Service retorna os dados processados para o Controller, que gera a resposta para o cliente.

### <a name="funcionalidades">Funcionalidades</a>

- Cadastrar Prato: Permite adicionar um novo prato ao cardápio, com informações como nome, preço e status (disponível ou não).
- Listar Pratos: Exibe todos os pratos cadastrados, podendo ser filtrados por status e preço.
- Buscar Prato por ID: Permite buscar um prato específico pelo seu ID único.
- Atualizar Prato: Permite alterar os dados de um prato já existente (nome, preço e status).
- Deletar Prato: Remove um prato do sistema.
- Registrar Venda: Deduz a quantidade do prato no estoque após uma venda.
- Tratamento de Erros: O sistema lança exceções customizadas para situações como prato não encontrado ou dados inválidos.

### Principais rotas

**Docs**

- http://localhost:8080/swagger-ui.html

**Dish**

- POST http://localhost:8080/api/v1/dishes
- POST http://localhost:8080/api/api/v1/dishes

## 🛠 <a name="instalação">Instalação</a>

Para visualizar com mais detalhes localmente, siga os passos abaixo:

**Pré requisitos**

Este é um projeto web estático, sem dependências de _runtime_ (como Node.js), mas você precisa ter o Git instalado.

- [Git](https://git-scm.com/)
- JDK 17 ou superior
- Uma IDE (IntelliJ IDEA, Eclipse ou VS Code)

**Clone o Repositório**

1. Clone o repositório:

   ```bash
   git clone https://github.com/IcaroMachadoCarvalho/cardapio-digital.git
   ```

2. Abra a pasta do projeto na sua IDE de preferência.
3. Aguarde a IDE indexar os arquivos.
4. Para configurar o banco de dados, crie o arquivo application-dev.properties dentro da pasta src/main/resources/ e cole o seguinte conteúdo:

```bash
   # Datasource H2
   spring.datasource.url=jdbc:h2:mem:testdb
   spring.datasource.driver-class-name=org.h2.Driver
   spring.datasource.username=sa
   spring.datasource.password=

   # JPA
   spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
   spring.jpa.hibernate.ddl-auto=update
   spring.jpa.show-sql=true

   # H2 Console
   spring.h2.console.enabled=true
   spring.h2.console.path=/h2-console
```

## 💻 <a name="uso">Uso</a>

#### Rodar a aplicação

1. Navegue até a classe Main em: src/main/com/IcaroMachadoCarvalho/gerenciadorestoque/Main.java.
2. Clique com o botão direito e selecione "Run 'CardapioDigitalApplication.java'".
3. Interaja com o menu no console da IDE digitando os números correspondentes.

## 🤝 <a name="contato">Contato</a>

Caso queira entrar em contato, use as informações do meu perfil:

- **Nome:** Ícaro Machado de Carvalho
- **LinkedIn:** [in/ícaromachadodecarvalho](www.linkedin.com/in/ícaromachadodecarvalho)
- **GitHub:** [@IcaroMachadoCarvalho ](https://github.com/IcaroMachadoCarvalho)

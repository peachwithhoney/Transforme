# Transforme+ - Plataforma de Gerenciamento de Doações e Projetos

O **Transforme+** é uma plataforma que permite o gerenciamento de usuários, doações e projetos. Com ele, é possível cadastrar usuários, registrar doações e acompanhar o progresso de projetos, incluindo metas financeiras e arrecadações.
Ele é um projeto para uma cadeira da universidade.

## Funcionalidades

- **Login de Usuários**: Autenticação de usuários com email e senha.
- **Gerenciamento de Usuários**: Cadastro, edição e exclusão de usuários.
- **Gerenciamento de Projetos**: Cadastro de projetos com metas financeiras e acompanhamento de arrecadações.
- **Registro de Doações**: Registro de doações vinculadas a usuários e projetos.
- **Relatórios**: Cálculo de arrecadações em períodos específicos.

## Tecnologias Utilizadas

- **Linguagem**: Java
- **Interface Gráfica**: JavaFX
- **Banco de Dados**: MySQL
- **Ferramentas**: Git, GitHub, VSCode e Eclipse

## Estrutura do Projeto

- **/assets**: Contém recursos visuais, como imagens e ícones.
- **/bin**: Arquivos compilados (se aplicável).
- **/classes**: Contém as entidades do sistema (`Usuario`, `Doacao`, `Projeto`).
- **/controller**: Lógica de negócio e validações.
- **/DAO**: Camada de acesso ao banco de dados (operações CRUD).
- **/exceptions**: Exceções personalizadas do sistema.
- **/main**: Classe principal e testes.
- **/service**: Serviços específicos do sistema.
- **/view**: Telas da interface gráfica (JavaFX).

## Como Executar o Projeto

1. **Pré-requisitos**:
   - Java JDK 11 ou superior.
   - MySQL instalado e configurado.
   - IDE de sua preferência (ex.: IntelliJ, Eclipse).

2. **Configuração do Banco de Dados**:
   - Crie um banco de dados no MySQL chamado `db_transforme`.
   - Execute o script SQL localizado em `src/main/resources/schema.sql` para criar as tabelas necessárias.

3. **Configuração do Projeto**:
   - Clone o repositório:
     ```bash
     git clone https://github.com/seu-usuario/transforme-plus.git
     ```
   - Importe o projeto na sua IDE.
   - Configure as credenciais do banco de dados no arquivo `Conexao.java`.

4. **Executando o Projeto**:
   - Execute a classe `Main` localizada em `src/main/Main.java`.
   - A tela de login será aberta. Use as credenciais de um usuário cadastrado para acessar o sistema.
  


## Contato

- **Nome**: [Clarissa Queiroz]
- **Email**: [clarissasousa.q@outlook.com]
- **GitHub**: [https://github.com/peachwithhoney]

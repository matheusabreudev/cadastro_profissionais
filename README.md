# Cadastro de Profissionais

O Cadastro de Profissionais é uma aplicação web que permite o gerenciamento de informações sobre profissionais, como nome, cargo, data de nascimento, entre outros.

## Funcionalidades

- Cadastro de novos profissionais.
- Edição e exclusão de profissionais existentes.
- Listagem de todos os profissionais cadastrados.

## Tecnologias Utilizadas

- **Spring Boot**: Framework Java para desenvolvimento de aplicações web.
- **Spring Data JPA**: Biblioteca do Spring para integração com o banco de dados.
- **PostgreSQL**: Banco de dados relacional utilizado para armazenar os dados dos profissionais.
- **Maven**: Gerenciador de dependências e construção de projetos.
- **Lombok**: Ferramenta que ajuda a reduzir a verbosidade do código Java, fornecendo anotações para gerar automaticamente getters, setters, construtores e outros métodos comuns.
- **JUnit**: Ferramenta utilizada para testes unitários da aplicação.

## Pré-requisitos

- Java 17 ou superior instalado.
- Maven instalado.
- PostgreSQL instalado e configurado.
  
## Instalação

1. Clone este repositório:
   ```bash
   git clone https://github.com/matheusabreudev/cadastro_profissionais.git
2. Importe o projeto em sua IDE de preferência (Eclipse, IntelliJ, etc.).
3. Configure o arquivo application.properties com as credenciais do banco de dados PostgreSQL:
   ```bash
   spring.datasource.url=jdbc:postgresql://localhost:5432/cadastro_profissionais(nome sugerido)
   spring.datasource.username=usuario
   spring.datasource.password=senha
4. Execute a aplicação a partir da classe CadastroProfissionaisApplication.
5. Acesse a aplicação em seu navegador: http://localhost:8080.

## Uso

- Swagger disponível na url http://localhost:8080/swagger-ui.html
- Todos os endpoints disponíveis no swagger

## Regras Aplicadas

1. Um contato só pode ser cadastrado se tiver um profissional associado e existente.
2. Levando em consideração numeros de telefone fixo e móvel e ddd, telefone só pode ter numeros e entre 10 e 11 caracteres.
3. Telefone também não pode ter letras ou caracteres especiais, foi aplicada uma validação para esse fim.
4. Foi realizada a exclusão lógica baseada em uma flag chamada "ativo", e os filtros se baseiam nesse ponto.
5. Caso o profissional tenha sido apagado, seus contatos também não irão aparecer.
6. Não é possível cadastrar contatos para profissionais excluídos.
7. Não é possível cadastrar contatos com telefones já existentes.

## Contato

Se você tiver alguma dúvida ou sugestão, sinta-se à vontade para entrar em contato comigo por email em matheus.abreu.magalhaes@gmail.com.

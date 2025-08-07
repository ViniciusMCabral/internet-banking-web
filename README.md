Trabalho Internet Banking (INF012)
1. Tecnologias Utilizadas

    Backend: Java & Spring Boot (Spring Web, Spring Data JPA, Validation, Spring Security).

    Microserviço de E-mail: Java & Spring Boot com JavaMailSender.

    Frontend: React com Vite e Axios para comunicação com a API.

    Banco de Dados: PostgreSQL.

    Teste de E-mail: MailHog (via Docker) para capturar e visualizar e-mails em ambiente de desenvolvimento.

    Documentação da API: Swagger (OpenAPI).

2. Arquitetura

    Frontend (React): A interface do usuário que roda no navegador.

    Backend (Spring Boot): API REST segura para o frontend, gerencia usuários, contas, operações e a lógica de negócio. Ele chama o Microserviço de E-mail quando precisa enviar uma notificação.

    Microserviço de E-mail (Spring Boot): Um serviço que recebe requisições do Backend e envia e-mails.

3. Pré-requisitos

    Java 17 ou superior

    Maven 3.8+

    Node.js 18+ e npm

    PostgreSQL

    Docker (Apenas para rodar o MailHog de forma simples)

4. Como Executar o Projeto

    Passo 1: Preparar o Ambiente

    Banco de Dados: Instância do PostgreSQL deve estar rodando em localhost:5432 e que você possa se conectar a ela (geralmente com o usuário postgres). Não é necessário criar nenhum banco de dados manualmente; a aplicação usará o banco postgres padrão.

    MailHog: Inicie o MailHog em um terminal usando Docker.

        docker run -d -p 1025:1025 -p 8025:8025 mailhog/mailhog

    Passo 2: Iniciar os Backends
    
    Microserviço de E-mail:

    Navegue até a pasta email.

    Verifique se o application.properties está configurado para o banco postgres e para o MailHog (localhost:1025).

    Execute a aplicação Spring Boot. Ela deverá iniciar na porta 8081.

    Backend:

    Navegue até a pasta backend.

    Verifique se o application.properties está configurado para o banco postgres e se a URL do serviço de e-mail aponta para http://localhost:8081/emails.

    Execute a aplicação Spring Boot. Ela deverá iniciar na porta 8080.

    Passo 3: Iniciar o Frontend

    Frontend:

    Navegue até a pasta frontend em um novo terminal.

    Verifique no arquivo de configuração do Axios (src/services/axios.js) se a baseURL está configurada para apontar para o backend (http://localhost:8080).

    Instale as dependências: 
        npm install.

    Inicie o servidor de desenvolvimento: npm run dev. Ele deverá iniciar na porta 5173.

    Passo 4: Acessar a Aplicação

    Aplicação Frontend: http://localhost:5173

    Documentação Swagger da API: http://localhost:8080/swagger-ui.html

    MailHog (Caixa de E-mails): http://localhost:8025
# Arquitetura referência para microserviços
### Adota como padrão uma gama de ferramentas DevOps

#### Ferramentas utilizadas:

  ###### + Web (spring mvc + Swagger), Jpa (spring data + bean validations), devtools (hot deploy), MySql, H2

  ###### + Controle de sessão de autenticação de token's assinados e criptografados

  ###### + Stack cloud spring (impl netflix oss), Spring Configuration Processor

  ###### Eureka:
      * implementa o design pattern Service Registry
      * Gerencia as instâncias de microserviços
      * Checa saúde e o balanceamento de carga de instâncias da mesma aplicação

  ###### Hystrix:
      * Implementa o design pattern Circuit Breaker
      * Tratamento de requisições para recursos(hosts) indisponíveis
      * Facilita a busca pela resolução de erros ou gargalos da aplicação

  ###### Zuul:
      * Implementa o design pattern Gateway
      * Porta de entrada da aplicação (proxy), irá filtrar todas as requisições e redirecionar para seus devidos microservices
      * Realiza autenticações no servidor de autenticação caso uma rota seja segura

  ###### Ribbon:
      * Load balancer

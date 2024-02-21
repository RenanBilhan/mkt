MarketPlace Backend Simulation
Simulação do Backend de um MarketPlace

Tecnologies used:
  * Java 17
  * Spring Boot
  * Spring Data JPA
  * Postgres DB
  * Spring Security
  * Stripe Payment API
  * Swagger
  * Clean Architecture

**Credit card test token to use:
card number: 4242424242424242 
exp month: any future month
exp year: any future year
cvc: any 3 numbers combination (123 for exemple)
user name: any name**

OBS: this token is provided by StripeAPI and you can find more of them in https://stripe.com/docs/testing#cards for different tests.

English Description -> 
**For the correct use of the application, you need a Stripe account. You can cadastrate at https://stripe.com/br. When you complete the cadastration, you can access https://dashboard.stripe.com/test/apikeys, where you can find your Secret API Key and the Public API key. You can pass both keys By VM args in the application properties. the properties are already defined.**

Application created for practicing table/class interaction and the integration with an external payment API (Stripe),saving client informations such as addresses, some non-sensive 
personal information,  stock information and basic stock and payment management. The payment Data Management is provided by the Stripe API.

Descrição em português ->
**Para o funcionamento correto da API voce precisara de uma conta na Stripe. Voce pode se cadastrar em https://stripe.com/br. Quando se cadastrar, acesse o link https://dashboard.stripe.com/test/apikeys, onde podera encontrar sua chave secreta e a chave publicável. Voce pode passar ambas chaves através dos VM Args no Application Properties.**

Aplicação criada com intenção de praticar interação entre classes/tabelas e integração com uma API externa de pagamento (Stripe), possibilitando salvar informações dos clientes como endereço, algumas informações 
pessoais não sensíveis, informações de stock e gerenciamento básico de estoque e pagamento.

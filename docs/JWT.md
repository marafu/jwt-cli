## O que é JSON Web Token (JWT)?

[**JSON Web Token (JWT)**](https://datatracker.ietf.org/doc/html/rfc7519) é um padrão aberto [RFC 7519](https://datatracker.ietf.org/doc/html/rfc7159) usado para transmitir informações de forma compacta entre diferentes partes no formato [JavaScript Object Notation (JSON)](https://datatracker.ietf.org/doc/html/rfc7159). É um formato compacto de representação de declarações destinado a ambientes com restrições de espaço, como cabeçalhos de autorização HTTP e parâmetros de consulta URI. Os JWTs codificam declarações para serem transmitidas como um objeto JSON que é usado como uma estrutura [JSON Web Signature (JWS)](https://datatracker.ietf.org/doc/html/rfc7515) ou como estrutura [JSON Web Encryption (JWE)](https://datatracker.ietf.org/doc/html/rfc7516). Essas estruturas permitindo que as declarações sejam assinadas digitalmente ou protegidas por integridade com um [Message Authentication Code (MAC)](https://datatracker.ietf.org/doc/html/rfc4949#autoid-17) por meio de criptografia.

---

## Cabeçalho do JWT

Todo token JWT deve começar com um cabeçalho, conforme descrito no [item 3.1 do RFC 7519 (JWT)](https://datatracker.ietf.org/doc/html/rfc7519#section-3.1). O cabeçalho define o formato do token, especifica se ele está assinado e, se for o caso, indica o algoritmo de assinatura utilizado. Além disso, estabelece diretrizes para codificação de caracteres, como UTF-8, e padroniza a manipulação de quebras de linha (CRLF vs LF) para garantir compatibilidade com a codificação Base64.  

---  

No exemplo fornecido pelo RFC, um cabeçalho típico de um token JWT é representado da seguinte forma:  

```json
{
  "typ": "JWT",
  "alg": "HS256"
}
```

A especificação do JWT permite a inclusão de outros campos além dos apresentados acima. No entanto, dois cabeçalhos são considerados especiais pela RFC e merecem destaque.  

O [item 5 do RFC 7519 (JWT)](https://datatracker.ietf.org/doc/html/rfc7519#section-5) aborda com mais detalhes os cabeçalhos de segurança do token, enfatizando dois em particular: `typ` e `cty`.  

- [5.1 **typ** (Type)](https://datatracker.ietf.org/doc/html/rfc7519#section-5.1): Esse cabeçalho indica o tipo de objeto contido no JWT. Sua função é auxiliar as aplicações a identificarem e processarem corretamente o token. A RFC recomenda que o valor desse campo seja `"JWT"` (em maiúsculas) para indicar explicitamente que o objeto segue a especificação JWT.  

- [5.2 **cty** (Content Type)](https://datatracker.ietf.org/doc/html/rfc7519#section-5.2): Esse cabeçalho define o formato estrutural do conteúdo do JWT em implementações que utilizam JWS (JSON Web Signature) ou JWE (JSON Web Encryption). O RFC detalha sua aplicação em cenários que envolvem assinaturas ou criptografia aninhada, além de fornecer diretrizes para seu uso adequado em diferentes contextos.  

---

### Operações Aninhadas  

Um token JWT pode ser assinado, criptografado ou ambos. Um **JWT aninhado** ocorre quando um token assinado é posteriormente criptografado. A ordem dessas operações é essencial: **primeiro a assinatura, depois a criptografia**.  

Embora tecnicamente seja possível inverter essa ordem, a abordagem recomendada é **assinar primeiro e criptografar depois**, pois:  

1. **Evita ataques** em que a assinatura é removida, deixando apenas a mensagem criptografada.  
2. **Garante a privacidade do signatário**, impedindo a exposição de informações sensíveis antes da criptografia.  
3. **Conforme algumas regulamentações**, assinaturas aplicadas sobre texto criptografado podem não ser consideradas válidas.  

Alguns artigos sugerem a aplicação de uma segunda assinatura após a criptografia, mas **não entrarei nesse mérito aqui**.  

O uso de tokens aninhados é um recurso comum em implementações de [JSON Web Encryption (JWE)](https://datatracker.ietf.org/doc/html/rfc7516).  

**Em publicações futuras, explorarei JWE em mais detalhes.**  

---

### Entendendo a estrutura JOSE

Tanto o [item 3.1 do RFC 7519 (JWT)](https://datatracker.ietf.org/doc/html/rfc7519#section-3.1) quanto a [seção 5 do RFC 7519 (JWT)](https://datatracker.ietf.org/doc/html/rfc7519#section-5) mencionam o header como JOSE Header e não JWT Header. Por esse motivo, explicarei o que é a estrutura JOSE e por que foi definida para uso na RFC do JWT.

O JWT e suas variantes seguem a estrutura [Javascript Object Signing and Encryption (JOSE)](https://jose.readthedocs.io/en/latest/#id8), conforme definido pelo [Grupo de Trabalho da IETF sobre JOSE](https://datatracker.ietf.org/wg/jose/about/).

Segundo a documentação, JOSE é uma estrutura que fornece um método seguro para transferir **claims** (reivindicações), como informações de autorização, entre as partes, garantindo a integridade dos dados. Para isso, JOSE disponibiliza um conjunto de especificações.

Claims são pares chave/valor, como `{"typ": "JWT"}`, que oferecem informações essenciais sobre o token para que um sistema possa aplicar o controle de acesso adequado aos seus recursos.

No contexto do [item 3.1 do RFC 7519 (JWT)](https://datatracker.ietf.org/doc/html/rfc7519#section-3.1) e da [seção 5 do RFC 7519 (JWT)](https://datatracker.ietf.org/doc/html/rfc7519#section-5), esses claims desempenham um papel fundamental na validação e restauração do token, destacando informações importantes no payload do JWT.

Além disso, o foco do [Grupo de Trabalho da IETF sobre JOSE](https://datatracker.ietf.org/wg/jose/about/) é aprimorar os aspectos criptográficos do JWT e suas variantes, incluindo a representação de chaves criptográficas por meio do [JSON Web Key (JWK)](https://www.rfc-editor.org/rfc/rfc7517).

Um JWT contém **claims** no payload que podem ser utilizados para controle de acesso a recursos, incluindo identificação do usuário, expiração do token e permissões. Um dos principais usos do JWT é como meio de autenticação e autorização, especialmente em sistemas que adotam modelos como OAuth 2.0.

Aprofundaremos mais sobre os **claims** do payload posteriormente.

#### JWK no contexto do JOSE

O [JSON Web Key (JWK)](https://www.rfc-editor.org/rfc/rfc7517) é uma estrutura JSON que representa uma chave criptográfica usada para verificar se o payload do JWT foi alterado. O uso de JWK é mais eficiente do que utilizar parâmetros individuais de chave, pois simplifica a leitura do token e reduz a complexidade da validação e restauração de um JWT. Todas as operações JWE e JWS esperam um JWK\!

---

## Payload do JWT

A segunda parte de um [**JSON Web Token (JWT)**](https://datatracker.ietf.org/doc/html/rfc7519) é o payload. Como já vimos, o payload é composto por claims e segue um formato de codificação semelhante ao do cabeçalho.

```json
{"iss":"joe",
 "exp":1300819380,
 "http://example.com/is_root":true}
```

O exemplo acima representa um conjunto de claims dentro do payload de um JWT.

### JWT Claims

De acordo com a [Seção 4 da RFC 7519](https://datatracker.ietf.org/doc/html/rfc7519#section-4), um conjunto de **JWT Claims** é um objeto JSON cujos membros contêm as informações (ou reivindicações) transmitidas pelo JWT. Cada claim dentro desse conjunto **deve possuir um nome único**, garantindo a integridade e clareza dos dados.

A especificação oficial classifica as claims em três categorias principais:

- [4.1 **Registered Claim Names** (Nomes de Claims Registrados)](https://datatracker.ietf.org/doc/html/rfc7519#section-4.1):  
  São claims padronizadas que possuem significados específicos e bem definidos. Exemplos:  
    
  - [`iss` (issuer)](https://datatracker.ietf.org/doc/html/rfc7519#section-4.1.1): Identifica o emissor do JWT.  
  - [`sub` (subject)](https://datatracker.ietf.org/doc/html/rfc7519#section-4.1.2): Representa o assunto ou "dono" do token.  
  - [`aud` (audience)](https://datatracker.ietf.org/doc/html/rfc7519#section-4.1.3): Determina o(s) destinatário(s) do token.  
  - [`exp` (expiration time)](https://datatracker.ietf.org/doc/html/rfc7519#section-4.1.4): Define a data e hora de expiração do token.  
  - [`nbf` (not before)](https://datatracker.ietf.org/doc/html/rfc7519#section-4.1.5): Indica a partir de quando o token passa a ser válido.  
  - [`iat` (issued at)](https://datatracker.ietf.org/doc/html/rfc7519#section-4.1.6): Representa a data e hora em que o token foi emitido.  
  - [`jti` (JWT ID)](https://datatracker.ietf.org/doc/html/rfc7519#section-4.1.7): Um identificador único para o token.


- [4.2 **Public Claim Names** (Nomes de Claims Públicos)](https://datatracker.ietf.org/doc/html/rfc7519#section-4.2):  
  Claims que podem ser usadas publicamente para compartilhamento de informações. Para evitar colisões de nomes, recomenda-se o uso de namespaces.

- [4.3 **Private Claim Names** (Nomes de Claims Privados)](https://datatracker.ietf.org/doc/html/rfc7519#section-4.3):  
  Claims personalizadas para atender às necessidades específicas de um sistema. Elas são acordadas entre as partes envolvidas e não devem entrar em conflito com os **Registered Claims** ou **Public Claims**.

Cada categoria de claim tem sua importância no contexto do JWT. Um artigo dedicado poderia explorar cada uma delas em detalhes, mas, para manter o foco neste conteúdo, esta visão geral serve como referência.


### JWT é inseguro?

O JWT, tecnicamente, não foi projetado para segurança, mas sim para comunicação. Portanto, é possível criar tokens [**JSON Web Token (JWT)**](https://datatracker.ietf.org/doc/html/rfc7519) sem assinatura, utilizando-os apenas como um meio de transmissão de informações\!

Também é possível criar um JWT contendo uma mensagem já criptografada dentro de uma claim específica. A RFC prevê cenários em que o conteúdo do JWT é protegido por um mecanismo externo à assinatura e/ou criptografia contida no próprio token, mas esse tópico não será abordado aqui.

[**JSON Web Token (JWT)**](https://datatracker.ietf.org/doc/html/rfc7519) não tem como foco a segurança da comunicação. No entanto, quando combinamos um JWT com um [JSON Web Key (JWK)](https://www.rfc-editor.org/rfc/rfc7517), obtemos o JWT na forma como geralmente o conhecemos.

Ou seja, o que chamamos de JWT, na verdade, é um JWT+JWK. Isso é similar ao caso do (GNU+Linux), que acabou sendo popularmente chamado apenas de Linux (exceto pelo Linus Torvalds). Da mesma forma, convencionamos falar em JWT porque, na prática, o JWT utilizado no dia a dia inclui uma assinatura para validar a integridade do payload, mesmo que essa assinatura não seja obrigatória. Ainda assim, ele não atende integralmente aos requisitos de um [JSON Web Signature (JWS)](https://datatracker.ietf.org/doc/html/rfc7515). 

**No futuro, posso aprofundar mais sobre JWS.**

---

### Assinatura do JWT

O que normalmente chamamos de **token JWT** é, na verdade, um token JWT que inclui uma **assinatura** para validar a integridade dos valores no payload — mesmo que a assinatura não seja obrigatória em sua estrutura básica.

O uso de assinatura no token JWT tem como principal objetivo garantir que o conteúdo do payload não foi adulterado. Ainda assim, o JWT da forma como o utilizamos não cumpre **todas as especificações de um JSON Web Signature (JWS)**. Por essa razão, futuramente posso me aprofundar neste tópico para explicar os detalhes de um JWS e sua aplicação.

Por enquanto, neste artigo, ficamos com a convenção e o uso mais comum: **JWT com assinatura**, que atende bem para cenários de validação de integridade em sistemas de autenticação e autorização.

A assinatura é feita por meio de uma chave criptográfica passada em tempo de criação, gerando a assinatura do payload para evitar modificações. Assim, conseguimos determinar se o token é válido mesmo em uma aplicação stateless.

---

### Formato de um JWT

O **JSON Web Token (JWT)** segue uma estrutura padronizada composta por três partes principais, separadas por pontos (`.`). Cada uma dessas partes tem um papel específico na construção do token e na transmissão segura das informações.  

Abaixo, detalhamos cada uma dessas partes e seu propósito dentro da estrutura de um JWT:

1. **Header** (Cabeçalho): Contém metadados sobre o token, como o algoritmo de assinatura usado.  
2. **Payload** (Carga útil): Contém as informações (claims) que você deseja transmitir, como ID do usuário ou permissões.  
3. **Signature (Optional)** (Assinatura Opcional): Garante a integridade do token usando JWK como uma chave pública/privada ou segredo para converter JWT em JWS e/ou JWE.

#### Exemplo de Estrutura de JWT pura

Conforme explicado, o JWT em si não fornece proteção contra adulteração de valor, podendo ser gerado apenas passando os headers e, no payload, os claims.

```
header.payload
```

#### Exemplo de Estrutura de JWT com JWK transformando-o em JWS ou JWE

Em um JWT assinado ou JWS, a própria estrutura do token fornece proteção contra adulteração de valor, sendo gerado ao passar os headers, os claims no payload e a assinatura do payload como último parâmetro.

```
header.payload.signature
```

O JWT tem as seguintes características:

- **Compacto:** Pode ser transmitido como um cabeçalho HTTP ou parâmetro de URL.  
    
- **Seguro:** O conteúdo está protegido contra adulteração com uma assinatura digital (usando HMAC ou RSA).  
    
- **Não é criptografado por padrão:** Qualquer pessoa que possuir o JWT poderá ler o conteúdo do `payload` (a menos que seja combinado com algo como **JWE**). Isso significa que ele **não deve conter informações sensíveis.**

Você pode obter os dados do payload facilmente pegando apenas o conteúdo entre os dois pontos finais (`.`) e decodificando-o em Base64, usando um navegador moderno ou um terminal shell de um sistema Unix-like (Unix, GNU/Linux, macOS, WSL, etc.).

#### Comandos de decodificação de um **payload** JWT

O **payload** de um **JSON Web Token (JWT)** pode ser facilmente decodificado para visualizar as informações contidas nele. Como o JWT **não é criptografado por padrão**, qualquer pessoa que possua o token pode acessar os dados do payload. Isso significa que **não é seguro armazenar informações sensíveis no JWT**, a menos que seja utilizado um mecanismo adicional, como **JWE (JSON Web Encryption)**.

Como o JWT usa **Base64Url** para codificar seu conteúdo, podemos utilizar ferramentas comuns para decodificá-lo.

##### Decodificando o payload de um JWT em um navegador
```javascript
// Formato do JWT: header.payload.signature

const jwt = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX2lkIjoxMjMsImV4cCI6MTcwMDAwMDAwMH0.suaAssinaturaAqui";

// Extrai o payload e decodifica de Base64Url para string JSON
const payload = JSON.parse(atob(jwt.split(".")[1]));

console.log(payload);
```

##### Decodificando o payload de um JWT em um shell (Linux/macOS)
```sh
# Formato do JWT: header.payload.signature

TOKEN="eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX2lkIjoxMjMsImV4cCI6MTcwMDAwMDAwMH0.suaAssinaturaAqui"

echo $TOKEN | cut -d "." -f2 | base64 -d | jq
```

##### Decodificando o payload de um JWT em PowerShell (Windows)
```powershell
# Formato do JWT: header.payload.signature

$TOKEN = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX2lkIjoxMjMsImV4cCI6MTcwMDAwMDAwMH0.suaAssinaturaAqui"

$TOKEN.Split(".")[1] | %{[System.Text.Encoding]::UTF8.GetString([System.Convert]::FromBase64String($_))}
```

Esses exemplos mostram como extrair e decodificar o payload de um JWT sem verificar sua assinatura. Para validar a autenticidade do token, seria necessário um processo adicional de verificação da assinatura usando a chave correta.  


## 🏛️ Estrutura do Projeto com Padrões de Arquitetura

O sistema foi desenvolvido seguindo **padrões arquitetônicos** que garantem **baixo acoplamento**, **alta coesão** e **separação de responsabilidades**, promovendo um código organizado, escalável e testável.

A implementação utiliza **Java** como linguagem principal devido à sua ampla adoção e suporte para bibliotecas confiáveis, como [**`JJWT`](https://github.com/jwtk/jjwt)**. No entanto, o conceito de **JWT é agnóstico à tecnologia** e pode ser aplicado em qualquer linguagem de programação.

A estrutura do projeto é modular, com pacotes bem definidos que encapsulam **Value Objects**, contratos (interfaces) e lógica de negócio, garantindo **clareza e manutenibilidade**. Além disso, a **testabilidade** é assegurada por meio de testes unitários focados nas principais funcionalidades do sistema.

---

### ⚠️ Aviso Importante

Os códigos apresentados neste artigo têm finalidade **estritamente educacional** e foram desenvolvidos para **demonstrar conceitos técnicos** sobre JWT e estruturas correlatas. **Não devem ser utilizados em produção**, pois não contemplam medidas essenciais de segurança e melhores práticas, como:

- **Gestão de Chaves Seguras**: As chaves de assinatura são definidas diretamente no código, sem uso de mecanismos seguros de armazenamento, como AWS Secrets Manager ou HashiCorp Vault.  
- **Proteção contra Vulnerabilidades**: O código não implementa validação ou sanitização robusta de entradas, podendo estar suscetível a ataques.  
- **Monitoramento e Logs Seguros**: O nível de logging não é adequado para produção e pode expor informações sensíveis.  
- **Tratamento de Exceções**: Não há cobertura completa para falhas inesperadas em um ambiente real.  

Se for necessário implementar JWT em um sistema real, siga **boas práticas de segurança, desenvolvimento e arquitetura**, além de utilizar bibliotecas e ferramentas confiáveis. Para ambientes de produção, considere:

- Armazenamento seguro de chaves e segredos.  
- Validação e sanitização rigorosa de entradas.  
- Uso de tokens alinhados ao seu sistema de autenticação (como OAuth2).  
- Aplicação de padrões de segurança reconhecidos, como os da **OWASP**.  

**Lembre-se: o foco aqui é o aprendizado**, e a implementação demonstrada serve apenas para entender o funcionamento e a estrutura do JWT. ✨

---

## 🗂️ Organização do Projeto

1. **`exceptions`**:  
   - Contém classes para tratamento de erros específicos do domínio, como `AuthenticationException`, `DomainException`, `InvalidKeyUsedException`, `TokenExpiredException`, `TokenMalformedException` e `TokenUnsupportedException`.  

2. **`factory`**:  
   - A classe `JwtFactory` centraliza a criação de objetos JWT, facilitando a configuração e a manutenção da lógica de geração de tokens.  

3. **`implementations`**:  
   - `GsonJSON` e `JJWT` fornecem implementações concretas para serialização JSON e manipulação de JWT, desacopladas do domínio por meio de interfaces.  

4. **`interfaces`**:  
   - Define contratos essenciais (`JSON`, `JWT` e `Logger`), permitindo flexibilidade e facilitando a substituição de implementações sem impacto na lógica principal.  

5. **`usecases`**:  
   - Contém os casos de uso `GenerateJWT` e `ValidateJWT`, encapsulando as regras de negócio relacionadas à criação e validação de tokens JWT.  

6. **`utils`**:  
   - Inclui utilitários como `SoutLogger`, que centraliza a lógica de logging.  

7. **`valueobjects`**:  
   - Classes como `Squad` e `Title` implementam o padrão **Value Object**, garantindo imutabilidade e encapsulando regras de negócio, como validação e sanitização.  

8. **`test/unitary`**:  
   - Contém testes unitários para `JJWT`, `Squad` e `Title`, garantindo a conformidade das implementações com os contratos definidos.  



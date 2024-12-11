
## Boas Práticas API

Em DELETE podemos retornar 204 (No Content) se o recurso foi excluído com sucesso

@DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity excluir(@PathVariable Long id) {
        var medico = repository.getReferenceById(id);
        medico.excluir();

        return ResponseEntity.noContent().build();
    }

Todos os métodos irão retornar ResponseEntity

@GetMapping("")
public ResponseEntity<Page<DadosListagemMedico>> buscarTodos(@PageableDefault(size = 10, sort = {"nome"}) Pageable paginacao) {
    var page = repository.findAllByAtivoTrue(paginacao).map(DadosListagemMedico::new);
    return ResponseEntity.ok(page);
}

@PutMapping("")
@Transactional
public ResponseEntity atualizar(@RequestBody @Valid DadosAtualizacaoMedico dados) {
    var medico = repository.getReferenceById(dados.id());
    medico.atualizarInformacoes(dados);

    return ResponseEntity.ok(new DadosDetalhamentoMedico(medico));
}

O POST retorna 201 

@PostMapping("")
    @Transactional
    public ResponseEntity cadastrar(@RequestBody @Valid DadosCadastroMedico dados, UriComponentsBuilder uriBuilder) {        
        var medico =  new Medico(
                null, 
                Boolean.TRUE,
                dados.nome(), 
                dados.email(), 
                dados.crm(),
                dados.telefone(), 
                dados.especialidade(), 
                new Endereco(
                    dados.endereco().logradouro(), 
                    dados.endereco().bairro(), 
                    dados.endereco().cep(),
                    dados.endereco().cidade(), 
                    dados.endereco().uf(),
                    dados.endereco().complemento(),
                    dados.endereco().numero()
                )
            );
        
        repository.save(medico);

        var uri = uriBuilder.path("/medicos/{id}").buildAndExpand(medico.getId()).toUri();
        return ResponseEntity.created(uri).body(new DadosDetalhamentoMedico(medico));
    }

Nova URI
 Método para detalhar o médico.
 @GetMapping("/{id}")
    public ResponseEntity detalhar(@PathVariable Long id) {
        var medico = repository.getReferenceById(id);       

        return ResponseEntity.ok(new DadosDetalhamentoMedico(medico));
    }

## Lidando com erros

Movendo classes para o "Dominio"

pripriedade para não retornar stacktrace na API server.error.include-stacktrace=never

Classe para tratar erros
@RestControllerAdvice
public class TratadorErros {
    
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Void> tratarEntityNotFoundException() {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(MethodArgumentNoValidExcpetion.class)
    public ResponseEntity<Void> tratarMethodArgumentNoValidExcpetion(MethodArgumentNoValidExcpetion exception) {
        var erros = exception.getFieldErrors();
        return ResponseEntity.badRequest().body(
            erros.strem()
                .map(DadosErroValidacao::new)
                .toList()
        );
    }

    private Record DadosErroValidacao(String campo, String mensagem) {
        public DadosErroValidacao(FieldError error) {
            this(
                erro.getField(),
                erro.getDefaultMessage()
            )
        }
    }
}

Centralizando mensagens de erro com arquivo ValidationMessagens

nome.obrigatorio=Nome é obrigatório
email.obrigatorio=Email é obrigatório
email.invalido=Formato do email é inválido
telefone.obrigatorio=Telefone é obrigatório
crm.obrigatorio=CRM é obrigatório
crm.invalido=Formato do CRM é inválido
especialidade.obrigatoria=Especialidade é obrigatória
endereco.obrigatorio=Dados do endereço são obrigatórios
id.obrigatorio=O ID deve ser informado

a classe fica assim

public record DadosAtualizacaoMedico(
    
    @NotNull(message = "{id.obrigatorio}")
    Long id,

    String nome,
    String telefone,
    DadosEndereco endereco
) {}

## Spring Security

Nova dependência

<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-security</artifactId>
</dependency>

Existem diversos algoritmos de hashing que podem ser utilizados para fazer essa transformação nas senhas dos usuários, sendo que alguns são mais antigos e não mais considerados seguros hoje em dia, como o MD5 e o SHA1. Os principais algoritmos recomendados atualmente são:

Bcrypt
Scrypt
Argon2
PBKDF2

nova migration e entidade jpa

CREATE TABLE usuarios(
    id            INTEGER       PRIMARY KEY AUTOINCREMENT,
    login         VARCHAR(100)  NOT NULL UNIQUE,
    senha         VARCHAR(255)  NOT NULL
);

Classe para serviço de autenticação

@Service
public class AutenticacaoService implements UserDetailsService {

    @Autowired
    private UsuarioRepository repository;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        return repository.findByLogin(login);
    }
}

Classe de configuração

@Configuration
@EnableWebSecurity
public class SecurityConfigurations {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        return http.csrf(csrf -> csrf.disable())
            .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}

Controller para login
@RestController
@RequestMapping("/login")
public class AutenticacaoController {
    
    @Autowired
    private AuthenticationManager manager;

    @PostMapping("")
    public ResponseEntity efetuarLogin(@RequestBody @Valid DadosAutenticacao dados) {
        var token = new UsernamePasswordAuthenticationToken(dados.login(), dados.senha());
        var authenticate = manager.authenticate(token);

        return ResponseEntity.ok().build();
    }
}

adicionar usuário para fazer login

Entidade usuário
@Table(name = "usuarios")
@Entity(name = "Usuario")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Usuario implements UserDetails {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String login;
    private String senha;
    
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }
    @Override
    public String getPassword() {
        return senha;
    }
    @Override
    public String getUsername() {
        return login;
    }
}

## JSON Web Token

<!-- JWT -->
<dependency>
    <groupId>com.auth0</groupId>
    <artifactId>java-jwt</artifactId>
    <version>4.4.0</version>
</dependency>
<!-- JWT -->

Classe que gera o token

@Service
public class TokenService {

    @Value("{api.security.token.secret}")
    private String secret;

    public String gerarToken(Usuario usuario) {
        try {
            var algoritmo = Algorithm.HMAC256(secret);
            return JWT.create()
                .withIssuer("API Voll.med")
                .withSubject(usuario.getLogin())
                .withClaim("nomeUsuario", usuario.getUsername())
                .withExpiresAt(dataExpiracao())
                .sign(algoritmo);
        } catch (JWTCreationException e) {
            throw new RuntimeException("Erro ao gerar token jwt", e);
        }
    }

    private Instant dataExpiracao() {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }
}

AJustando classe que autentica
@RestController
@RequestMapping("/login")
public class AutenticacaoController {
    
    @Autowired
    private AuthenticationManager manager;

    @Autowired
    private TokenService tokenService;

    @PostMapping("")
    public ResponseEntity efetuarLogin(@RequestBody @Valid DadosAutenticacao dados) {
        var token = new UsernamePasswordAuthenticationToken(dados.login(), dados.senha());
        var authenticate = manager.authenticate(token);

        return ResponseEntity.ok(tokenService.gerarToken((Usuario)authenticate.getPrincipal()));
    }
}


Configura o properties

api.security.token.secret=${JWT_SECRET: 123456789}

COloca dentro de .env (JWT_SECRET="MinhaSenhaLinda")

## Controler de acesso

classe que configurará o filtro

@Component
public class SecurityFilter extends OncePerRequestFilter{

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        
            filterChain.doFilter(request, response);
    }
}

Implementar
@Bean
public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    return http.csrf().disable()
        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and().authorizeHttpRequests()
        .requestMatchers(HttpMethod.POST, "/login").permitAll()
        .requestMatchers(HttpMethod.DELETE, "/medicos").hasRole("ADMIN")
        .requestMatchers(HttpMethod.DELETE, "/pacientes").hasRole("ADMIN")
        .anyRequest().authenticated()
        .and().addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
        .build();
}

Nessa aula, você aprendeu como:
Funcionam os Filters em uma requisição;
Implementar um filter criando uma classe que herda da classe OncePerRequestFilter, do Spring;
Utilizar a biblioteca Auth0 java-jwt para realizar a validação dos tokens recebidos na API;
Realizar o processo de autenticação da requisição, utilizando a classe SecurityContextHolder, do Spring;
Liberar e restringir requisições, de acordo com a URL e o verbo do protocolo HTTP.
# spring-security-sample

## 스프링 시큐리티 (inflearn 백기선님 강의)

[//]: # (SecurityConfig.class )

[//]: # (  - spring security 설정 class)
  
## 스프링 시큐리티 아키텍처(Spring Security Architecture)

## 순서
1. 어느 부분에서 Authentication은 저장되는가
2. AuthenticationManager를 통해서 인증된다.(Authentication) 
3. 결과로 나온 Authentication을 다시 SecurityContextHolder에 저장은 어디서?
4. Authentication은 UsernamePasswordAuthenticationFilter, SecurityContextPersisenceFilter 에서 저장되는데 그러면 이 필터들은 어디에서 등록되는가?
5. WebSecurityConfigurerAdapter를 상속받은 SecurityConfig에 설정한 정보가 Filter에 등록되는데 어떻게 url로 요청하면 FilterChainProxy 들어왔는가?
6. DelegatingFilterProxy를 통해서 FilterChainProxy에 들어온다.
7. 권한 확인(인가)은 어디서 이루어지는가? AccessDecisionManager
8. AccessDecisionManager는 어디서 사용하고 있는가? FilterSecurityInterceptor
9. 인증과 인가처리에 발생한 에러가 어떻게 처리되는지? ExceptionTranslationFilter

### SecurityContextHolder와 Authentication
- Spring Security 핵심 객체
  #### SecurityContextHolder
   - SecurityContext 제공, 기본적으로 ThreadLocal를 사용
   - 한 Thread에 특화되어 있는 정보
   - application 어디에서나 꺼내서 사용할 수 있다.
   - SampleService.findSercurityContextHolder() 참고 

  #### SecurityContext
   - Authentication 제공.

  #### Authentication
   - Principal과 GrantAuthority 제공.

  #### Principal
   - "누구"에 해당하는 정보
   - UserDetailsService에서 리턴한 그 객체(AccountService.class 참고)
   - 객체는 Userdetails 타입

  #### GrantAuthority
   - "ROLE_USER", "ROLE_ADMIN"등 Principal이 가지고 있는 "권한"을 나타낸다.
   - 인증 이후, 인가 및 권한 확인할때 이 정보를 참조한다.

  #### UserDetails
   - 어플리케이션이 가지고 있는 유저 정보와 스프링 시큐리티가 사용하는 Authentication 객체 사이의 어댑처

  #### UserDetailsService
   - 유저 정보를 UserDetails 타입으로 가져오는 DAO 인터페이스
   - 구현은 마음대로

### AuthenticationManager(인증할 때 사용)와 Authentication
- 스프링 시큐리티에서 인증(Authentication)은 AuthenticationManager가 한다.

### Authentication과 SecurityContextHolder
   - UsernamePasswordAuthenticationFilter
       * 폼 인증을 처리하는 시큐리티 필터
       * 인증된 Authentication 객체를 SecurityContextHolder에 넣어주는 필터
       * SecurityContextHolder.getContext().setAuthentication(authentication)

   - SecurityContextPersisenceFilter
      * SecurityContext를 HTTP session에 캐시(기본 전략)하여 여러 요청에서 Authentication을 공유할 수 있는 공유 필터
      * SecurityContextRepository를 교체하여 세션을 HTTP session이 아닌 다른 곳에 저장하는 것도 가능하다.
      * 같은 session에서만 공유된다.
  
### 스프링 시큐리티 Filter와 FilterChainProxy
- 스프링 시큐리티 필터는 FilterChainProxy가 호출한다.
- 여기에 등록되는 필터들은 SecurityConfig에서 설정한 정보가 SecurityFilterChain을 만드는데 사용된다.(FilterChainProxy.getFilters에 SecurityFilterChain)
- SecurityConfig 설정에 따라서 등록되는 Filter에 개수가 달라진다.

### DelegatingFilterProxy와 FilterChainProxy
- 둘 다 서블릿 Filter 용도가 다르다.
  #### DelegatingFilterProxy
   - 일반적인 서블릿 필터(위에서 살펴본 다른 필터들과 같은 서블릿 필터지만 서블릿에 직접 등록되는 필터)
   - 서블릿 필터 처리를 스프링에 들어있는 빈으로 위이함고 싶을 때 사용하는 서블릿 필터
   - 타겟 빈 이름을 설정한다.
   - 스프링 부트 없이 스프링 시큐리티 설정할 때는 AbstractSecurityWebApplicationInitializer를 사용해서 등록
   - 스프링 부트를 사용할 때는 자동으로 등록된다. (SecurityFilterAutoConfiguration)

  #### FilterChainProxy
   - 보통 "springSecurityFilterChain" 이라는 이름의 빈으로 등록된다.

### AccessDecisionManager
- 인가할 때 사용
  #### Access Control 결정을 내리는 인터페이스로, 구현체 3가지를 기본적으로 제공
    - AffirmativeBased : 여러 Voter중에 한명이라도 허용하면 허용, 기본 전략
    - ConsensusBased : 다수결
    - UnanimousBased : 만장일치

  #### AccessDecisionVoter
    - 해당 Authentication이 특정한 Object에 접근할 때 필요한 ConfigAttributes를 만족하는지 확인한다.
    - WebExpressionVoter : 웹 시큐리티에서 사용하는 기본 구현체, ROLE_Xxxx가 매치하는지 확인
    - RoleHierarchyVoter : 계층형 ROLE 지원, ADMIN > MANAGER > USER

### FilterSecurityInterceptor
- AccessDecisionManager를 사용하여 Access Control또는 예외 처리하는 필터.
- 대부분의 경우 FilterChainProxy에 제일 마지막 필터로 들어있다.

### ExceptionTranslationFilter
- 필터 체인에서 발생하는 AccessDeniedException과 AuthenticationException을 처리하는 필터
  
  #### AuthenticationException
    - AuthenticationEntryPoint 실행
    - AbstractSecurityInterceptor 하위 클래스(예, FilterSecurityInterceptor)에서 발생하는 예외만 처리
    - 그렇다면 UsernamePasswordAuthenticationFilter에서 발생한 인증 에러는? UsernamePasswordAuthenticationFilter 자체에서 처리한다.

  #### AccessDeniedException
    - 익명 사용자라면 AuthenticationEntryPoint 실행
    - 익명 사용자가 아니라면 AccessDeniedHandler에게 위임


## 정리
DeligatingFilterProxy -> FilterChaninProxy -> 시큐리티 필터 목록들(체인들은 어떻게 만들어지는가? WebSecurity, HttpSecurity를 이용해서 만들어진다. 참고 - WebSecurity 주석)
-> 인증 관련된 객체(AuthenticationManager) -> 인가 관련된 객체(AccessDecisionManager)
-> SecurityContextHolder -> SecurityContext -> Authentication -> Pricipal, GrantAuthority

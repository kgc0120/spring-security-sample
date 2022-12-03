# spring-security-sample

## 스프링 시큐리티 (inflearn 백기선님 강의)

SecurityConfig.class 
  - spring security 설정 class
  
## Spring Security Architecture
### SecurityContextHolder와 Authentication

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

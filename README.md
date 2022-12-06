# Spring-blog
스프링 boot과 jpa를 활용한 blog 프로젝트

## project architecture
> 언어 : java
  화면개발 : STS
  DB : mySql
  라이브러리 : JPA, Security, jstl
  개발 기간 : 2022-11-20 ~ 2022-12-31

### 2022-11-20
> 스프링 부트를 활용한 블로그 프로젝트를 시작한다.(jpa, security, jstl)의 라이브러리를 가져왔고 DB로는 mysql을 사용할 것이다.
 스프링 부트를 처음 써보는 것은 아니지만 매번 restapi따로 security따로 하다보니 합쳐서 하나의 프로젝트로 구현하고 싶어 시작했다.
 이번 기회로 스프링 부트 및 jpa사용법을 완벽하게 이해했으면 좋겠다.
 
### 2022-11-28
> 기본 화면 개발과 전통적인 방법으로 기존에 로그인하는 법과 회원가입을 해봤다. ajax를 활용했고 그냥 DB에 붙어 수정하고 저장하면 되니 간단하게 할 수 있었다. 그러나
 이제는 spring security를 활용할 것이니 url주소와 여러 설정들을 손 볼 예정이다. jpa를 활용한 방법은 정말 편하고 효율적이지만 가끔 테이블이 많아지면 감당할 수 있을까
 걱정이 된다. 대처방법 있다고 하는데 학습해야 겠다.
 
### 2022-12-01
> 스프링 시큐리티를 활용한 로그인 서비스를 구현해 보았다. 역시 생각했던만큼 복잡하고 어려웠지만 예전에 학습한 적이 있어 그리 애먹진 않았던거 같다. 스프링 tablib가 자동완성 안되는 것과
  더이상 WebSecurityConfigurerAdapter가 지원하지 않아 SecurityFilterChain을 쓰는 것이 시간을 잡아먹었지만 그래도 구글링하는 실력이 느는거 같아 만족스럽다.
  시큐리티 관련해서는 따로 정리해서 학습할 예정이다. 뭔가 편리해졌지만 새로운 것인만큼 공부해야할 것이 산더미!!

### 2022-12-02
> 스프링 시큐리티를 활용해서 비밀번호를 해쉬로 변환하였다. 현업에 근무하면서 개인정보는 암호화가 필수이며 단방향으로 쓸지 양뱡향으로 쓸지 구분했어야하는게
  생각났다. BCryptPasswordEncoder를 써서 만들어놨던 config에 설정해놓고 빈에 등록해주면 ioc로 스프링에서 관리하게 된다. 이렇게 관리된 것을 DI로
  편하게 쓸 수 있어서 spring의 2가지 특징을 한번에 공부할 수 있었다. 그러나 보안관련이라 그런가 경로관련해서 설정하는 것이 상당히 까다로웠다.
    

### 2022-12-04
> 스프링 시큐리티를 활용해서 시큐리티에서 제공하는 id, password가 아닌 회원가입하여 DB에 저장된 User정보를 가져와 로그인할 수 있게 로직을 구성하였다.
  loginProcessingUrl사용해서 가로챈 유저정보를 
  protected void configure(AuthenticationManagerBuilder auth) throws Exception{
	auth.userDetailsService(principalDetailService).passwordEncoder(encodePWD());
  } 사용해서 패스워드를 같은 해쉬로 암호화해서 DB에 있는 해쉬랑 비교할 수 있다. 
  PrincipalDetailService를 생성해서 UserDetailsService를 상속해 인터페이스를 구현한다. userRepository에 username을 select할 수 있는 optional을 주고 생성한다음
  UserDetails으로 함수를 구현하고 return new PrincipalDetail(principal); 반환해준다. 이때 PrincipalDetail에는 UserDetails의 형식으로 반환해주어야 하니 내부 로직을 생성해준다!
  진짜 어렵고 양도 많았고 심지어 중간에 DI를 해주지않아 로그인할 때 계속 null이 반환되어 exception이 떨어져 1시간넘게 고생했다. 다하고나니 어떤 로직인지 머리로는 이해되지만 다시하라고하면
  조금 헤맬거 같다. 기본에 충실하는것이 중요할 거같다! 상속할때 extends와 implements도 구분하자 왜쓰는지 알아두기!!

### 2022-12-05
> 블로그 글쓰기 및 글 목록보기 페이징기능을 구현했다. main페이지에서 글 목록을 보여줄 것이기 때문에 @PageableDefault(size = 3, sort = "id", direction = Sort.Direction.DESC) Pageable pageable를 
   사용했고 boardService.글목록(pageable)사용해 pageable을 변수로 넣어주고 page값으로 return을 받았다. return boardRepository.findAll(pageable) 받을때도 간단하게 findAll에 pageable값으로 호출하면
   page값으로 반환할 수 있다. 또한 글쓸때 ui를 조금 이쁘게 가져가고 싶어서 summernote라이브러리를 가져와 사용했다. 상당히 맘에 들고 sequrity부분이 마무리되니 간단한 crud로 구현되는거같아 가볍게
   느껴진다.

### 2022-12-06
> 블로그 글쓰기의 마무리인 상세보기, 삭제하기, 수정하기 등을 구현했다. ajax로 넘기는 데이터는 apicontroller로 넘겨주기로했다. CRUD의 전과정을 할 수 있었고 ui나 간단한 것들만 진행하면 될 거 같다.
   전체 블로그의 틀은 구현했고 이제 라이브러리나 opensource들을 사용해 풍부하게 만들어 볼 예정이다. 다음에는 회원수정을 진행할 것이다.




  
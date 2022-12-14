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

### 2022-12-07
> 이번에는 회원정보 수정을 구현했다. 간단하게 joinForm.jsp를 복사 및 수정해서 updateForm으로 만들었고 필요한 데이터만 ajax로 json으로 만들어 넘겨줬다. restful하게 로직을 구현하고 스프링 boot의 큰 장점인
   더티체킹을 통해 하나의 @Transactional을 써서 자동으로 메서드가 끝나면 커밋되는 것을 한번 더 확인했다. 이제는 rest도 금방 구현할 수 있어 자신감이 붙었다. 계속 회원수정을 구현하고 카카오 로그인도 구현할 계획이다.

### 2022-12-09
> 회원정보를 수정하면서 수정이 완료되고 다시 돌아가면 저장된 정보를 불러오기 위해 세션을 update해줘야한다는걸 알게되었다. AuthenticationManager를 통해 Authentication객체를 생성해주고
   AuthenticationManager 객체 활용하기 위해 SecurityConfig에 생성하고 @Bean으로 등록해서 IoC로 관리하게 한다. AuthenticationManager 객체를 활용해 수정된 user정보에서 username과 password로 새로운 토큰을 만들어 Authentication객체를 생성.
   HttpSession의  SecurityContextHolder안에 SecurityContext안에 있는 Authentication객체를 AuthenticationManager를 통해 내가 새로만든 Authentication객체로 수정해준다! 진짜 이해하기 어려웠지만 이제는 하면서 이해력이 높아지는 것 같다.
   마지막에 json으로 username을 보내지않아 session에 저장이 안돼 당황했지만 디버깅하면서 찾아냈고 설정하는데 크게 어렵지 않았다. 덕분에 session을 update하는 것에 크게 관심을 가진 시간이었다. 카카로 로그인까지 마치고 싶었는데
   생각보다 길어져서 카카오 개발자 센터에서 애플리케이션으로 추가해서 사용할 서비스들의 환경설정을 진행했다. 그동한 공부한 Rest api가 활용될 순간이라 정말 기대된다.

### 2022-12-11
> 카카오로그인 서비스를 하기위해 사전설정은 카카오 개발센터에서 다 진행했고 url을 통해 전달받은 authorization 정보들을 oauth에 요청하여 oauthToken을 받았고 해당 토큰을 가지고 사용자 정보 조회를 위한 데이터를 카카오에 요청했다.
   그리고 해당 유저의 정보를 내부 DB정보와 중복이 있는지 체크하고 없으면 새로 DB에 로그인하며 저장되는 것을 확인했고 로그인을 처리한다음 redirect로 메인사이트에 처리를 넘겨주었다. 그러나 이번 과정을 진행하면서 정말 많은 코드들을 수정하고 작성하는
   과정에서 갑자기 spring security로 로그인정보가 세션에 저장이 안된다... principal이 계속 null로 뜨고 분명히 저장은 했는데 security에서 로그인을 처리해주지 못하고 있는것 같다 security는 건들지 않았는데... 오늘 거의 5시간을 헤메고 있고
   도저히 답이 나오지 않아 내일 수정하기로했다. 분명 건들지 않았는데 왜 그러지... 한번 알아봐야겠다...

### 2022-12-13
> 2일동안 죽어라 코드만 봤다. 구글링해서 알려주는 방법들을 다해보고 새로 열어보고 했는데 계속 세션에 불러와지지 않는것이다. 그래서 다른 브라우저로 열어봐야지까지 갔는데 놀랍게도 열린것이다. 크롬에서는 왜안됐지 확인하다가
  쿠키를 허용을 안하고 막은 상태로 있던것이다....현업에서도 이 부분은 자주했었는데 집이라고 생각을 못했다니 진짜 기쁘고도 허탈하다. 카카오 로그인도 잘됐고 덕분에 스프링 시큐리티 모든것을 돌아보고 온 것 같다. 이제 댓글이랑 조회수 작업을 하고
  이 프로젝트를 마무리 할 수 있게 됐다. 정말 다행이다... 여기에 리액트로 화면 조정도 해보고 싶고 jwt토큰을 이용해서 웹을 다뤄보고도 싶다.

### 2022-12-14
> 댓글 작성까지 마무리했다. 이번엔 진행하면서 Reply테이블과 Board 테이블의 연관관계를 재정립했다. 그 이유는 Board 테이블을 부를 때 모델에 Reply들을 가져오는데 이때 Reply안에도 Board가 id로 묶여있어서 무한루프에 걸리게 된다.
   Jackson에선 이를 방지하고자 @JsonIgnoreProperties어노테이션을 지원하고 이를 모델에서 사용하면 외래키를 불러올때 해당 객체를 제한할 수 있다. 이를 활용해 게시글에 댓글다는 것을 완료했다. 이제 출입할 때 조회수 올리는 것과 실제 블로그처럼
   좋아요나 이런 마크들을 달고 숫자로 표현할 생각이다. 막힌부분이 뚤리니 살거같다!!
  
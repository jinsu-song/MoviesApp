

프로젝트 이름 : MoviesApp




설정
- api 호출할 때 header에 들어가는 Auth를 local.propertyies 파일로 따로 저장해서 관리하기에 
https://developer.themoviedb.org/reference/intro/getting-started 사이트로 가셔서 clone 받은 소스의 local.properties 파일에 Auth를 
API_KEY="Bearer eyJhbGciOiJIUzI1N~~~~" 이렇게 기입해 주시길 바랍니다.



  2024.05.27 수정목록
1. 패키지 구조 변경
2. BaseActivity, BaseFragment 생성 및 사용하지 않는 import문 삭제
3. 함수명 camelCase로 변경
4. API_KEY local.properties 파일에 저장
5. README.md 파일 생성
6. HomeFragment와 SearchFragment의 중복코드 BaseFragment로 공통화 작업
7. 즐겨찾기 화면 구현 with Room db Library
8. 각 클래스의 멤버 접근제어자 정리
9. Application 클래스의 메서드 MainViewModel로 옮김
  
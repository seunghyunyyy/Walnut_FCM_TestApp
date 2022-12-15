# Walnut_FCM_TestApp

테스트 앱

1. 파이어베이스 가입
2. 파이어베이스에 프로젝트 추가
3. 프로젝트에 앱 등록
4. Android 앱에 Firebase 추가 (파이어베이스 등록 단계에 설명 있음)
5. Firebase SDK 추가 -> 파이어베이스 사이트에 나와있는건 최신화가 되지 않아서 안드로이드 스튜디오 상단바에서 /tools/firebase/Cloud Messaging-> Set up Firebase Cloud Messaging을 통해 추가하는 것이 편함.
6. 안드로이드 앱과 파이어베이스가 연결 되었는지 확인
7. 안드로이드 스튜디오 상단바에서 /tools/firebase/Authentication -> Authenticate using & custom authentication system을 통해 sdk 등록


로그인/회원가입
회원가입 시 파이어베이스 Authentication에 등록이 됨.
로그인 시 자동로그인 체크를 하면 기기 내부에 id와 password를 저장(SaveSharedPreference.java)하여 자동으로 로그인
로그아웃 시 기기에 저장된 정보를 삭제

알림내역
앱 실행 후 로그인(또는 자동 로그인) 하면 보이는 알림내역 버튼을 통해 내가 받은 알림 내역들을 확인할 수 있음.
MessagesListActivity가 미리 작성해둔 HttpUtillity를 통해 HTTP통신을 해서 데이터베이스로부터 알림 내역들을 가져온 후 listView로 화면에 보여줌.


ex) final String url = "http://192.168.50.192:8080/push/v1"이라고 가정하면,
HttpUtility.getRest(url + "/messages", "token", SaveSharedPreference.getString(getApplicationContext(), "token"));
-> SaveSharedPreference에 저장한 token값을 string값으로 가져와 http://192.168.50.192:8080/push/v1/messages에 token을 Params로 전달하여 get함.

각각의 아이템을 클릭하면 클릭한 아이템에 해당하는 msgId를 intent를 통해 MessageInfoActivity로 전달하고, MessageInfoActivity에서 HttpUtility.getRest(url + "/messages/" + ID, "", "")를 통해 해당 ID의 메세지 정보들을 보여준다.

* 승인/거부 미구현

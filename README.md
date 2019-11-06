# ImageSearch
카카오 이미지 검색 API를 구현한 안드로이드 데모 앱입니다.

MVVM(Model-View-ViewModel) 패턴과 AAC(Android Architecture Components)를 활용한 안드로이드 아키텍처를 구현했습니다.
1. UI를 그리는 뷰와, 뷰로부터 이벤트를 감지하고 API와 통신하는 뷰모델을 분리시켰습니다.
2. Retrofit2와 Rx를 활용한 API로의 요청/수신 로직을 구현했습니다.
3. 뷰모델 내의 비동기 수신 데이터를 관리하는 LiveData가 존재하고 뷰에서는 해당 LiveData를 관찰하고 있습니다.	이렇게 비동기로 수신받은 데이터를 구독하고 있는 뷰에 전달하여 뷰를 변경하도록 했습니다.

## Language
- App : Kotlin

## Libraries
- JetPack / AAC
  - [Databinding](https://developer.android.com/topic/libraries/data-binding/index.html)
  - [LiveData](https://developer.android.com/topic/libraries/architecture/livedata)
  - [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel)

- Reactive
  - [RxAndroid](https://github.com/ReactiveX/RxAndroid)
  - [RxJava](https://github.com/ReactiveX/RxJava)
  - [RxBinding](https://github.com/JakeWharton/RxBinding)

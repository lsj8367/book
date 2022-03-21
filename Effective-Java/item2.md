# 생성자에 매개변수가 많다면 Builder를 고려하라

앞에서 살펴봤던 정적 팩토리 메소드와 생성자에는 제약이 하나 있는데,

필드 변수가 많아질수록 대응하기가 힘들다는 점이다.

```java
public class User {
    private String firstName;
    private String lastName;
    private String address;
    private String zipcode;
    private LocalDateTime createdAt;
    //... 이하 생략
}
```

여러가지 생성자를 만들어서 그것으로 인스턴스를 만들어줄 수는 있지만,

그 마저도 갯수가 많다면 알아보기 힘들 것이다.

자바빈즈 패턴도 있는데,

이 패턴은 매개변수 없는 기본 생성자로 객체를 만들고,

`Setter`메소드들로 매개변수의 값을 설정해준다.

이 방법의 단점은 어떠한 변수가 필수 값이라고 가정할 때,

값이 제대로 들어가지 않아서 예외를 발생시킬 수도 있다.

그러면 어떤 로직을 실행하는데에 있어서, 유효한 객체가 들어올 것이라고 예상했던 로직들에서

예외가 발생할 것이다. 그렇게 되면, 일관성이 유지되지 않는다.

그래서 나온것이 바로 Builder 패턴이다.

빌더의 단점은 객체를 생성해주려면

앞단에서 Builder를 무조건적으로 구현을 해주어야 한다.


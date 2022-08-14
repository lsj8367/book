# 자원을 직접 명시하지 말고 의존 객체 주입을 사용하라

많은 클래스들이 하나 이상의 자원에 의존하는데,

이를 다형성 개념에 맞지않게 상수로 등록하는 경우를 볼 수 있는데

이를 조금 변환하는게 좋다.

피시방에는 CPU가 인텔, 라이젠이 있을 수 있는데,

최초의 버전에선 인텔만 있어서 라이젠을 고려하지 않은 경우 아래 코드 처럼 될 수 있다.

이 예시가 아닐지라도, 유틸리티 클래스의 경우에 이런 경우가 더 심각해질 수 있다.

그말인 즉, 테스트하기 어려운 코드가 작성된다고 본다.

```java
public class PcRoom {
    private static final Cpu INTEL = new ...;
    private static final Cpu RYZEN = new ...;
    
    private PcRoom() {
        throw new UnsupportedOperationException();
    }
    
    public static Computer intelSpec() {
        return new Computer(INTEL, ...);
    }

    public static Computer ryzenSpec() {
        return new Computer(INTEL, ...);
    }
}
```

이런 경우에는 아무리 유틸리티 클래스라고 한들 타입이 정해져있어서,

CPU를 바꾸거나 해줄 수가 없고 추가해주려면 상수를 추가해주어야 한다.

이럴때 사용하는 것이 의존 객체인데 생성자에서 인자를 직접 할당해주는 방법이다.

```java
public class PcRoom {
    
    private final Cpu cpu;
    
    public PcRoom(final Cpu cpu) {
        this.cpu = cpu;
    }
    
    public Computer spec() {
        
    }
}

```

이렇게 생성자에서 할당해주는 방식으로 선택해주게 되면

애초에 첫 조건이었던 CPU던, 추후에 들어오는 CPU이던 각자의 구현에 맞게 끼워주면 되는 형식으로 구성이 된다.

이 주입 방식은 앞에서 살펴봤던 방식들에도 응용할 수가 있게 된다.

책에서는 `Supplier<T>`에 관한 내용도 나오지만,

이 방식까지 고려한 설계가 나올까? 하는 생각도 든다.

그리고 더 나아가서는 이러한 예제를 보니

많은 논쟁이 있는 유틸리티 클래스를 사용하지 않아도 될것 같다. (또는 필요하지 않다)

라는 생각이 계속 들게되는 챕터인것 같다.

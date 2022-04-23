# private 생성자나 열거 타입으로 싱글턴임을 보증하라

[싱글톤](https://velog.io/@lsj8367/Java-%EB%94%94%EC%9E%90%EC%9D%B8%ED%8C%A8%ED%84%B4-%EC%8B%B1%EA%B8%80%ED%86%A4Singleton-pattern)이란 인스턴스를 오직 하나만 생성할 수 있는 클래스를 말한다.

싱글톤의 예시로는 우리가 많이 쓰는 프레임워크인 스프링 프레임워크

그 내의 빈들이 싱글톤 인스턴스이다.

클래스를 싱글톤으로 만들게되면 이를 사용하는 클라이언트를 테스트하기가 어렵다고 한다.

이유인 즉슨, 싱글톤으로 만들어진 인스턴스를 가짜(Mock객체)로 구현할 수 없다.

보통의 싱글톤 방식은 아래와 같다.

```java
public class Singleton {
    private final Singleton instance;
    
    private Singleton() {
    }
    
    public static Singleton getInstance() {
        if (instance == null) {
            return new Singleton();
        }
        
        return this.instance;
    }
}
```

이 경우에는 문제점이 멀티스레드에 안전하지 않다는 것이 있다.

그래서 `static` 변수로 인스턴스를 로딩시점에 먼저 생성하는 방법이 있다.

그렇게 되면, 리플렉션을 사용해서 호출하는 경우만 아니라면 싱글톤임을 보장해 줄 수 있다.

책에서는 상황에 따라 스레드별로 다른 인스턴스를 넘겨주게 할 수 있다고는 나와있지만,

싱글톤객체를 그렇게까지 다뤄야하나 생각이 든다.

```java
public class Singleton {
    private Singleton() {
    }
    
    public static Singleton getInstance() {
        return LazyHolder.INSTANCE;
    }
    
    private static class LazyHolder {
        private static final Singleton INSTANCE = new Singleton();
    }
}
```

위와 같은 방법이나, 또는 `enum`을 통한 싱글톤 인스턴스가 제일 괜찮다고 생각한다.

열거타입은 리플렉션 공격에도 다른 인스턴스가 생기는 일을 막아준다.

> 대부분 상황에서는 원소가 하나뿐인 열거 타입이 싱글턴을 만드는 가장 좋은 방법이라고 한다.

왜냐면 `enum` 자체가 `serialization` 및 `thread-safety`를 보장하고, 싱글톤에서 발생하는 단일 인스턴스 위반을 해결해준다.

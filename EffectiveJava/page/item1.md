# Item1. 생성자 대신 정적 팩토리 메소드를 고려하라

🚫 주의!! GoF의 디자인패턴의 그런 패턴과는 무관하다

우선 이 책에서 나오는 클라이언트부터 정리를 해야겠다.

여기서의 클라이언트는 그냥 우리라고 생각하는게 맞다고 본다.

우리가 인스턴스를 얻기 위해서는 일반적으로 `public` 생성자이다.

여기서 나오는 방법은 일반적 생성자가 아닌

`정적 팩토리 메소드` 그 클래스의 인스턴스를 반환하는 방법이다.

## 장점

그렇다면 이 방법의 장점을 알아본다.

### 1. 이름을 가질 수 있다.

대개 생성자는 이름이 없기 때문에 코드로 보면

```java
Foo foo = new Foo();
Foo foo = new Foo(parameter1);
Foo foo = new Foo(param1, param2);
```

이런식으로 어떤 조건일 때 각기 다른 생성자로 인스턴스화를 해주는지

알 수 있는 법이 힘들다.

기본적으로 컴퓨터라는 객체가 모니터와 본체를 가지고 있다고 한다면,

본체만 따로 구매했을 경우엔 모니터는 없는 상태이다.

```java
Computer computer = new Computer("본체");
Computer computer = Computer.noMonitor("본체");
```

좀 극단적으로 예를 들었지만 어떤게 더 나을거냐 라는건 본인의 판단이다.

물론 내가 생각할 때에는 후자가 더 이해하기 쉽다고 생각한다.

생성자 오버로딩이 여러개가 되어있거나, 그렇게 사용해야 한다면 더더욱

**정적 팩토리 메소드**를 사용하는걸 추천한다.

### 2. 호출될 때마다 새로운 인스턴스를 반환하지 않아도 된다.

이 이유로 불변 클래스는 인스턴스를 미리 만들어 놓거나

새로 생성한 인스턴스를 캐싱하여 사용하게 될 수 있다.

여기서 나온 `Boolean.valueOf(boolean);`를 들어가보면

이미 `public static final Boolean`으로 `true`와 `false`에 대한 객체를 정의해두었다.

이처럼 같은 객체가 반복적으로 요청되는 상황일 경우에 이런 정적 팩토리 메소드는 좋은 방법이 될 수 있다.

그래서 정적 팩토리 메소드는 **인스턴스 통제** 클래스라고도 한다.

그 이유는, 언제 어느 인스턴스를 살아 있게 할지 **통제**하기 때문.

인스턴스를 통제함으로써 [Singleton](https://lsj8367.github.io/java/Java-design-pattern-7/) 이나 인스턴스화 불가로도 만들 수 있다.

결국 이 싱글톤 패턴도 지금 이 정적 팩토리 메소드를 사용하면서 +@ 로 동시성 문제까지 고안한 패턴이라 생각한다.

### 3. 반환 타입의 하위 타입 객체를 반환할 수 있는 능력이 있다.

> 반환할 객체의 클래스를 자유롭게 선택하게 할 수 있는 **유연성**을 제공한다.

```java
public class Parent {
    private Parent() {
    }
    
    public static Parent of() {
    // 자식의 클래스를 유연하게 구성하였고 클라이언트가 호출 할 때에는
    // Parent parent = Parent.of(); //실상 불러오게 된건 Child가 끼워지는것
        return Child.of();
    }
}

public class Child extends Parent {
    private Child() {
    }
    
    public static Child of() {
        return new Child();
    }
}
```

`Collections` 프레임워크는 불변 객체나 동기화 등등의 기능을 하는 유틸리티 구현체를 제공하는데

전부 정적 팩토리 메소드로 얻게 되어있다. (물론 직접 만들어줘도 된다.)

### 4. 입력 매개변수에 따라 매번 다른 클래스의 객체를 반환할 수 있다.

이부분은 팩토리와 연관이 있다.

팩토리 뜻은 공장이라는 뜻인데, 입력값이 들어온 매개변수에 따라 다른 객체를 반환해주면 된다.

```java
public class Parent {
    private Parent() {
    }
    
    public static Parent of(final char selector) {
        if (selector == 'p') {
            return new Parent();
        }
        return Child.of();
    }
}

public class Child extends Parent {
    private Child() {
    }
    
    public static Child of() {
        return new Child();
    }
}
```

### 5. 정적 팩토리 메소드를 작성하는 시점에는 반환할 객체의 클래스가 존재하지 않아도 된다.

```java
public abstract class Weapon {

    public static Weapon getInstance(final String classPath) {
        
        Weapon weapon = null;

        try {
            Class<?> clazz = Class.forName(classPath);
            weapon = (Weapon) clazz.getDeclaredConstructor().newInstance();

        } catch (InstantiationException e) {
            System.out.println(e.getMessage());
        } catch (IllegalAccessException e) {
            System.out.println(e.getMessage());
        } catch (InvocationTargetException e) {
            System.out.println(e.getMessage());
        } catch (NoSuchMethodException e) {
            System.out.println(e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }

        return weapon;
    }

    abstract String attack();
}
```

자바 리플렉션을 활용해서 실제 구현체는 없는 상황에서 이처럼

`clazz.getDeclaredConstructor().newInstance()` 로 인스턴스를 생성해주며

책 내용처럼 JDBC는 아니지만 구현을 해봤다.

`Weapon` 의 구현체는 어디에도 없지만 구현체가 없을 때에는 예외로 미리 잡아줌으로써

예외 처리도 미리 정의해놓고 사용할 수 있다.

> 아래는 작은 예시이다.

```java
public class Ax extends Weapon {
    @Override
    public String attack() {
        return "도끼로 찍는다.";
    }
}
```

```java
class WeaponTest {

    @Test
    @DisplayName("정상적인 생성 테스트")
    void normalTest() {
        Weapon weapon = Weapon.getInstance("ex.Ax");
        assertThat(weapon.attack()).isEqualTo("도끼로 찍는다.");
    }

    @Test
    @DisplayName("리플렉션 생성 예외 테스트")
    void exceptionTest() {
        // ClassNotFoundException이 발생한다.
        // 예시에서는 System.out.println(); 으로 에러 메세지를 찍어두었다.
        final Weapon weapon = Weapon.getInstance("ex.Gun");
        assertThatThrownBy(() -> weapon.attack())
            .isInstanceOf(NullPointerException.class);
    }
}
```

이제 단점에 대해 알아보자

## 단점

### 1. 상속을 하려면 public이나 protected 생성자가 필요하니 정적 팩토리 메소드만 제공하면 하위 클래스를 만들 수 없다.

위의 예시에서 다시 `Weapon`을 다시 보자면...

```java
public abstract class Weapon {
    private Weapon() {}
    public static Weapon getInstance(); //...이하 생략
    public abstract String attack();
}
```

짧게 가져오면 이런 추상 클래스 아래에 추상 메서드가 있다고만 가정한다.

```java
public class Gun extends Weapon {
    public Gun() {
        super(); // 에러 !!
    }
}
```

컴파일 에러가 발생하게 되고 이런식이라면 정적 팩토리 메소드는 만들 수 없게 된다.

### 2. 정적 팩토리 메소드 프로그래머가 찾기 어렵다.

생성자처럼 API 설명에 잘 드러나지는 않지만,

나는 개발할 때 `.`을 찍어보면서 그리고 `Builder`클래스가 좀 보여서

`new` 대신 그 클래스 이름을 적고 점을 찍어서 정적 팩토리를 먼저 찾는편이다.

그래서 나는 이게 단점으로 와닿지 않는다.
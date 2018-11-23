### 待解决问题
* spring怎么保证configuration创建的嵌套的bean的单例，如bar的单例
```
@Configuration
public class TestConfigration {
    @Bean
    public Bar bar() {
        return new Bar();
    }

    @Bean
    public Foo foo() {
        return new Foo(bar());
    }

}
```

* 
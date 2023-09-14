package com.dotd.user.batch.reader;

import com.dotd.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import javax.persistence.EntityManagerFactory;

/*

@RequiredArgsConstructor를 사용하면 생성자가 자동으로 만들어지기 때문에 생성자에서 설정을 해야 합니다.

또한 JpaPagingItemReader는 Spring Batch에서 제공하는 추상 클래스이기 때문에, 그냥 생성자 안에서 초기화할 수 없습니다.
afterPropertiesSet을 오버라이드하여 초기화 로직을 구현할 수 있습니다.

즉 afterPropertieseSet 은 빈의 속성이 주입된 후 추가적인 초기화 작업을 진행한다.
EntityMangerFactory가 주입된 후에 필요한 설정들을 설정한다.
 */

@Component
@StepScope
@RequiredArgsConstructor
public class UserItemReader extends JpaPagingItemReader<User> {

    private final EntityManagerFactory entityManagerFactory;


    @Override
    public void afterPropertiesSet() throws Exception {
        setEntityManagerFactory(entityManagerFactory);
        setQueryString("select u from User u");
        setPageSize(100);
        super.afterPropertiesSet();
    }
}
package ac.knu.likeknu.service;

import ac.knu.likeknu.repository.CafeteriaRepository;
import ac.knu.likeknu.repository.MenuRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@Slf4j
@DisplayName("식단 비즈니스 로직 테스트")
@ExtendWith(value = MockitoExtension.class)
public class MenuServiceTest {

    @InjectMocks
    MenuService menuService;

    @Mock
    CafeteriaRepository cafeteriaRepository;
    @Mock
    MenuRepository menuRepository;

    @Test
    void getMenuDataAndSuccess() {
        //given
        //when
        //then
    }
}

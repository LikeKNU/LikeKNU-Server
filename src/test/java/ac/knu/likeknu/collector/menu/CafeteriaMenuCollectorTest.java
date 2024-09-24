package ac.knu.likeknu.collector.menu;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CafeteriaMenuCollectorTest {

    @Autowired
    private CafeteriaMenuCollector cafeteriaMenuCollector;

    @Test
    void test() {
        cafeteriaMenuCollector.collectMenus();
    }
}
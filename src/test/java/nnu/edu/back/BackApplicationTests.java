package nnu.edu.back;

import nnu.edu.back.common.utils.FileUtil;
import nnu.edu.back.pojo.support.param.ParamScript;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BackApplicationTests {

    @Test
    void contextLoads() {
        ParamScript paramScript = FileUtil.parseParamXML("E:\\monitoring\\bin\\0ef6d9a4-fb05-4f5b-494d-e4713a8a7730\\param\\b43d3106-b1a6-b6d9-2dd2-6e622478e761\\param.conf");
        System.out.println(1);
    }

}

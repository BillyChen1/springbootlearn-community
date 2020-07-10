package cn.billychen.community.cache;

import cn.billychen.community.dto.TagDTO;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TagCache {
    public static List<TagDTO> get() {
        ArrayList<TagDTO> tagDTOS = new ArrayList<>();
        TagDTO program = new TagDTO();
        program.setCategoryName("开发语言");
        program.setTags(Arrays.asList("js", "php", "css", "html", "java", "python", "cpp"));
        tagDTOS.add(program);

        TagDTO framework = new TagDTO();
        framework.setCategoryName("框架");
        framework.setTags(Arrays.asList("spring", "flask", "express"));
        tagDTOS.add(framework);

        TagDTO server = new TagDTO();
        server.setCategoryName("服务器");
        server.setTags(Arrays.asList("linux", "nginx", "docker", "ubuntu", "tomcat", "redis"));
        tagDTOS.add(server);

        return tagDTOS;
    }
}

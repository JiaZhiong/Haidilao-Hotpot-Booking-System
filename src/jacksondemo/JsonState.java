package jacksondemo;

import java.io.File;

public interface JsonState {
    File savePath = new File("src\\hotpot\\booking\\system\\Haidilao.json");
    
    public void serialize();
    public void deserialize();
}

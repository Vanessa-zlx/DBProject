package cn.sakuraxiafan.util;

import java.io.IOException;
import java.util.Objects;
import java.util.Properties;

public class DBMapper<T> {
    private final Properties loader = new Properties();
    Class<T> target;
    private String targetName;

    public Class<T> getTarget() {
        return target;
    }

    public DBMapper(Class<T> target) {
        this.target = target;
        try{
            loader.load(Thread.currentThread().getContextClassLoader().getResourceAsStream(
                    "map/"+target.getSimpleName()+".properties"));
            targetName = loader.getProperty("prefix");
            if (targetName==null){
                targetName =  target.getSimpleName().toLowerCase();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String map(String field){
        String property = loader.getProperty(field);
        return Objects.requireNonNullElseGet(property, () -> targetName + StringUtil.upperFirst(field));
    }
}

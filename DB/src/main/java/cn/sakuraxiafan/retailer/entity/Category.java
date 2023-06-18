package cn.sakuraxiafan.retailer.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Category {
    private String name;
    private String superTypeName;

    /**
     *
     * @param name 10
     * @param superTypename 10
     */
    public Category(String name, String superTypename) {
        this.name = name;
        this.superTypeName = superTypename;
    }
}

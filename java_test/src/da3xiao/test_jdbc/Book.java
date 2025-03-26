package da3xiao.test_jdbc;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * /**
 * 书籍信息表
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Book implements Serializable {
    private Long id;
    private String name;
    private String description;
    private Integer price;
    private String cover_img;
    private String author;
    private String isbn;
    private String coverImg;
}

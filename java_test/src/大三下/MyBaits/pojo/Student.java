package 大三下.MyBaits.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import javax.persistence.Column;

@TableName(value = "student")
@Data
public class Student {
    @TableId(type = IdType.AUTO)
    @Column(name = "sid")
    private Integer sid;

    @Column(name = "sname")
    private String sname;

    @Column(name = "age")
    private Integer age;
}
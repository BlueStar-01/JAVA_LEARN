package da3xiao.MyBaits.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Entity;
import org.hibernate.annotations.Table;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

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
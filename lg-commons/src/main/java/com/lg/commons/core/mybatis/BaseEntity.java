
package com.lg.commons.core.mybatis;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.lg.commons.util.validators.Update;
import com.lg.commons.util.validators.Update;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * The class Base entity.
 *
 * @author paascloud.net@gmail.com
 */
@Data
public class
BaseEntity<T> implements Serializable {

    private static final long serialVersionUID = 2393269568666085258L;

    @Min(1)
    @NotNull(groups = {Update.class})
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
}

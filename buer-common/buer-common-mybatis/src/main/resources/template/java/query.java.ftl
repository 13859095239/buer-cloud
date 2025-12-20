package ${queryPackage};

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**

 * ${table.comment} Query

 *
 * @author ${author}
 * @since ${since}
 */
@Data
@Accessors(chain = true)
@Schema(description = "${table.comment} Query")
public class ${queryEntity} implements Serializable {

    /**
     * ${cnTableSubName}id列表
     */
    @Schema(description = "${cnTableSubName}id列表")
    private String ${enTableSubName}Ids;

}
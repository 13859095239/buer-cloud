package ${controllerPackage};

import cn.dev33.satoken.annotation.SaCheckPermission;
import entity.com.buer.common.core.R;
import annotation.com.buer.common.log.SysLog;
<#if isDTO>
import ${dtoImport};
<#else>
import ${entityImport};
</#if>
import ${queryImport};
<#if isVO>
import ${voImport};
</#if>
import ${serviceImport};
import com.mybatisflex.core.paginate.Page;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * ${cnTableSubName} Controller
 *
 * @author ${author}
 * @since ${since}
 */
@RestController
@RequestMapping("${enTableSubNameLine}")
@RequiredArgsConstructor
@Tag(name = "${cnTableSubName} Controller")
public class ${entity}Controller {

    private final ${entity}Service service;

    /**
     * 通过id查询${cnTableSubName}
     *
     * @param id id
     * @return ${voEntity}
     */
    @Operation(summary = "通过id查询${cnTableSubName}")
    @GetMapping(value = "/{id}")
    public R<${voEntity}> get${enTableSubNameUpper}ById(@PathVariable Long id) {
        return R.ok(service.get${enTableSubNameUpper}ById(id));
    }

    /**
     * 新增${cnTableSubName}
     *
     * @param ${dtoParam} ${dtoEntity}对象
     * @return Boolean
     */
    @SysLog("新增${cnTableSubName}")
    @Operation(summary = "新增${cnTableSubName}")
    @PostMapping
    @SaCheckPermission("${enTableSubNameLine}-add")
    public R<Boolean> add${enTableSubNameUpper}(@Validated @RequestBody ${dtoEntity} ${dtoParam}) {
        return R.ok(service.add${enTableSubNameUpper}(${dtoParam}));
    }

    /**
     * 通过id修改${cnTableSubName}
     *
     * @param ${dtoParam} ${dtoEntity}对象
     * @return Boolean
     */
    @SysLog("编辑${cnTableSubName}")
    @Operation(summary = "通过id修改${cnTableSubName}")
    @PutMapping
    @SaCheckPermission("${enTableSubNameLine}-edit")
    public R<Boolean> update${enTableSubNameUpper}(@Validated @RequestBody ${dtoEntity} ${dtoParam}) {
        return R.ok(service.update${enTableSubNameUpper}(${dtoParam}));
    }

    /**
     * 通过id删除${cnTableSubName}
     *
     * @param ${enTableSubName}Ids ${enTableSubName}Ids
     * @return Boolean
     */
    @SysLog("删除${cnTableSubName}")
    @Operation(summary = "通过id删除${cnTableSubName}")
    @DeleteMapping
    @SaCheckPermission("${enTableSubNameLine}-delete")
    public R<Boolean> remove${enTableSubNameUpper}ByIds(@RequestBody String ${enTableSubName}Ids) {
        return R.ok(service.remove${enTableSubNameUpper}ByIds(${enTableSubName}Ids));
    }

    /**
     * 列表查询${cnTableSubName}
     *
     * @param ${queryParam} 查询对象
     * @return 列表
     */
    @Operation(summary = "列表查询${cnTableSubName}")
    @GetMapping(value = "/list")
    public R<List<${voEntity}>> list${enTableSubNameUpper}(${queryEntity} ${queryParam}) {
        return R.ok(service.list${enTableSubNameUpper}(${queryParam}));
    }

    /**
     * 分页查询${cnTableSubName}
     *
     * @param page   分页对象
     * @param ${queryParam} 查询对象
     * @return 分页对象
     */
    @Operation(summary = "分页查询${cnTableSubName}")
    @GetMapping(value = "/page")
    public R<Page<${voEntity}>> page${enTableSubNameUpper}(Page<${voEntity}> page, ${queryEntity} ${queryParam}) {
        return R.ok(service.page${enTableSubNameUpper}(page, ${queryParam}));
    }

}
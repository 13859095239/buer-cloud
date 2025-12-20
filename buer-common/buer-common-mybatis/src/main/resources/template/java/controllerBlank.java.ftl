package ${controllerPackage};

import ${serviceImport};
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
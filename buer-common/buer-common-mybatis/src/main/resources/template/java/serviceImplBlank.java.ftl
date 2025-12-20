package ${serviceImplPackage};

import ${entityImport};
import ${mapperImport};
import ${serviceImport};
import com.mybatisflex.spring.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * ${cnTableSubName} ServiceImpl
 *
 * @author ${author}
 * @since ${since}
 */
@Service
@RequiredArgsConstructor
public class ${entity}ServiceImpl extends ServiceImpl<${mapperEntity}, ${entity}> implements ${entity}Service {

}
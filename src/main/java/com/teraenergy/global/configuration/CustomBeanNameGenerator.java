package com.teraenergy.global.configuration;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanNameGenerator;
import org.springframework.context.annotation.AnnotationBeanNameGenerator;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

public class CustomBeanNameGenerator implements BeanNameGenerator {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomBeanNameGenerator.class);

    // 이 클래스는 Spring이 기본적으로 사용하는 BeanNameGenerator입니다. 컨트롤러가 아닐경우 이 클래스를 사용하려고 선언하였습니다.
    private final AnnotationBeanNameGenerator defaultGenerator = new AnnotationBeanNameGenerator();

    @Override
    public String generateBeanName(final BeanDefinition definition, final BeanDefinitionRegistry registry) {
        // 파라미터의 definition에는 bean을 만드려는 Class<?> 외 metadata정보를 가지고 옵니다.
        // registry는 딱히 활용할일이 없어 자세한 정보는 모르겠습니다.

        final String result;

        // definition이 컨트롤러일 경우 패키지 이름을 포함한 Bean 이름을, 아닐경우 Spring 기본형식을 따릅니다.
        if (isController(definition)) {
            result = generateFullBeanName((AnnotatedBeanDefinition) definition);
        } else {
            result = this.defaultGenerator.generateBeanName(definition, registry);
        }

        LOGGER.info("Registered Bean Name : " + result); //$NON-NLS-1$

        return result;
    }

    private String generateFullBeanName(final AnnotatedBeanDefinition definition) {
        // 패키지를 포함한 전체 이름을 반환합니다.
        return definition.getMetadata().getClassName();
    }

    private Set<String> getAnnotationTypes(final BeanDefinition definition) {
        final AnnotatedBeanDefinition annotatedDef = (AnnotatedBeanDefinition) definition;
        final AnnotationMetadata metadata = annotatedDef.getMetadata();
        return metadata.getAnnotationTypes();
    }

    private boolean isController(final BeanDefinition definition) {
        if (definition instanceof AnnotatedBeanDefinition) {

            // definition에 속한 모든 Annotation을 가져옵니다.
            final Set<String> annotationTypes = getAnnotationTypes(definition);

            // annotation 중 @Controller이거나 @RestController 일 경우 Controller로 인식합니다.
            for (final String annotationType : annotationTypes) {
                if (annotationType.equals(Controller.class.getName())) {
                    return true;
                }
                if (annotationType.equals(RestController.class.getName())) {
                    return true;
                }
            }
        }
        return false;
    }
}
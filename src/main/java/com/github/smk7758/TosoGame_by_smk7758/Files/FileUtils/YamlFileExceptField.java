package com.github.smk7758.TosoGame_by_smk7758.Files.FileUtils;

import static java.lang.annotation.ElementType.*;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Target(FIELD)
@Retention(RetentionPolicy.RUNTIME)
/**
 * If you don't want to move on loadField().
 *
 * @author smk7758
 */
public @interface YamlFileExceptField {
}

package nl.kiipdevelopment.minescreen.util;

import org.jetbrains.annotations.ApiStatus;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

@ApiStatus.Internal
@Target({ ElementType.ANNOTATION_TYPE, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.FIELD, ElementType.TYPE })
public @interface Unimplemented {}

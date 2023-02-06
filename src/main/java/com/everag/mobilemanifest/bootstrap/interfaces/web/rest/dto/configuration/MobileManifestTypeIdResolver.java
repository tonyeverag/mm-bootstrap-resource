package com.everag.mobilemanifest.bootstrap.interfaces.web.rest.dto.configuration;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.DatabindContext;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.fasterxml.jackson.databind.jsontype.TypeIdResolver;

import java.io.IOException;

public class MobileManifestTypeIdResolver implements TypeIdResolver {

    private JavaType baseType;

    private static final String OLD_PACKAGE = "com.dairy.mobilemanifest.interfaces.web.rest.dto";
    private static final String NEW_PACKAGE = "com.dairy.mobilemanifest.interfaces.web.rest.dto.configuration";

    @Override
    public String getDescForKnownTypeIds() {
        return "";
    }

    @Override
    public JsonTypeInfo.Id getMechanism() {
        return JsonTypeInfo.Id.CUSTOM;
    }

    @Override
    public String idFromBaseType() {
        return baseType.getRawClass().getName();
    }

    @Override
    public String idFromValue(Object obj) {
        return idFromValueAndType(obj, obj.getClass());
    }

    @Override
    public String idFromValueAndType(Object obj, Class<?> clazz) {
        return clazz.getName();
    }

    @Override
    public void init(JavaType baseType) {
        this.baseType = baseType;

    }

    @Override
    public JavaType typeFromId(DatabindContext arg0, String type) throws IOException {
        String className = null;
        if (type.contains(NEW_PACKAGE)) {
            className = type;
        } else {
            className = type.replace(OLD_PACKAGE, NEW_PACKAGE);
        }
        Class<?> clazz = null;
        try {
            clazz = Class.forName(className);
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException("Could not find class: " + className);
        }

        return TypeFactory.defaultInstance().constructSpecializedType(this.baseType, clazz);
    }
}
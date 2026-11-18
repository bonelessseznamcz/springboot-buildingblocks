package com.flem.resthello.configuraton;

import com.flem.resthello.entity.User;
import org.jspecify.annotations.Nullable;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.xml.JacksonXmlHttpMessageConverter;
import org.springframework.http.converter.json.JacksonJsonHttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;
import tools.jackson.databind.ser.std.SimpleBeanPropertyFilter;
import tools.jackson.databind.ser.std.SimpleFilterProvider;

import java.util.Map;

@ControllerAdvice
public class FieldFilterAdvice implements ResponseBodyAdvice<Object> {

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return converterType == JacksonJsonHttpMessageConverter.class
                || converterType == JacksonXmlHttpMessageConverter.class;
    }

    @Override
    public @Nullable Map<String, Object> determineWriteHints(
            @Nullable Object body, MethodParameter returnType,
            MediaType selectedContentType,
            Class<? extends HttpMessageConverter<?>> selectedConverterType) {
        String methodName = returnType.getMethod().getName();
        var request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                .getRequest();
        var fields = request.getParameter("fields"); // e.g. "name,email"
        var exclude = request.getParameter("exclude"); // e.g. "email,ssn"

        SimpleFilterProvider filterProvider;
        if (exclude != null && !exclude.isBlank()) {
            // Exclude specified fields
            filterProvider = new SimpleFilterProvider()
                    .addFilter("userFilter",
                            SimpleBeanPropertyFilter.serializeAllExcept(exclude.split(",")));
        } else if (fields != null && !fields.isBlank()) {
            // Include only specified fields
            filterProvider = new SimpleFilterProvider()
                    .addFilter("userFilter",
                            SimpleBeanPropertyFilter.filterOutAllExcept(fields.split(",")));
        } else if (methodName.equals("getUserById")) {
            // Default: exclude sensitive fields
            filterProvider = new SimpleFilterProvider()
                    .addFilter("userFilter",
                            SimpleBeanPropertyFilter.serializeAllExcept("email", "ssn"));
        } else {
            filterProvider = new SimpleFilterProvider()
                    .addFilter("userFilter",
                            SimpleBeanPropertyFilter.serializeAllExcept("ssn"));
        }

        // Hint key: "tools.jackson.databind.ser.FilterProvider"
        return Map.of("tools.jackson.databind.ser.FilterProvider", filterProvider);
    }

    @Override
    public @Nullable Object beforeBodyWrite(@Nullable Object body, MethodParameter returnType,
                                            MediaType selectedContentType,
                                            Class<? extends HttpMessageConverter<?>> selectedConverterType,
                                            ServerHttpRequest request, ServerHttpResponse response) {
        return body;
    }
}

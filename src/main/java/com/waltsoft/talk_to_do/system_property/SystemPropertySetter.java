package com.waltsoft.talk_to_do.system_property;

import com.waltsoft.talk_to_do.dot_env.DotEnv;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public class SystemPropertySetter {

    final DotEnv dotenv;

    public SystemPropertySetter() {
        this.dotenv = new DotEnv();
    }

    public void set() {
        for (final Supplier<Map<String, String>> propertiesLoader : getPropertiesLoaderFunctions()) {
            final Map<String, String> dataMap = propertiesLoader.get();

            if (dataMap==null) {
                continue;
            }

            for (final Map.Entry<String, String> entry : dataMap.entrySet()) {
                if (entry==null || entry.getValue()==null || entry
                        .getValue()
                        .isEmpty()) {
                    continue;
                }

                System.setProperty(entry.getKey(), entry.getValue());
            }
        }
    }

    List<Supplier<Map<String, String>>> getPropertiesLoaderFunctions() {
        List<Supplier<Map<String, String>>> loaderFunctions = new ArrayList<>();
        loaderFunctions.add(this::loadDatabaseProperties);
        return loaderFunctions;
    }

    Map<String, String> loadDatabaseProperties() {
        final Map<String, String> properties = new HashMap<>();

        final String datasourceUrlKey = "spring.datasource.url";
        final String databaseUrlFromDotEnv = dotenv.getDatabaseUrl();
        properties.put(datasourceUrlKey, databaseUrlFromDotEnv);

        final String datasourceUserNameKey = "spring.datasource.username";
        final String databaseUserFromDotEnv = dotenv.getDatabaseUser();
        properties.put(datasourceUserNameKey, databaseUserFromDotEnv);

        final String datasourcePasswordKey = "spring.datasource.password";
        final String databasePasswordFromDotEnv = dotenv.getDatabasePassword();
        properties.put(datasourcePasswordKey, databasePasswordFromDotEnv);

        return properties;
    }


}

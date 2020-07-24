package com.robustwealth.microservices.sample.repository.common;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.text.MessageFormat;

import javax.annotation.Nonnull;

import org.apache.commons.io.IOUtils;

public class SqlReader {
    private final Class<?> repositoryClass;

    public SqlReader(Class<?> repositoryClass) {
        this.repositoryClass = repositoryClass;
    }

    @Nonnull
    public String getSql(String queryName) {
        final InputStream stream = getClass().getResourceAsStream(
                File.separator + getBaseResourceFromRepositoryClassName() + "_" + queryName + ".sql");

        if (stream == null) {
            throw new IllegalStateException(
                    MessageFormat.format("Unable to open SQL file for {0}, {1}", repositoryClass.getName(), queryName));
        }

        try {
            return IOUtils.toString(stream, StandardCharsets.UTF_8);
        } catch (final IOException e) {
            throw new IllegalStateException(MessageFormat.format("IOException opening SQL file for {0}, {1}",
                    repositoryClass.getName(), queryName), e);
        }
    }

    @Nonnull
    private String getBaseResourceFromRepositoryClassName() {
        return repositoryClass.getName().replaceAll("\\.", File.separator);
    }
}

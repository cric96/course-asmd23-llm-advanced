package it.unibo.utils;

import it.unibo.requirements.RequirementAnalysis;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;

public class Resources {
    public static String loadAllContent(String name) throws Exception {
        return new String(Files.readAllBytes(getPath(name)));
    }
    
    private static java.nio.file.Path getPath(String name) throws Exception {
        return Paths.get(getResourceURI(name));
    }
    
    private static java.net.URI getResourceURI(String name) throws Exception {
        return Objects.requireNonNull(
                RequirementAnalysis.class.getClassLoader().getResource(name)
        ).toURI();
    }
}

package org.starwars;

import au.com.dius.pact.model.Pact;
import au.com.dius.pact.model.PactReader;
import au.com.dius.pact.provider.junit.loader.PactLoader;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PactFolderSystemEnvLoader implements PactLoader {

    private final File path;

    public PactFolderSystemEnvLoader() {
        String path = Optional.ofNullable(System.getenv("pacts"))
                                .orElse(Optional.ofNullable(System.getProperty("pacts"))
                                .orElse(PactFolderSystemEnvLoader.class.getClassLoader().getResource("pacts").getPath()));
        this.path = new File(path);
    }

    @Override
    public List<Pact> load(String providerName) throws IOException {
        List<Pact> pacts = new ArrayList<Pact>();
        File[] files = path.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.endsWith(".json");
            }
        });
        if (files != null) {
            for (File file : files) {
                Pact pact = PactReader.loadPact(file);
                if (pact.getProvider().getName().equals(providerName)) {
                    pacts.add(pact);
                }
            }
        }
        return pacts;
    }
}

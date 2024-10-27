package com.erpang.github.util;

import org.kohsuke.github.GHMyself;
import org.kohsuke.github.GitHub;
import org.kohsuke.github.GitHubBuilder;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class Main {
    public static final String TOKEN = System.getenv("GITHUB_TOKEN");
    public static void main(String[] args) throws IOException {
// Batch delete the GitHub repository.
        GitHub github = new GitHubBuilder().withOAuthToken(TOKEN).build();
        Set<String> excludes = new HashSet<String>();
        excludes.add("hnzhrh");

        System.out.println("-----------------------------------------------------------------");
        GHMyself ghMyself = github.getMyself();
        ghMyself.listRepositories(100).forEach(ghRepository -> {
            if (!excludes.contains(ghRepository.getName())) {
                try {
                    System.out.printf("Try to delete repo %s\n", ghRepository.getName());
                    ghRepository.delete();
                    System.out.println("Deleted repo " + ghRepository.getName());
                } catch (IOException e) {
                    System.out.printf("Repo:%s msg:%s\n", ghRepository.getName(), e.getMessage());
                    throw new RuntimeException(e);
                }
            }
        });
    }
}

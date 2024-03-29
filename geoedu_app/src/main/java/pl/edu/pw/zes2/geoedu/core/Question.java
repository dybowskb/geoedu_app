package pl.edu.pw.zes2.geoedu.core;

import java.nio.file.Path;
import java.util.Objects;

public abstract class Question {
    private String title;
    private Path imagePath;

    public Question(String title, Path imagePath) {
        this.title = title;
        this.imagePath = imagePath;
    }

    public abstract String getCorrectAnswer();

    public String getTitle() {
        return title;
    }

    public Path getImagePath() {
        return imagePath;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + Objects.hashCode(title);
        hash = 59 * hash + Objects.hashCode(imagePath);
        return hash;
    }

    @Override
    public boolean equals(Object o) { 
        if (!(o instanceof Question)) {
            return false;
        }

        return hashCode() == ((Question)o).hashCode();
    }
}

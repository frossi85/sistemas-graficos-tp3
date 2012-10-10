package model.loader;

import model.geometry.Model;
import model.ModelLoadException;

public interface iLoader {
    public Model load(String path) throws ModelLoadException;
}

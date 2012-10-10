package model;

import java.util.HashMap;
import model.geometry.Model;
import model.loader.LoaderFactory;

public class ModelFactory {
    
    private static HashMap<Object, Model> modelCache = new HashMap<Object, Model>();
        
    public static Model createModel(String source) throws ModelLoadException {
        Model model = modelCache.get(source);
        if (model == null) {
            model = LoaderFactory.load(source);
            modelCache.put(source, model);
        }
        
        if (model == null)
            throw new ModelLoadException();
        
        return model;
    }
}

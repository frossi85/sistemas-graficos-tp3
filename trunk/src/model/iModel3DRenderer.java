package model;

import model.geometry.Model;

public interface iModel3DRenderer {
    public void render(Object context, Model model);
    public void debug(boolean value);
}
